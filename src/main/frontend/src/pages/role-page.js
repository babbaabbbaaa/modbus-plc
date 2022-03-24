import React from 'react';
import {Button, Form, message, Modal} from 'antd';
import TablePanel from '@/components/table';
import {addRole, deleteRole, roleList} from '@/service/role-service';
import OperateModal from '../components/operate-modal';

class UserPage extends React.Component {
	formRef = React.createRef();
	columns = [
		{
			title: 'ID',
			dataIndex: 'id',
			key: 'id',
			width: 80,
			align: "center"
		}, {
			title: '角色名',
			dataIndex: 'roleName',
			key: 'roleName',
			width: 400,
			align: "center"
		}, {
			title: '角色说明',
			dataIndex: 'description',
			key: 'description',
			width: 700
		}, {
			title: '操作',
			dataIndex: 'option',
			key: 'option',
			align: 'center',
			width: 700,
			render: (value, record, index) => {
				return <div>
					<Button className='submit-btn' onClick={this.editRole.bind(this, record)}>编辑</Button>
					<Button className='submit-btn' onClick={this.deleteTableRole.bind(this, record)}>删除</Button>
				</div>
			}
		},
	];

	constructor(props) {
		super(props);
		this.state = {
			dataSource: [],
			modalItem: [],
			title: '新增角色'
		}
	}

	availableRoleList = {
		"ADMIN": "管理员，拥有所有的权限。且无法被删除。",
		"MANAGER": "经理，拥有所有权限。可以在没有使用的情况下被删除。",
		"OPERATOR": "操作员，拥有重码确认以及人工复检权限。可以在没有使用的情况下被删除。",
		"ENGINEER": "工程师，拥有重码确认以及人工复检权限。可以在没有使用的情况下被删除。"
	};
	modalRoleList = [];
	createdRoleList = [];


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
			controlType: 'Select',
			placeholder: '请输入',
			key: 'roleName',
			options: this.modalRoleList,
			changeValue:(e)=>{
				this.changeRoleDefinition(e)
			},
			rules: [{
				required: true,
				message: '请输入角色名'
			}]
		},
		{
			label: '角色说明',
			controlType: 'TextArea',
			key: 'description',
			disabled: true
		},

	]

	changeRoleDefinition = (value) => {
		if(value) {
			const {editData} = this.state;
			console.log(editData);
			editData.description = this.availableRoleList[value];
			this.setState({editData: editData })
		}
	}



	componentDidMount() {
		this.getTableList();
		window.addEventListener("setItemEvent", (e) => {
			if(e.key === 'userInfo'){
				this.getTableList();
			}
		});
	}




	getTableList = () => {
		roleList().then(res => {
			const { code, data } = res;
			if (code === 0) {
				let assignedData = data.map(item => {
					item.description = this.availableRoleList[item.roleName]
					return item;
				});
				this.setState({
					dataSource: assignedData
				})

				this.createdRoleList = data.map(item => item.roleName);
				this.modalRoleList = [];
				for(let role in this.availableRoleList) {
					if (this.createdRoleList.indexOf(role) === -1) {
						let option = {};
						option.label = role;
						option.value = role;
						this.modalRoleList.push(option);
					}
				}

				this.modalItem = this.modalItem.map(item => {
					if (item.label === '角色名') {
						item.options = this.modalRoleList
					}
					return item;
				})
			}
		})
	}

	addNewRole = () => {
		this.setState({
			isModalVisible: true,
			title: '新增角色',
			editData: {}
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
		if (record.roleName === 'ADMIN') {
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
					const { code } = res;
					if (code === 0) {
						this.getTableList();
						message.success('删除成功');
					}
				});
			}
		});
	}

	changeRole = (type, data) => {
		if (type) {
			if (this.state.title === "新增角色" && this.createdRoleList.indexOf(data.roleName) > -1) {
				message.warn(data.roleName + "已经存在！");
				return;
			}

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

	render() {
		const { dataSource, isModalVisible, editData, title } = this.state;
		return <div className='page-container'>
			<Form className='page-form' name='search' ref={this.formRef}>
				<Button className='submit-btn' onClick={this.addNewRole}>新增角色</Button>
			</Form>
			<TablePanel
				columns={this.columns}
				showHeader={true}
				dataSource={dataSource}
			/>
			{isModalVisible && <OperateModal
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