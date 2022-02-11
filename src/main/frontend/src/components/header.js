import React from 'react'
import { Menu,Dropdown } from 'antd';
import { Link } from 'react-router-dom';
import Semaphore from '@/components/semaphore';
import { logout } from '@/service/common-service';

class Header extends React.Component {
	pathname = (window.location.hash).substr(2) || 'filter'
	state = {
		current: this.pathname,
		userName: '',
		roles: []
	};

	menu = (
		<Menu>
			<Menu.Item>
				<div className='logout' onClick={this.logout}>退出账号</div>
			</Menu.Item>
		</Menu>
	);

	logout = () => {
		logout().then(res=>{
			localStorage.clear()
		})
	}

	componentDidMount () {
		let userInfo = localStorage.getItem('userInfo')||'{}';
		userInfo = JSON.parse(userInfo);
		this.setState({
			userName: userInfo.username||'',
			roles: userInfo.roles||[]
		})
			
	}

	handleClick = e => {
		console.log('click ', e);
		this.setState({ current: e.key });
	}; 
	render() {
		const { current,userName,roles } = this.state;
		return (
			<div className = 'page-header'>
				<Menu onClick = { this.handleClick } selectedKeys = {[current]} mode = "horizontal" >
					<Menu.Item key = "filter" >
						<Link to = "/" > PLC 控制器 </Link> 
					</Menu.Item > 
					<Menu.Item key = "test" >
						<Link to = "/test" > PLC 测试 </Link> 
					</Menu.Item > 
					<Menu.Item key = "config" >
						<Link to = "/config" > 安全控制器 </Link>
					</Menu.Item > 
					{ roles.indexOf('ADMIN')>-1&&
						<Menu.Item key = "user" >
							<Link to = "/user" > 用户管理 </Link>
						</Menu.Item > 
					}
				</Menu>
				<div className='header-right'>
					<Semaphore />
					<Dropdown overlay={this.menu} placement="bottomLeft" arrow>
						<div className='user-info'>{userName}</div>
					</Dropdown>
				</div>
			</div>
		);
	}
}
export default Header;