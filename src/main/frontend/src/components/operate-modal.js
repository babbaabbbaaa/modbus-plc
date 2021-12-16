import React, { useState, useEffect } from 'react';
import { Form,Row,Col,Modal, Button } from 'antd';
import FormCondition from '@/components/form-condition';

const OperateModal = (props) => {
  const { isModalVisible,addConfigItem,title,modalContent,data} = props;
  const [form] = Form.useForm();
  // const [isModalVisible, setIsModalVisible] = useState(false);

  useEffect(() => {
    if (data&&title!=='新增配置') {
      form.setFieldsValue({
        ...data
      });
    }else{
      form.resetFields();
    }
  });

  const handleOk = () => {
    form.validateFields()
    .then(values => {
      addConfigItem(true,values)
    })
    .catch(errorInfo => {
      console.log(errorInfo)
    });
  };

  const handleCancel = () => {
    addConfigItem(false)
  };

  return (
    <Modal 
      title={title} 
      closable={false}
      maskClosable={false}
      visible={isModalVisible} 
      onOk={handleOk} 
      onCancel={handleCancel}
      footer={[
        <Button key="back" onClick={handleCancel}>取消</Button>,
        <Button key="submit" type="primary" onClick={handleOk}>确定</Button>]}
    >
      <Form className='page-form' name='search' form={form}>
        <Row>
          {
            modalContent.map(condition => {
              return <Col key={condition.key} xs={24} sm={24} md={24} lg={24} xl={24}>
                <Form.Item name={condition.key} label={condition.label} rules={condition.rules}>
                    {FormCondition(condition)}
                </Form.Item>
              </Col>
            })
          }
        </Row>
      </Form>
    </Modal>
);
};

export default OperateModal;