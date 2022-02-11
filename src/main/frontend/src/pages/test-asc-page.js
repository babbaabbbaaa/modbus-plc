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
						span: 9
					}
				},
				{
					label: '模具号',
					controlType: 'Input',
					key: 'moldNo',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '压射号',
					controlType: 'Input',
					key: 'injectionNo',
					disabled: true,
					labelCol: {
						span: 9
					}
				}
			]
		},
		{
			name: '数据A',
			formRef: this.formA,
			formList: [
				{
					label: '流水号',
					controlType: 'Input',
					key: 'serialNumber',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: 'SR1000二维码编号',
					controlType: 'Input',
					key: 'barcodeData',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '读码判定结果',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重差值',
					controlType: 'Input',
					key: 'weightDifference',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重判定结果',
					controlType: 'Input',
					key: 'weightResult',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '自动线检测结果',
					controlType: 'Input',
					key: 'autoInspectResult',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '人工复检结果',
					controlType: 'Input',
					key: 'manualReinspectResult',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '复检人员',
					controlType: 'Input',
					key: 'reinspectBy',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '二维码字符提取',
					controlType: 'Input',
					key: 'barcode',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '二维码等级提取',
					controlType: 'Input',
					key: 'barcodeGrade',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重公差上限设定值',
					controlType: 'Input',
					key: 'maxWeightTolerance',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重公差下限设定值',
					controlType: 'Input',
					key: 'minWeightTolerance',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重压铸前',
					controlType: 'Input',
					key: 'weightBeforeDieCasting',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重压铸后',
					controlType: 'Input',
					key: 'weightAfterDieCasting',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '使用产品/穴数选择',
					controlType: 'Input',
					key: 'holeSelection',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '打标使用状态',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '数据源',
					controlType: 'Input',
					key: 'datasource',
					disabled: true,
					labelCol: {
						span: 9
					}
				}
			]
		},
		{
			name: '数据B',
			formRef: this.formB,
			formList: [
				{
					label: '流水号',
					controlType: 'Input',
					key: 'serialNumber',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: 'SR1000二维码编号',
					controlType: 'Input',
					key: 'barcodeData',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '读码判定结果',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重差值',
					controlType: 'Input',
					key: 'weightDifference',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重判定结果',
					controlType: 'Input',
					key: 'weightResult',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '自动线检测结果',
					controlType: 'Input',
					key: 'autoInspectResult',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '人工复检结果',
					controlType: 'Input',
					key: 'manualReinspectResult',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '复检人员',
					controlType: 'Input',
					key: 'reinspectBy',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '二维码字符提取',
					controlType: 'Input',
					key: 'barcode',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '二维码等级提取',
					controlType: 'Input',
					key: 'barcodeGrade',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重公差上限设定值',
					controlType: 'Input',
					key: 'maxWeightTolerance',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重公差下限设定值',
					controlType: 'Input',
					key: 'minWeightTolerance',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重压铸前',
					controlType: 'Input',
					key: 'weightBeforeDieCasting',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '铝重压铸后',
					controlType: 'Input',
					key: 'weightAfterDieCasting',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '使用产品/穴数选择',
					controlType: 'Input',
					key: 'holeSelection',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '打标使用状态',
					controlType: 'Input',
					key: 'codeReadingFunc',
					disabled: true,
					labelCol: {
						span: 9
					}
				},
				{
					label: '数据源',
					controlType: 'Input',
					key: 'datasource',
					disabled: true,
					labelCol: {
						span: 9
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
					moldNo: res.data.logTime,
					injectionNo: res.data.injectionNo
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