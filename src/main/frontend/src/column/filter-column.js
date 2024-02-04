import {Button} from 'antd'
const data = [ 
  {
    title: '序号',
    dataIndex: 'index',
    key: 'index',
    width: 60
  },
  {
    title: '自动线检测结果',
    dataIndex: 'autoInspectResult',
    key: 'autoInspectResult',
    width: 135,
    render: (value) => value?.includes('NG') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '人工复检结果',
    dataIndex: 'manualReinspectResult',
    key: 'manualReinspectResult',
    width: 135
  },
  {
    title: '复检人员',
    dataIndex: 'reinspectBy',
    key: 'reinspectBy',
    width: 135
  },
  {
    title: '数据源',
    dataIndex: 'dataSource',
    key: 'dataSource',
    width: 100
  },
  {
    title: '时间',
    dataIndex: 'logTime',
    key: 'logTime',
    width: 170
  },
  {
    title: 'SR1000二维码编号',
    dataIndex: 'barcodeData',
    key: 'barcodeData',
    width: 420
  },
  {
    title: 'SR1000二维码编号位数',
    dataIndex: 'barcodeDataLength',
    key: 'barcodeDataLength',
    width: 420
  },
  {
    title: '二维码字符提取',
    dataIndex: 'barcode',
    key: 'barcode',
    width: 135
  },
  {
    title: '托盘识别号',
    dataIndex: 'trayIdentityCode',
    key: 'trayIdentityCode',
    width: 135
  },
  {
    title: '通规功能',
    dataIndex: 'generalFunc',
    key: 'generalFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '测高功能',
    dataIndex: 'heightFunc',
    key: 'heightFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '打标功能',
    dataIndex: 'flagFunc',
    key: 'flagFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '读码功能',
    dataIndex: 'scanFunc',
    key: 'scanFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '台风功能',
    dataIndex: 'typhoonFunc',
    key: 'typhoonFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '槽深检测功能',
    dataIndex: 'slotDepthFunc',
    key: 'slotDepthFunc',
    width: 165,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '旋转错误检测',
    dataIndex: 'spinCheckFunc',
    key: 'spinCheckFunc',
    width: 165,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '焊缝功能',
    dataIndex: 'weldFunc',
    key: 'weldFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '测高数值1',
    dataIndex: 'heightMeasure1',
    key: 'heightMeasure1',
    width: 100
  },
  {
    title: '测高数值2',
    dataIndex: 'heightMeasure2',
    key: 'heightMeasure2',
    width: 100
  },
  {
    title: '测高数值3',
    dataIndex: 'heightMeasure3',
    key: 'heightMeasure3',
    width: 100
  },
  {
    title: '测高数值4',
    dataIndex: 'heightMeasure4',
    key: 'heightMeasure4',
    width: 100
  },
  {
    title: '测高数值5',
    dataIndex: 'heightMeasure5',
    key: 'heightMeasure5',
    width: 100
  },
  {
    title: '测高数值6',
    dataIndex: 'heightMeasure6',
    key: 'heightMeasure6',
    width: 100
  },
  {
    title: '测高数值7',
    dataIndex: 'heightMeasure7',
    key: 'heightMeasure7',
    width: 100
  },
  {
    title: '测高数值8',
    dataIndex: 'heightMeasure8',
    key: 'heightMeasure8',
    width: 100
  },
  {
    title: '测高数值9',
    dataIndex: 'heightMeasure9',
    key: 'heightMeasure9',
    width: 100
  },
  {
    title: '测高数值10',
    dataIndex: 'heightMeasure10',
    key: 'heightMeasure10',
    width: 100
  },
  {
    title: '测高数值11',
    dataIndex: 'heightMeasure11',
    key: 'heightMeasure11',
    width: 100
  },
  {
    title: '测高数值12',
    dataIndex: 'heightMeasure12',
    key: 'heightMeasure12',
    width: 100
  },
  {
    title: '测高数值13',
    dataIndex: 'heightMeasure13',
    key: 'heightMeasure13',
    width: 100
  },
  {
    title: '测高数值14',
    dataIndex: 'heightMeasure14',
    key: 'heightMeasure14',
    width: 100
  },
  {
    title: '测高数值15',
    dataIndex: 'heightMeasure15',
    key: 'heightMeasure15',
    width: 100
  },
  {
    title: '测高数值16',
    dataIndex: 'heightMeasure16',
    key: 'heightMeasure16',
    width: 100
  },
  {
    title: '测高数值17',
    dataIndex: 'heightMeasure17',
    key: 'heightMeasure17',
    width: 100
  },
  {
    title: '测高数值18',
    dataIndex: 'heightMeasure18',
    key: 'heightMeasure18',
    width: 100
  },
  {
    title: '测高数值19',
    dataIndex: 'heightMeasure19',
    key: 'heightMeasure19',
    width: 100
  },
  {
    title: '测高数值20',
    dataIndex: 'heightMeasure20',
    key: 'heightMeasure20',
    width: 100
  },
]

export default data;