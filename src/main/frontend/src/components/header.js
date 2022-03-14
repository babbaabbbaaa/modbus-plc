import React from 'react'
import { Menu,Dropdown } from 'antd';
import { Link } from 'react-router-dom';
import Semaphore from '@/components/semaphore';
import { logout } from '@/service/common-service';
import Login from '@/components/login';

class Header extends React.Component {
	pathname = (window.location.hash).substr(2) || 'filter'
	state = {
		current: this.pathname,
		userName: '',
		roles: [],
		isLogin: false
	};

	menu = () => {
		return (
			<Menu>
				<Menu.Item>
					<div className='logout' onClick={this.logoutClick}>退出账号</div>
				</Menu.Item>
			</Menu>
		);
	} 

	logoutClick = () => {
		logout().then(res=>{
			localStorage.clear();
			window.location.reload();
		})
	}

	clickLogin = () => {
		Login.show();
	}

	componentDidMount () {
		this.getUserInfo(localStorage.getItem('userInfo')||'{}');
		window.addEventListener("setItemEvent", (e) => {
			if(e.key === 'userInfo'){
				this.getUserInfo(e.newValue||'{}');
			}
		});
	}

	getUserInfo = (value) => {
		let userInfo = value;
		userInfo = JSON.parse(userInfo);
		let loginInfo = false;
		if(userInfo.username){
			loginInfo = true;
		}
		this.setState({
			userName: userInfo.username||'',
			roles: userInfo.roles||[],
			isLogin: loginInfo
		});
	}

	handleClick = e => {
		this.setState({ current: e.key });
	}; 
	render() {
		const { current,userName,isLogin } = this.state;
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
					<Menu.Item key = "user" >
						<Link to = "/user" > 用户管理 </Link>
					</Menu.Item >
				</Menu>
				<div className='header-right'>
					<Semaphore />
					{
						isLogin?<Dropdown overlay={this.menu} placement="bottomLeft" arrow>
							<div className='user-info'>{userName}</div>
						</Dropdown>:<div className='header-login' onClick={this.clickLogin}>登录</div>
						
					}
				</div>
			</div>
		);
	}
}
export default Header;