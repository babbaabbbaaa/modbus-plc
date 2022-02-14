import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import { Form,Row,Col,Modal, Button,Input } from 'antd';
import {login} from '@/service/common-service';
import md5 from 'md5';

class Login extends PureComponent {
  formRef = React.createRef();
	constructor(props) {
		super(props);
		this.state = {
			visible: false,
      passwordValue: ''
		};
	}
  

	show() {
		this.setState({ visible: true },()=>{
      let timer = setTimeout(()=>{
        clearTimeout(timer);
        console.log(document.getElementById('loginInput'))
        document.getElementById('loginInput').focus();
      },5)
    });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

  handleOk = () => {
    const { passwordValue } = this.state;
    let params = new FormData();
    params.append('username',passwordValue);
    params.append('password',passwordValue);
    login(params).then(res => {
      if(res.code === 0){
        localStorage.setItem('userInfo',JSON.stringify(res.data))
        this.setState({
          visible: false
        })
        window.location.reload();
      }
    })
    .catch(errorInfo => {
      console.log(errorInfo)
    });
  };

  keyDownHandle = (e) => {
    if (e.keyCode === 13) {
			this.handleOk();
		}
  }

  changeHandle = (e) => {
    let value = e.target.value;
    if(value.indexOf('↵')>-1){
      let data = value.split('↵')[0];
      console.log(data)
      this.setState({
        passwordValue: data
      },this.handleOk)
    }else{
      this.setState({
        passwordValue: value
      })
    }
  }

	render() {
		const { visible,passwordValue } = this.state;
		return (
			<Modal 
      title='请输入' 
      closable={false}
      maskClosable={false}
      visible={visible} 
      onOk={this.handleOk} 
      onCancel={this.handleCancel}
      footer={[
        <Button key="back" onClick={this.handleCancel}>取消</Button>,
        <Button key="submit" type="primary" onClick={this.handleOk}>确定</Button>]}
    >
      <Row className='page-form'>
        <Col xs={24} sm={24} md={24} lg={24} xl={24}>
          <Form.Item name='password'>
            <Input id='loginInput' type="password" value={passwordValue} onKeyDown={this.keyDownHandle} onChange={this.changeHandle}/>
          </Form.Item>
        </Col>
      </Row>
    </Modal>
		);
	}
}

const div = document.createElement('div');
document.body.appendChild(div);
 
const BoxLogin = ReactDOM.render(React.createElement(Login), div);
export default BoxLogin;