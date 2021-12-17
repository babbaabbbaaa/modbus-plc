import React from 'react';
import {Form,Row,Col,Button,Modal,message} from 'antd';
import {testSearch} from '@/service/test-service';
import FormCondition from '@/components/form-condition';

class TestPage extends React.Component{
  
  formRef = React.createRef();
  testFormList = [
    {
      label: 'SR1000二维码编号',
      controlType: 'Input',
      key: 'barcodeData',
      disabled: true
    },
    {
      label: '二维码字符提取',
      controlType: 'Input',
      key: 'barcode',
      disabled: true
    },
    {
      label: '通规功能',
      controlType: 'Input',
      key: 'generalFunc',
      disabled: true
    },
    {
      label: '测高功能',
      controlType: 'Input',
      key: 'heightFunc',
      disabled: true
    },
    {
      label: '旋转错误检测',
      controlType: 'Input',
      key: 'spinCheckFunc',
      disabled: true
    },
    {
      label: '读码功能',
      controlType: 'Input',
      key: 'scanFunc',
      disabled: true
    },
    {
      label: '台风功能',
      controlType: 'Input',
      key: 'typhoonFunc',
      disabled: true
    },
    {
      label: '槽深检测功能',
      controlType: 'Input',
      key: 'slotDepthFunc',
      disabled: true
    },
    {
      label: '打标功能',
      controlType: 'Input',
      key: 'flagFunc',
      disabled: true
    },
    {
      label: '焊缝功能',
      controlType: 'Input',
      key: 'weldFunc',
      disabled: true
    },
    {
      label: '测高数值1',
      controlType: 'Input',
      key: 'heightMeasure1',
      disabled: true
    },
    {
      label: '测高数值2',
      controlType: 'Input',
      key: 'heightMeasure2',
      disabled: true
    },
    {
      label: '测高数值3',
      controlType: 'Input',
      key: 'heightMeasure3',
      disabled: true
    },
    {
      label: '测高数值4',
      controlType: 'Input',
      key: 'heightMeasure4',
      disabled: true
    },
    {
      label: '测高数值5',
      controlType: 'Input',
      key: 'heightMeasure5',
      disabled: true
    },
    {
      label: '测高数值6',
      controlType: 'Input',
      key: 'heightMeasure6',
      disabled: true
    },
    {
      label: '测高数值7',
      controlType: 'Input',
      key: 'heightMeasure7',
      disabled: true
    },
    {
      label: '测高数值8',
      controlType: 'Input',
      key: 'heightMeasure8',
      disabled: true
    },
    {
      label: '测高数值9',
      controlType: 'Input',
      key: 'heightMeasure9',
      disabled: true
    },
    {
      label: '测高数值10',
      controlType: 'Input',
      key: 'heightMeasure10',
      disabled: true
    },
    {
      label: '测高数值11',
      controlType: 'Input',
      key: 'heightMeasure11',
      disabled: true
    },
    {
      label: '测高数值12',
      controlType: 'Input',
      key: 'heightMeasure12',
      disabled: true
    },
    {
      label: '测高数值13',
      controlType: 'Input',
      key: 'heightMeasure13',
      disabled: true
    },
    {
      label: '测高数值14',
      controlType: 'Input',
      key: 'heightMeasure14',
      disabled: true
    },
    {
      label: '测高数值15',
      controlType: 'Input',
      key: 'heightMeasure15',
      disabled: true
    },
    {
      label: '测高数值16',
      controlType: 'Input',
      key: 'heightMeasure16',
      disabled: true
    },
    {
      label: '测高数值17',
      controlType: 'Input',
      key: 'heightMeasure17',
      disabled: true
    },
    {
      label: '测高数值18',
      controlType: 'Input',
      key: 'heightMeasure18',
      disabled: true
    },
    {
      label: '测高数值19',
      controlType: 'Input',
      key: 'heightMeasure19',
      disabled: true
    },
    {
      label: '测高数值20',
      controlType: 'Input',
      key: 'heightMeasure20',
      disabled: true
    },
    {
      label: '测高系数(配置)',
      controlType: 'Input',
      key: 'ratio',
      disabled: true
    },
    {
      label: '重码标记(7002)',
      controlType: 'Input',
      key: 'duplicate',
      disabled: true
    },
    {
      label: '产品类型(7003)',
      controlType: 'Input',
      key: 'productTypeId',
      disabled: true
    },

  ]

  testHandle = () => {
    testSearch().then(res => {
      if(res.code === 0){
        this.formRef.current.setFieldsValue({...res.data})
      }
    })
  }

  render () {
    return <div className='page-container'>
      <Form className='page-form' name='search' ref={this.formRef}>
        <Row>
        <Col xs={24} sm={24} md={24} lg={24} xl={24}>
            <Button className="btn submit-btn" onClick={this.testHandle}>测试</Button>
          </Col>
          {
            this.testFormList.map(condition => {
              return <Col key={condition.key} xs={24} sm={24} md={12} lg={8} xl={8}>
                <Form.Item name={condition.key} label={condition.label} rules={condition.rules}>
                    {FormCondition(condition)}
                </Form.Item>
              </Col>
            })
          }
        </Row>
      </Form>
    </div>
  }
}

export default TestPage;