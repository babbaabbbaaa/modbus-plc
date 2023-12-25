import React from 'react';
import { Form, Row, Col, Button, Modal, message } from 'antd';
import { testSearch } from '@/service/test-service';
import FormCondition from '@/components/form-condition';

class TestPage extends React.Component {

    formRef = React.createRef();
    testFormList = [{
            label: '二维码编号(D7054~D7153)',
            controlType: 'Input',
            key: 'barcodeData',
            disabled: true,
            col: {xs:24, sm:24,md:12,lg:12,xl:12},
            labelCol: {span: 6}
        },
        {
            label: '二维码字符提取',
            controlType: 'Input',
            key: 'barcode',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '二维码等级',
            controlType: 'Input',
            key: 'barcodeGrade',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '托盘识别号',
            controlType: 'Input',
            key: 'trayIdentityCode',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '通规功能(D7006)',
            controlType: 'Input',
            key: 'generalFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高功能(D7007)',
            controlType: 'Input',
            key: 'heightFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '旋转错误检测(D7008)',
            controlType: 'Input',
            key: 'spinCheckFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '读码功能(D7009)',
            controlType: 'Input',
            key: 'scanFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '台风功能(D7010)',
            controlType: 'Input',
            key: 'typhoonFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '槽深检测功能(D7011)',
            controlType: 'Input',
            key: 'slotDepthFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '打标功能(D7012)',
            controlType: 'Input',
            key: 'flagFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '焊缝功能(D7013)',
            controlType: 'Input',
            key: 'weldFunc',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值1(D7014)',
            controlType: 'Input',
            key: 'heightMeasure1',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值2(D7015)',
            controlType: 'Input',
            key: 'heightMeasure2',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值3(D7016)',
            controlType: 'Input',
            key: 'heightMeasure3',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值4(D7017)',
            controlType: 'Input',
            key: 'heightMeasure4',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值5(D7018)',
            controlType: 'Input',
            key: 'heightMeasure5',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值6(D7019)',
            controlType: 'Input',
            key: 'heightMeasure6',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值7(D7020)',
            controlType: 'Input',
            key: 'heightMeasure7',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值8(D7021)',
            controlType: 'Input',
            key: 'heightMeasure8',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值9(D7022)',
            controlType: 'Input',
            key: 'heightMeasure9',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值10(D7023)',
            controlType: 'Input',
            key: 'heightMeasure10',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值11(D7024)',
            controlType: 'Input',
            key: 'heightMeasure11',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值12(D7025)',
            controlType: 'Input',
            key: 'heightMeasure12',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值13(D7026)',
            controlType: 'Input',
            key: 'heightMeasure13',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值14(D7027)',
            controlType: 'Input',
            key: 'heightMeasure14',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值15(D7028)',
            controlType: 'Input',
            key: 'heightMeasure15',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值16(D7029)',
            controlType: 'Input',
            key: 'heightMeasure16',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值17(D7030)',
            controlType: 'Input',
            key: 'heightMeasure17',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值18(D7031)',
            controlType: 'Input',
            key: 'heightMeasure18',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值19(D7032)',
            controlType: 'Input',
            key: 'heightMeasure19',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高数值20(D7033)',
            controlType: 'Input',
            key: 'heightMeasure20',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '测高系数(配置)',
            controlType: 'Input',
            key: 'ratio',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '重码标记(7002)',
            controlType: 'Input',
            key: 'duplicate',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },
        {
            label: '产品类型(7003)',
            controlType: 'Input',
            key: 'productTypeId',
            disabled: true,
            col: {xs:24, sm:24,md:6,lg:6,xl:6},
            labelCol: {span: 12}
        },

    ]

    testHandle = () => {
        this.formRef.current?.setFieldsValue({})
        testSearch().then(res => {
            if (res.code === 0) {
                this.formRef.current?.setFieldsValue({...res.data })
            }
        })
    }

    render() {
        return <div className = 'page-container' >
            <Form className = 'page-form' name = 'search' ref = { this.formRef } >
            <Row >
                <Col xs = { 24 }
                    sm = { 24 }
                    md = { 24 }
                    lg = { 24 }
                    xl = { 24 } >
                    <Button className = "btn submit-btn" onClick = { this.testHandle } > 测试 </Button>
                </Col>
                {
                    this.testFormList.map(condition => {
                        const {col} = condition;
                        return <Col className = "form-item-mark" key = { condition.key }
                            xs = { col.xs }
                            sm = { col.sm }
                            md = { col.md }
                            lg = { col.lg }
                            xl = { col.xl } >
                            <Form.Item name = { condition.key }
                                labelCol = { condition.labelCol }
                                label = { condition.label }
                                rules = { condition.rules }
                                labelAlign={"right"}>
                                    { FormCondition(condition) }
                            </Form.Item>
                        </Col>
                    })
                }
                </Row>
            </Form >
        </div>
    }
}

export default TestPage;