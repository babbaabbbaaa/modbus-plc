import React from 'react';
import {Form,Row,Col,Button,Modal,Select,message} from 'antd';
import TablePanel from '@/components/table';
import columns from '@/column/filter-asc-column';
import {searchList,confirmItem,exportList,configOption,usersOptions,reinspect,countQualifiedProducts} from '@/service/filter-service';
import FormCondition from '@/components/form-condition';
import {download} from '@/utils/index';

const { Option } = Select;

let invalidGrade = ['C', 'D', 'E', 'F'];
class FilterAscPage extends React.Component{
  formRef = React.createRef();
  barcode = '';
  showDup = true;
  showConfirmed = true;
  errMessageShow = true;
  running = false;
  options = {
    title: '操作',
    dataIndex: 'options',
    key: 'options',
    fixed: 'left',
    width: 85,
    render: (text,record) => {
      if(record.duplicated === 'DUP'){
        return <Button className='table-sure-btn' onClick={this.confirmHandle.bind(this,record)}>确认</Button>
      }
    }
  }
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
        usersOptions: [],
        manualReinspectResultOptions:[],
        autoInspectResultOptions: []
      }
    }
  }
  timer = null;
  componentDidMount () {
    this.getTableList();
    this.getConfigOption();
    this.getUsersOptions();
    let tableColumns = columns.map(item => {
      if(item.dataIndex === 'manualReinspectResult'){
        item.render = (value, row, index)=>this.columnRender(value, row, index);
      }
      if(item.dataIndex === 'autoInspectResult' ) {
        item.render = (value) => value.includes('NG')? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
      }
      return item;
    })
    this.timer = setInterval(()=>{
      this.getTableList()
    },3000)
    this.setState({
      columns: tableColumns,
      manualReinspectResultOptions: [{label: "", value: ''}, {label: "复检OK", value: "复检OK"}, {label: "复检NG", value: "复检NG"}],
      autoInspectResultOptions: [{label: "", value: ''}, {label: "设备OK", value: "设备OK"}, {label: "设备NG", value: "设备NG"}]
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
            this.formRef.current?.setFieldsValue({
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

  getUsersOptions = () => {
    usersOptions().then(res => {
      if(res.code === 0){
        let list = res.data.length>0&&res.data.map((item,index) => {
          return {
            label: item,
            value: item
          }
        });
        this.setState({
          usersOptions: list
        })
      }
    })
  }

  getTableList = () => {
    const {searchParam,page,size} = this.state;
    let formValue = this.formRef.current?.getFieldsValue();
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
        // eslint-disable-next-line array-callback-return
        let tableList = [];
        if (data.content && data.content.length > 0) {
          data.content.map((item,index) => {
            let subLength = item.subDieCastings.length;
            item.index = index+1+(page-1)*size;
            // eslint-disable-next-line array-callback-return
            subLength>0&&item.subDieCastings.map((ele,ind) => {
              if(ind === 0){
                tableList.push({
                  ...item,
                  ...ele,
                  rowSpan: subLength
                })
              }else{
                tableList.push({
                  ...item,
                  ...ele,
                  rowSpan: 0
                })
              }
            })
          })
        }

        this.setState({
          dataSource: tableList,
          searchParam: {
            page: 1,
            size: 10,
            ...formValue,
            end: searchParam.end,
            from: searchParam.from
          },
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
      if (res) {
        const {code, data} = res;
        if (code === 0) {
          this.setState({
            qualifiedCount: data.qualifiedCount,
            notQualifiedCount: data.notQualifiedCount
          })
        }
      }
    })
  }

  changeValue = (value,record,index,e) => {
    Modal.confirm({
      title: `是否要将人工复检结果修改为${e}?`,
      okText: '确认',
      cancelText: '取消',
      onOk: () => {
        reinspect({id: record.subId, status: e}).then(res=>{
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

  searchHandle = () => {
    this.getTableList()

  }

  resetHandle = () => {
    this.formRef.current?.resetFields();
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
            message.error(res.message||'确认失败！');
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
    let value = e.target.value;
    this.errMessageShow = true;
    this.barcode = value;
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

  columnRender = (value, row, index) => {
    let classNameBg = value?.includes('NG') ? 'bg-red color-white' : '';
    return <Select 
      className={classNameBg}
      defaultValue={value}
      onChange={this.changeValue.bind(this,value,row,index)}>
      <Option value=''/>
      <Option value='复检NG'>复检NG</Option>
      <Option value='复检OK'>复检OK</Option>
    </Select>
  }

  render () {
    const { columns, dataSource,totalCount,page,size,productOptions, usersOptions, manualReinspectResultOptions, autoInspectResultOptions, qualifiedCount,notQualifiedCount } = this.state;
    const formCondition = [
      {
        label: 'SR1000二维码编号',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'barcodeData',
        ref: 'barcodeRef',
        changeValue: this.changeBarcode,
        onFocus: this.focusHandle,
        col: {xs: 24, sm: 24, md: 12, lg: 12, xl: 12}
      },
      {
        label: '时间',
        controlType: 'RangePicker',
        placeholder: ['开始时间', '结束时间'],
        format: 'YYYY-MM-DD HH:mm:ss',
        key: 'date',
        showTime: true,
        col: {xs: 24, sm: 12, md: 12, lg: 12, xl: 12},
        changeValue: this.dateChange
      },
      {
        label: '产品类型',
        controlType: 'Select',
        placeholder: '请选择',
        key: 'productTypeId',
        col: {xs: 24, sm: 12, md: 6, lg: 6, xl: 6},
        options: productOptions
      },
      {
        label: '自动线检测结果',
        controlType: 'Select',
        placeholder: '请输入',
        key: 'autoInspectResult',
        col: {xs: 24, sm: 12, md: 6, lg: 6, xl: 6},
        options: autoInspectResultOptions
      },
      {
        label: '人工复检结果',
        controlType: 'Select',
        placeholder: '请输入',
        key: 'manualReinspectResult',
        col: {xs: 24, sm: 12, md: 6, lg: 6, xl: 6},
        options: manualReinspectResultOptions
      },
      {
        label: '复检人员',
        controlType: 'Select',
        placeholder: '请输入',
        key: 'reinspectBy',
        col: {xs: 24, sm: 12, md: 6, lg: 6, xl: 6},
        options: usersOptions
      },
    ];
    const rowClassName = (record) => {
      let className = '';
      let formValue = this.formRef.current?.getFieldsValue();
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
              cancelText: '取消'
            });
            this.showConfirmed = false;
          }
          className = 'bg-yellow'
          break;
        default: break;
      }

      if (record.autoInspectResult === '设备NG') {
        if (invalidGrade.includes(record.barcodeGrade?.toUpperCase())) {
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
          <Col xs={24} sm={12} md={6} lg={4} xl={4}>
            <div className='text-line'>合格数量：{ qualifiedCount }</div>
          </Col>
          <Col xs={24} sm={12} md={6} lg={4} xl={4}>
            <div className='text-line'>不合格数量：{ notQualifiedCount }</div>
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

export default FilterAscPage;