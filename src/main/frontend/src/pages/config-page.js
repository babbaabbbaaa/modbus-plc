import React from 'react';
import {Form, Row, Col, Button, Modal, message} from 'antd';
import TablePanel from '@/components/table';
import OperateModal from '../components/operate-modal';
import {secureConfig, secureConfigs, secureClear, secureLogin, secureSignal} from '@/service/config-service';


class ConfigPage extends React.Component {

    formRef = React.createRef();
    modalItem = [{
        label: '产品类型',
        controlType: 'Input',
        placeholder: '请输入',
        key: 'productType',
        rules: [{
            required: true,
            message: '请输入产品类型'
        }]
    }, {
        label: '产品类型ID',
        controlType: 'InputNumber',
        placeholder: '请输入',
        key: 'productTypeId',
        rules: [{
            required: true,
            message: '请输入产品类型ID'
        }]
    }, {
        label: '二维码截取开始位置',
        controlType: 'InputNumber',
        placeholder: '请输入',
        key: 'start'
    }, {
        label: '二维码截取结束位置',
        controlType: 'InputNumber',
        placeholder: '请输入',
        key: 'end'
    }, {
        label: '测高系数',
        controlType: 'InputNumber',
        placeholder: '请输入',
        key: 'ratio'
    }, {
        label: '合格位起始位置',
        controlType: 'InputNumber',
        placeholder: '请输入',
        key: 'qualifiedStart'
    }, {
        label: '合格位结束位置',
        controlType: 'InputNumber',
        placeholder: '请输入',
        key: 'qualifiedEnd'
    }
    ]

    constructor(props) {
        super(props);
        this.state = {
            columns: [{
                title: '序号',
                dataIndex: 'id',
                key: 'id',
                width: 165
            }, {
                title: '产品类型',
                dataIndex: 'productType',
                key: 'productType',
                width: 165
            }, {
                title: '产品类型ID',
                dataIndex: 'productTypeId',
                key: 'productTypeId',
                width: 165
            }, {
                title: '二维码截取开始位置',
                dataIndex: 'start',
                key: 'start',
                width: 165
            }, {
                title: '二维码截取结束位置',
                dataIndex: 'end',
                key: 'end',
                width: 165
            }, {
                title: '测高系数',
                dataIndex: 'ratio',
                key: 'ratio',
                width: 165
            }, {
                title: '合格位起始位置',
                dataIndex: 'qualifiedStart',
                key: 'qualifiedStart',
                width: 165
            }, {
                title: '合格位结束位置',
                dataIndex: 'qualifiedEnd',
                key: 'qualifiedEnd',
                width: 165
            }
            ],
            dataSource: [],
            selectedRowKeys: [],
            selectedRows: [],
            editData: {},
            isLight: 0,
            isModalVisible: false
        }
    }

    componentDidMount() {
        this.getConfigs();
    }

    getConfigs = () => {
        secureConfigs().then(res => {
            if (res.code === 0) {
                res.data.length > 0 && res.data.map((item, index) => {
                    item.key = item.id;
                })
                this.setState({
                    dataSource: res.data
                })
            }
        })
    }

    addHandle = () => {
        this.setState({
            isModalVisible: true,
            title: '新增配置',
            editData: {}
        })
    }

    addConfigItem = (type, data) => {
        console.log(type, data)
        if (type) {
            secureConfig(data).then(res => {
                message.success(`${this.state.title}成功！`);
                this.getConfigs();
                this.setState({
                    editData: {},
                    isModalVisible: false
                })
            })
        } else {
            this.setState({
                isModalVisible: false
            })
        }

    }

    clearHandle = () => {
        Modal.confirm({
            title: `您确认要清除吗？`,
            okText: '确认',
            cancelText: '取消',
            onOk: () => {
                secureClear({productTypeId: this.state.selectedRows[0].productTypeId}).then(res => {
                    if (res.code === 0) {
                        message.success('清除成功！');
                        this.getConfigs();
                    } else {
                        message.error(res.message || '清除失败！');
                    }
                })
            }
        });
    }

    editHandle = () => {
        this.setState({
            title: '编辑配置',
            isModalVisible: true,
            editData: this.state.selectedRows[0]
        })
    }

    onSelectChange = (selectedRowKeys, selectedRows) => {
        this.setState({
            selectedRowKeys,
            selectedRows
        })
    }

    render() {
        const {columns, dataSource, isModalVisible, title, selectedRowKeys, editData} = this.state;
        const rowSelection = {
            onChange: this.onSelectChange,
        };

        const rowClassName = () => {
            return 'center'
        }

        return <div className='page-container'>
            <Form className='page-form'
                  name='search'
                  ref={this.formRef}>
                <Row>
                    <Col xs={24}
                         sm={24}
                         md={24}
                         lg={24}
                         xl={24}>
                        <Button className="btn submit-btn"
                                disabled={selectedRowKeys.length !== 1}
                                onClick={this.clearHandle}> 清除数据库配置 </Button>
                        <Button className="btn submit-btn"
                                disabled={selectedRowKeys.length !== 1}
                                onClick={this.editHandle}> 编辑配置 </Button>
                        <Button className="btn submit-btn" onClick={this.addHandle}> 新增 </Button>
                    </Col>
                </Row>
            </Form>
            <TablePanel
                columns={columns}
                rowSelection={rowSelection}
                rowClassName={rowClassName}
                showHeader={true}
                dataSource={dataSource}
            />
            <OperateModal
                isModalVisible={isModalVisible}
                title={title}
                data={editData}
                modalContent={this.modalItem}
                addConfigItem={this.addConfigItem}
            />
        </div>
    }
}

export default ConfigPage;