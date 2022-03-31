import React from 'react';
import {Form,Row,Col,Button,Modal,message,Select} from 'antd';
import TablePanel from '@/components/table';
import columns from '@/column/filter-column';
import {searchList,confirmItem,exportList,configOption, countQualifiedProducts,reinspect} from '@/service/filter-service';
import FormCondition from '@/components/form-condition';
import {download} from '@/utils/index';

const {Option} = Select;
let invalidGrade = ['C', 'D', 'E', 'F'];
class FilterPage extends React.Component{
  options = {
    title: '操作',
    dataIndex: 'options',
    key: 'options',
    width: 85,
    render: (text,record) => {
      if(record.duplicated === 'DUP'){
        return <Button className='table-sure-btn' onClick={this.confirmHandle.bind(this,record)}>确认</Button>
      }
    }
  }

  formRef = React.createRef();
  barcode = '';
  showDup = true;
  showConfirmed = true;
  errMessageShow = true;
  constructor(props){
    super(props);
    this.state = { 
      columns: [this.options,...columns],
      dataSource: [],
      page: 1,
      size: 10,
      totalCount: 0,
      qualifiedCount: 0,
      notQualifiedCount: 0,
      searchParam: {
        end: null,
        from: null,
        productTypeId: '',
        barcode: '',
        barcodeData: '',
        productOptions: [],
        autoInspectResultOptions:[]
      }
    }
  }
  timer = null;
  componentDidMount () {
    this.getTableList();
    this.getConfigOption();
    let tableColumns = columns.map(item => {
      if(item.dataIndex === 'manualReinspectResult'){
        item.render = (value, row, index)=>this.columnRender(value, row, index);
      }
      return item;
    })
    this.timer = setInterval(()=>{
      this.getTableList()
    },3000)
    this.setState({
      columns: tableColumns,
      autoInspectResultOptions: [{label: "设备OK", value: "设备OK"}, {label: "设备NG", value: "设备NG"}],
      manualReinspectResultOptions: [{label: "", value: ''}, {label: "复检OK", value: "复检OK"}, {label: "复检NG", value: "复检NG"}]
    })
  }

  componentWillUnmount(){
    clearInterval(this.timer);
    this.timer = null;
  }
  getConfigOption = () => {
    configOption().then(res => {
      if(res.code === 0){
        let list = res.data.length>0&&res.data.map((item,index) => {
          if(index ===0){
            this.formRef.current.setFieldsValue({
              productTypeId: item.productTypeId
            })
          }
          return {
            label: item.productType,
            value: item.productTypeId
          }
        });
        this.setState({
          productOptions: list
        })
      }
    })
  }

  getTableList = () => {
    const {searchParam,page,size} = this.state;
    let formValue = this.formRef.current.getFieldsValue();
    delete formValue['date']
    let params = {
      page,
      size,
      ...formValue,
      end: searchParam.end,
      from: searchParam.from
    }
    searchList(params).then(res => {
      const {code,data,msg} = res;
      if(code === 0){
        this.errMessageShow = true;
        if (data.content && data.content.length > 0) {
          data.content.map((item,index) => {
            item.index = index+1+(page-1)*size;
          })
        }
        this.setState({
          searchParam: {
            page: 1,
            size: 10,
            ...formValue,
            end: searchParam.end,
            from: searchParam.from
          },
          dataSource: data.content,
          totalCount: data.totalElements
        })
      }else{
        if(this.errMessageShow){
          this.errMessageShow = false;
          message.error(msg,6)
        }
        this.setState({
          dataSource: [],
          totalCount: 0
        })
      }
    }).catch(err=>{
      const {msg} = err;
      if(this.errMessageShow){
        this.errMessageShow = false;
        message.error(msg,6)
      }
      this.setState({
        dataSource: [],
        totalCount: 0
      })
    })
    countQualifiedProducts(params).then(res => {
      const {code, data} = res;
      if (code === 0) {
        this.setState({
          qualifiedCount: data.qualifiedCount,
          notQualifiedCount: data.notQualifiedCount
        })
      }
    })
  }

  searchHandle = () => {
    this.getTableList()
  }

  resetHandle = () => {
    this.formRef.current.resetFields();
    this.getTableList();
  }

  exportHandle = () => {
    const {searchParam} = this.state;
    exportList(searchParam).then(res => {
      if(!!res){
        download(res);
      }
    })
  }

  confirmHandle = (record) => {
    Modal.confirm({
      title: '您确认要提交吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: () => {
        confirmItem({barcode: record.barcode, productTypeId: record.productTypeId}).then(res=>{
          if(res.code === 0) {
            message.success('确认成功');
            this.getTableList();
          }else{
            message.error(res.msg||'确认失败！');
          }
        })
      }
    });
  }

  focusHandle = () => {
    document.getElementById('barcodeRef').select();
  }

  onPageChange = (page) => {
    this.setState({ page: page }, this.getTableList);
  }

  onSizeChange = (current, pageSize) => {
    this.setState({ page: 1, size: pageSize }, this.getTableList);
  }

  
  changeBarcode = (e) => {
    this.errMessageShow = true;
    this.showConfirmed = true;
    this.showDup = true;
  }


  dateChange = (dates, dateStrings) => {
    let searchParam = {
      ...this.state.searchParam,
      end: dateStrings[1],
      from: dateStrings[0]
    }
    this.setState({
      searchParam
    })
  }

  changeValue = (value,record,index,e) => {
    console.log(e,value,record,index)
    Modal.confirm({
      title: `是否要将人工复检结果修改为${e}?`,
      okText: '确认',
      cancelText: '取消',
      onOk: () => {
        reinspect({id: record.id, status: e}).then(res=>{
          if(res.code === 0) {
            message.success('确认成功');
            this.getTableList();
          }else{
            message.error(res.msg||'确认失败！');
          }
        })
      }
    });
  }

  columnRender = (value, row, index) => {
    return <Select 
      defaultValue={value}
      onChange={this.changeValue.bind(this,value,row,index)}>
      <Option value=''/>
      <Option value='复检NG'>复检NG</Option>
      <Option value='复检OK'>复检OK</Option>
    </Select>
  }

  render () {
    const { columns, dataSource,totalCount,page,size,productOptions, autoInspectResultOptions, qualifiedCount, notQualifiedCount } = this.state;
    const formCondition = [
      {
        label: '产品类型',
        controlType: 'Select',
        placeholder: '请选择',
        key: 'productTypeId',
        col: {xs:24, sm:12,md:10,lg:10,xl:8},
        options: productOptions
      },
      {
        label: '时间',
        controlType: 'RangePicker',
        placeholder: ['开始时间','结束时间'],
        format: 'YYYY-MM-DD HH:mm:ss',
        key: 'date',
        showTime: true,
        col: {xs:24, sm:12,md:10,lg:10,xl:8},
        changeValue: this.dateChange
      },
      {
        label: '二维码字符提取',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'barcode',
        col: {xs:24, sm:12,md:10,lg:10,xl:8}
      },
      {
        label: '自动线检测结果',
        controlType: 'Select',
        placeholder: '请选择',
        key: 'autoInspectResult',
        col: {xs:24, sm:12,md:10,lg:10,xl:8},
        options: autoInspectResultOptions
      },
      {
        label: 'SR1000二维码编号',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'barcodeData',
        ref: 'barcodeRef',
        changeValue: this.changeBarcode,
        onFocus: this.focusHandle,
        col: {xs:24, sm:24,md:24,lg:24,xl:24}
      },
    ];
    const rowClassName = (record) => {
      let className = '';
      let formValue = this.formRef.current.getFieldsValue();
      switch(record.duplicated){
        case 'DUP': 
          if(this.showDup&&formValue.barcodeData?.toUpperCase()===record.barcodeData?.toUpperCase()){
            Modal.confirm({
              title: 'Confirm',
              content: '该二维码重码！',
              okText: '确认',
              cancelText: '取消',
            });
            this.showDup = false;
          }
          className = 'bg-red';
          break;
        case 'CONFIRMED' : 
          if(this.showConfirmed&&formValue.barcodeData?.toUpperCase()===record.barcodeData?.toUpperCase()){
            Modal.confirm({
              title: 'Confirm',
              content: '该二维码重码！',
              okText: '确认',
              cancelText: '取消',
            });
            this.showConfirmed = false;
          }
          className = 'bg-yellow'
          break;
        default: break;
      }

      if (record.autoInspectResult === '设备NG') {
        if (invalidGrade.includes(record.barcodeGrade.toUpperCase())) {
          className = 'bg-red';
        }
      }
      return className
    }
    return <div className='page-container'>
      <Form className='page-form' name='search' ref={this.formRef}>
        <Row>
          {
            formCondition.map(condition => {
              const { col } = condition
              return <Col key={condition.key} xs={col.xs} sm={col.sm} md={col.md} lg={col.lg} xl={col.xl}>
                <Form.Item name={condition.key} label={condition.label}>
                    {FormCondition(condition)}
                </Form.Item>
              </Col>
            })
          }
          <Col xs={24} sm={12} md={8} lg={6} xl={6}>
            <Button className="btn submit-btn" onClick={this.searchHandle}>搜索</Button>
            <Button className="btn reset-btn" onClick={this.resetHandle}>重置</Button>
          </Col>
          <Col xs={24} sm={12} md={8} lg={6} xl={6}>
            <Button className="btn submit-btn" onClick={this.exportHandle}>导出</Button>
          </Col>
        </Row>
        <Row>
          <Col xs={24} sm={12} md={6} lg={4} xl={4}>
            <div className='text-line'>设备OK：{ qualifiedCount }</div>
          </Col>
          <Col xs={24} sm={12} md={6} lg={4} xl={4}>
            <div className='text-line'>设备NG：{ notQualifiedCount }</div>
          </Col>
        </Row>
      </Form>
      <TablePanel
        columns={columns}
        showHeader={true}
        rowClassName={rowClassName}
        dataSource={dataSource}
        total={totalCount}
				pageIndex={page}
				pageSize={size}
        onPageChange={this.onPageChange}
        onSizeChange={this.onSizeChange}
      />
    </div>
  }
}

export default FilterPage;