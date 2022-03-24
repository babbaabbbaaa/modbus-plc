import React from 'react';
import {Form,Button,message,Modal} from 'antd';
import TablePanel from '@/components/table';
import {userChange,userList,roleList,deleteUser} from '@/service/user-service';
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
			width: 200,
			render: (value,record,index) => {
				return <div>
					<Button className='submit-btn' onClick={this.editUser.bind(this,record)}>编辑</Button>
					<Button className='submit-btn' onClick={this.deleteTableUser.bind(this,record)}>删除</Button>
				</div> 
			}
		},
	];
  constructor(props){
    super(props);
    this.state = { 
      dataSource: [],
			modalItem: [],
			title: '新增用户'
    }
    this.getRoleList();
  }
	
	roleList = [];
	modalItem = [
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
			options: this.roleList,
			rules: [{
				required: true,
				message: '请选择角色'
			}]
		}
	]
  componentDidMount () {
    this.getTableList();
		window.addEventListener("setItemEvent", (e) => {
			if(e.key === 'userInfo'){
				this.getTableList();
			}
		});
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

  getRoleList = async() => {
		let list = [];
    const {code,data} = await roleList();
		if(code === 0){
			list = data.map(item => {
				item.label = item.roleName;
				item.value = item.roleName;
				return item;
			})
			this.roleList = list;
			this.modalItem = this.modalItem.map(item => {
				if(item.label === '角色'){
					item.options = list
				}
				return item;
			})
		}
  }

	addNewUser = () => {
		this.setState({
			isModalVisible: true,
			title: '新增用户',
			editData: {}
		})
	}

	editUser = (record) => {
		let roles = record.roles.length>0&&record.roles.map(item=> {
			return item.roleName
		})
		let data = {
			id: record.id,
			username: record.username,
			roles: roles,
			cardNumber: record.cardNumber
		}
		this.setState({
			title: '',
			editData: data,
			isModalVisible: true
		})
	}

	deleteTableUser = (record) => {
		Modal.confirm({
			title: '删除用户',
			content: `您确认要删除用户${record.username}吗？`,
			onOk: () => {
				let params = {
					cardNumber: record.cardNumber,
					id: record.id,
					username: record.username,
					roles: record.roles
				}
				deleteUser(params).then(res => {
					const {code} = res;
					if(code === 0){
						this.getTableList();
						message.success('删除成功');
					}
				});
			}
		});
	}

	changeUser =  (type, data) => {
		if (type) {
			// eslint-disable-next-line array-callback-return
			let roles = this.roleList.filter(item => {
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
    const { dataSource,isModalVisible,editData,title } = this.state;
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
				modalContent={this.modalItem}
				operateModalSure={this.changeUser}
			/>}
    </div>
  }
}

export default UserPage;