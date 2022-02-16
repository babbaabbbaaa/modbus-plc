import React from 'react';
import { Form, Row, Col, Button, Modal, message } from 'antd';
import { testSearch } from '@/service/test-service';
import FormCondition from '@/components/form-condition';

class TestAscPage extends React.Component {

	formCommon = React.createRef();
	formA = React.createRef();
	formB = React.createRef();

	testFormList = [
		{
			name: '通用数据',
			formRef: this.formCommon,
			formList: [
				{
					label: '时间',
					controlType: 'Input',
					key: 'logTime',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '模具号(D7004)',
					controlType: 'Input',
					key: 'moldNo',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '压射号(D7005)',
					controlType: 'Input',
					key: 'injectionNo',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
                {
                    label: '产品类型(D7003)',
                    controlType: 'Input',
                    key: 'productTypeId',
                    disabled: true,
                    labelCol: {
                        span: 8
                    }
                }
			]
		},
		{
			name: 'A工位',
			formRef: this.formA,
			formList: [
				{
					label: '流水号(D7007)',
					controlType: 'Input',
					key: 'serialNumber',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '二维码编号(D7054~D7103)',
					controlType: 'Input',
					key: 'barcodeData',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
                {
					label: '二维码截取',
					controlType: 'Input',
					key: 'barcode',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
                {
					label: '二维码等级',
					controlType: 'Input',
					key: 'barcodeGrade',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '读码判定结果(D7009)',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重差值(D7018/D7019)',
					controlType: 'Input',
					key: 'weightDifference',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重判定结果',
					controlType: 'Input',
					key: 'weightResult',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重公差上限(D7020/D7021)',
					controlType: 'Input',
					key: 'maxWeightTolerance',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重公差下限(D7022/D7023)',
					controlType: 'Input',
					key: 'minWeightTolerance',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重压铸前(D7014/D7015)',
					controlType: 'Input',
					key: 'weightBeforeDieCasting',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重压铸后(D7016/D7017)',
					controlType: 'Input',
					key: 'weightAfterDieCasting',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '使用产品/穴数选择(D7006)',
					controlType: 'Input',
					key: 'holeSelection',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '打标使用状态(D7008)',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 8
					}
				}
			]
		},
		{
			name: 'B工位',
			formRef: this.formB,
			formList: [
				{
					label: '流水号(D7011)',
					controlType: 'Input',
					key: 'serialNumber',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '二维码编号(D7104~D7153)',
					controlType: 'Input',
					key: 'barcodeData',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
                {
					label: '二维码截取',
					controlType: 'Input',
					key: 'barcode',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
                {
					label: '二维码等级',
					controlType: 'Input',
					key: 'barcodeGrade',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '读码判定结果(D7013)',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重差值(D7018/D7019)',
					controlType: 'Input',
					key: 'weightDifference',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重判定结果',
					controlType: 'Input',
					key: 'weightResult',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重公差上限(D7030/D7031)',
					controlType: 'Input',
					key: 'maxWeightTolerance',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重公差下限(D7032/D7033)',
					controlType: 'Input',
					key: 'minWeightTolerance',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重压铸前(D7024/D7025)',
					controlType: 'Input',
					key: 'weightBeforeDieCasting',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '铝重压铸后(D7026/D7027)',
					controlType: 'Input',
					key: 'weightAfterDieCasting',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '使用产品/穴数选择(D7010)',
					controlType: 'Input',
					key: 'holeSelection',
					disabled: true,
					labelCol: {
						span: 8
					}
				},
				{
					label: '打标使用状态(D7012)',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 8
					}
				}
			]
		}
	]

	testHandle = () => {
		
		this.formCommon.current.setFieldsValue({});
		this.formA.current.setFieldsValue({});
		this.formB.current.setFieldsValue({});
    
		testSearch().then(res => {
			if (res.code === 0) {
				this.formCommon.current.setFieldsValue({
					logTime: res.data.logTime,
					moldNo: res.data.moldNo,
					injectionNo: res.data.injectionNo,
					productTypeId: res.data.productTypeId
				})
				this.formA.current.setFieldsValue({...res.data.subDieCastings[0]});
				
				this.formB.current.setFieldsValue({...res.data.subDieCastings[1]})
			}
		})
	}

	render() {
		return <div className = 'page-container page-form' >
			<Button className = "btn submit-btn" onClick = { this.testHandle } > 测试 </Button> 
			{
				this.testFormList.map(item => {
					return <div className='test-form-item'>
						<div className='test-form-item-name'>{item.name}</div>
						<Form name = 'search' ref = { item.formRef } >
							<Row >
								{
									item.formList.map(condition => {
										return <Col key = { condition.key }
											xs = { 24 }
											sm = { 24 }
											md = { 12 }
											lg = { 8 }
											xl = { 8 } >
											<Form.Item name = { condition.key }
												labelCol = { condition.labelCol }
												label = { condition.label }
												rules = { condition.rules } >
													{ FormCondition(condition) } 
											</Form.Item> 
										</Col>
									})
								}
							</Row> 
						</Form > 
					</div>
				})
			} 
		</div>
	}
}

export default TestAscPage;