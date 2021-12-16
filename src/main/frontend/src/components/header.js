import React from 'react'
import {Menu} from 'antd';
import {Link} from 'react-router-dom';

class Header extends React.Component {
  pathname = (window.location.hash).substr(2)||'filter'
  state = {
    current: this.pathname,
  };

  handleClick = e => {
    console.log('click ', e);
    this.setState({ current: e.key });
  };

  render() {
    const { current } = this.state;
    return (
      <Menu className='page-header' onClick={this.handleClick} selectedKeys={[current]} mode="horizontal">
        <Menu.Item key="filter">
          <Link to="/">PLC 控制器</Link>
        </Menu.Item>
        <Menu.Item key="test">
          <Link to="/test">PLC 测试</Link>
        </Menu.Item>
        <Menu.Item key="config">
          <Link to="/config">安全控制器</Link>
        </Menu.Item>
      </Menu>
    );
  }
}
export default Header;