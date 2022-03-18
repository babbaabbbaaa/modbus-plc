import React from 'react';
import {Form,Button,message,Modal} from 'antd';
import TablePanel from '@/components/table';
import {roleList, addRole, deleteRole} from '@/service/role-service';
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
			title: '角色名',
			dataIndex: 'roleName',
			key: 'roleName',
			width: 160
		},{
			title: '操作',
			dataIndex: 'option',
			key: 'option',
			align: 'center',
			width: 200,
			render: (value,record,index) => {
				return <div>
					<Button className='submit-btn' onClick={this.editRole.bind(this,record)}>编辑</Button>
					<Button className='submit-btn' onClick={this.deleteTableRole.bind(this,record)}>删除</Button>
				</div> 
			}
		},
	];
  constructor(props){
    super(props);
    this.state = { 
      dataSource: [],
			modalItem: [],
			title: '新增角色'
    }
  }
	
	modalItem = [
		{
			label: 'ID',
			controlType: 'Input',
			placeholder: '请输入',
			key: 'id',
			disabled: true
		}, 
		{
			label: '角色名',
			controlType: 'Input',
			placeholder: '请输入',
			key: 'roleName',
			rules: [{
				required: true,
				message: '请输入角色名'
			}]
		}, 
		
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
    roleList().then(res=>{
			const {code,data} = res;
			if(code === 0){
				this.setState({
					dataSource: data
				})
			}
    })
  }

	addNewRole = () => {
		this.setState({
			isModalVisible: true,
			title: '新增角色'
		})
	}

	editRole = (record) => {
		let data = {
			id: record.id,
			roleName: record.roleName,
		}
		this.setState({
			title: '更新角色',
			editData: data,
			isModalVisible: true
		})
	}

	deleteTableRole = (record) => {
		if(record.roleName === 'ADMIN') {
			message.warn("管理员无法被删除！")
			return;
		}
		Modal.confirm({
			title: '删除角色',
			content: `您确认要删除角色${record.roleName}吗？`,
			onOk: () => {
				let params = {
					roleName: record.roleName,
					id: record.id
				}
				deleteRole(params).then(res => {
					const {code} = res;
					if(code === 0){
						this.getTableList();
						message.success('删除成功');
					}
				});
			}
		});
	}

	changeRole =  (type, data) => {
		if (type) {
			let params = {
				roleName: data.roleName,
				id: data.id
			}
			addRole(params).then(res => {
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
        <Button className='submit-btn' onClick={this.addNewRole}>新增角色</Button>
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
				operateModalSure={this.changeRole}
			/>}
    </div>
  }
}

export default UserPage;