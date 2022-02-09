import React from 'react';
import {Form,Row,Col,Button,Modal,message} from 'antd';
import TablePanel from '@/components/table';
import columns from '@/column/filter-asc-column';
import {searchList,confirmItem,exportList,configOption} from '@/service/filter-service';
import FormCondition from '@/components/form-condition';
import {download} from '@/utils/index';

let invalidQualified = ['C', 'D', 'E', 'F'];
class FilterPage extends React.Component{
  formRef = React.createRef();

  constructor(props){
    super(props);
    this.state = { 
      columns: [...columns],
      dataSource: [],
      page: 1,
      size: 10,
      totalCount: 0,
      qualifiedNum: 0,
      failedNum: 0,
      searchParam: {
        end: null,
        from: null,
        productTypeId: '',
        barcode: '',
        barcodeData: '',
        productOptions: [],
        qualifiedList:[]
      }
    }
  }
  timer = null;
  componentDidMount () {
    this.getTableList();
    this.getConfigOption();
    this.timer = setInterval(()=>{
      this.getTableList()
    },1000)
    this.setState({
      qualifiedList: [{label: "合格", value: 1}, {label: "不合格", value: 0}]
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
    this.setState({
      searchParam: {
        page: 1,
        size: 10,
        ...formValue,
        end: searchParam.end,
        from: searchParam.from
      }
    })
    let params = {
      page,
      size,
      ...formValue,
      end: searchParam.end,
      from: searchParam.from
    }
    searchList(params).then(res => {
      const {code,data} = res;
      if(code === 0){
        // eslint-disable-next-line array-callback-return
        let tableList = [];
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
        console.log(tableList)
        this.setState({
          dataSource: tableList,
          totalCount: data.totalElements
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
            message.error(res.message||'确认失败！');
          }
        })
      }
    });
  }

  onPageChange = (page) => {
    this.setState({ page: page }, this.getTableList);
  }

  onSizeChange = (current, pageSize) => {
    this.setState({ page: 1, size: pageSize }, this.getTableList);
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

  render () {
    const { columns, dataSource,totalCount,page,size,productOptions, qualifiedList,qualifiedNum, failedNum } = this.state;
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
        col: {xs:24, sm:12,md:8,lg:8,xl:6},
        changeValue: this.dateChange
      },
      {
        label: 'SR1000二维码编号',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'barcodeData',
        col: {xs:24, sm:12,md:8,lg:8,xl:6}
      },
      {
        label: '二维码字符提取',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'barcode',
        col: {xs:24, sm:12,md:8,lg:8,xl:6}
      },
      {
        label: '自动线检测结果',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'autoInspectResult',
        col: {xs:24, sm:12,md:10,lg:10,xl:8}
      },
      {
        label: '人工复检结果',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'manualReinspectResult',
        col: {xs:24, sm:12,md:10,lg:10,xl:8}
      },
      {
        label: '复检人员',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'reinspectBy',
        col: {xs:24, sm:12,md:10,lg:10,xl:8}
      },
    ];
    const rowClassName = (record) => {
      let className = '';
      switch(record.duplicated){
        case 'DUP': className = 'bg-red';
          break;
        case 'CONFIRMED' : className = 'bg-yellow'
          break;
        default: break;
      }

      if (record.barcodeQualify) {
        if (invalidQualified.includes(record.barcodeQualify.toUpperCase())) {
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
              return <Col key={condition.key} xs={24} sm={12} md={8} lg={8} xl={6}>
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