import React, { PureComponent } from 'react';
import ReactDOM from 'react-dom';
import { Form,Row,Col,Modal, Button,Input } from 'antd';
import {secureLogin} from '@/service/config-service';
import md5 from 'md5';

class Login extends PureComponent {
  formRef = React.createRef();
	constructor(props) {
		super(props);
		this.state = {
			visible: false
		};
	}
  

	show() {
		this.setState({ visible: true });
	}

	handleCancel = () => {
		this.setState({ visible: false });
	}

  handleOk = () => {
    this.formRef.current.validateFields()
    .then(values => {
      console.log(values.password)
      console.log(md5(values.password))
      secureLogin(md5(values.password)).then(res => {
        if(res.code === 0){
          this.setState({
            visible: false
          })
          window.location.reload();
        }
      })
    })
    .catch(errorInfo => {
      console.log(errorInfo)
    });
  };

	render() {
		const { visible } = this.state;
		return (
			<Modal 
      title='请输入密码' 
      closable={false}
      maskClosable={false}
      visible={visible} 
      onOk={this.handleOk} 
      onCancel={this.handleCancel}
      footer={[
        <Button key="back" onClick={this.handleCancel}>取消</Button>,
        <Button key="submit" type="primary" onClick={this.handleOk}>确定</Button>]}
    >
      <Form className='page-form' name='search' ref={this.formRef}>
        <Row>
          <Col xs={24} sm={24} md={24} lg={24} xl={24}>
            <Form.Item name='password' label='密码'>
              <Input type="password"/>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </Modal>
		);
	}
}

const div = document.createElement('div');
document.body.appendChild(div);
 
const BoxLogin = ReactDOM.render(React.createElement(Login), div);
export default BoxLogin;