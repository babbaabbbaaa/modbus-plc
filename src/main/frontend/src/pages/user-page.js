import React from 'react';
import {Form,Row,Col,Button,Modal,message} from 'antd';
import TablePanel from '@/components/table';
import columns from '@/column/filter-column';
import {userChange,userList,roleList} from '@/service/user-service';
import FormCondition from '@/components/form-condition';
import {download} from '@/utils/index';

let invalidQualified = ['C', 'D', 'E', 'F'];
class UserPage extends React.Component{
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

  constructor(props){
    super(props);
    this.state = { 
      columns: [
				{
					title: 'ID',
					dataIndex: 'index',
					key: 'index',
					width: 80
				},{
					title: '用户名',
					dataIndex: 'username',
					key: 'username',
					width: 160
				},{
					title: '卡号',
					dataIndex: 'index',
					key: 'index',
					width: 260
				},{
					title: '角色',
					dataIndex: 'roles',
					key: 'roles',
					width: 260,
					render: (value,record,index) => {
						return  <div>
							{
								record.roles.map(item => {
									return <span>{item.roleName}</span>
								})
							}
						</div>
					}
				},
			],
      dataSource: [],
      page: 1,
      size: 10,
      totalCount: 0,
      roleList: [],
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
    this.getRoleList();
    
  }
  
  getTableList = () => {
    userList().then(res=>{
			const {code,data} = res;
			if(code === 0){
				this.setState({
					dataSource: data
				})
			}
    })
  }

  getRoleList = () => {
    roleList().then(res=>{
			const {code,data} =res;
			this.setState({
				roleList: data
			})
    })
  }

  render () {
    const { columns, dataSource } = this.state;
    return <div className='page-container'>
      <Form className='page-form' name='search' ref={this.formRef}>
        <Button>新增用户</Button>
      </Form>
      <TablePanel
        columns={columns}
        showHeader={true}
        dataSource={dataSource}
      />
    </div>
  }
}

export default UserPage;