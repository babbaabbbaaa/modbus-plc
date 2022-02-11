import React from 'react';
import {Form,Button,message} from 'antd';
import TablePanel from '@/components/table';
import {userChange,userList,roleList} from '@/service/user-service';
import OperateModal from '../components/operate-modal';

class UserPage extends React.Component{
  formRef = React.createRef();
	columns = [
		{
			title: 'ID',
			dataIndex: 'id',
			key: 'id',
			width: 80
		},{
			title: '用户名',
			dataIndex: 'username',
			key: 'username',
			width: 160
		},{
			title: '卡号',
			dataIndex: 'cardNumber',
			key: 'cardNumber',
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
							return <span>{item.roleName},</span>
						})
					}
				</div>
			}
		},{
			title: '操作',
			dataIndex: 'option',
			key: 'option',
			align: 'center',
			width: 100,
			render: (value,record,index) => {
				return  <Button className='submit-btn' onClick={this.editUser.bind(this,value,record,index)}>编辑</Button>
			}
		},
	];
  constructor(props){
    super(props);
    this.state = { 
      dataSource: [],
      roleList: [],
			modalItem: [],
			title: '新增用户'
    }
  }
  componentDidMount () {
		this.setState({
			modalItem: [
				{
					label: 'ID',
					controlType: 'Input',
					placeholder: '请输入',
					key: 'id',
					disabled: true
				}, 
				{
					label: '用户名',
					controlType: 'Input',
					placeholder: '请输入',
					key: 'username',
					rules: [{
            required: true,
            message: '请输入用户名'
        }]
				}, 
				{
					label: '卡号',
					controlType: 'Input',
					placeholder: '请输入',
					key: 'cardNumber',
					rules: [{
            required: true,
            message: '请输入卡号'
        }]
				}, 
				{
					label: '角色',
					controlType: 'Select',
					placeholder: '请选择',
					key: 'roles',
					mode: 'multiple',
					options: this.state.roleList,
					rules: [{
            required: true,
            message: '请选择角色'
        }]
				}
			]
		})
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
			if(code === 0){
				let list = data.map(item => {
					item.label = item.roleName;
					item.value = item.roleName;
					return item;
				})
				this.setState({
					roleList: list
				})
			}
    })
  }

	addNewUser = () => {
		this.setState({
			isModalVisible: true,
			title: '新增用户'
		})
	}

	editUser = (value,record,index) => {
		let roles = record.roles.length>0&&record.roles.map(item=> {
			return item.roleName
		})
		let data = {
			id: record.id,
			username: record.username,
			roles: roles,
			cardNumber: record.cardNumber
		}
		console.log(roles)
		this.setState({
			title: '',
			editData: data,
			isModalVisible: true
		})
	}

	changeUser =  (type, data) => {
		
		if (type) {
			// eslint-disable-next-line array-callback-return
			let roles = this.state.roleList.filter(item => {
				if(data.roles.indexOf(item.roleName)> -1){
					return item;
				}
			});
			let params = {
				cardNumber: data.cardNumber,
				id: data.id,
				username: data.username,
				roles: roles
			}
			userChange(params).then(res => {
					message.success(`${this.state.title}成功！`);
					this.getTableList();
					this.setState({
						editData: {},
						isModalVisible: false
					});
			})
		} else {
			this.setState({
				isModalVisible: false
			})
		}
	}

  render () {
    const { dataSource,isModalVisible,editData,title,modalItem } = this.state;
    return <div className='page-container'>
      <Form className='page-form' name='search' ref={this.formRef}>
        <Button className='submit-btn' onClick={this.addNewUser}>新增用户</Button>
      </Form>
      <TablePanel
        columns={this.columns}
        showHeader={true}
        dataSource={dataSource}
      />
			{isModalVisible&&<OperateModal
				isModalVisible={isModalVisible}
				title={title}
				data={editData}
				modalContent={modalItem}
				operateModalSure={this.changeUser}
			/>}
    </div>
  }
}

export default UserPage;