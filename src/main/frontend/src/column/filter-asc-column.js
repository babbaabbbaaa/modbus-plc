import React from "react";


function columnRender (value, row, index) {
  const obj = {
    children: value,
    props: {},
  };
  
  obj.props.rowSpan = row.rowSpan;
  return obj;
}

const data = [ 
  {
    title: '序号',
    dataIndex: 'index',
    key: 'index',
    render: (value, row, index)=>columnRender (value, row, index),
    fixed: 'left',
    width: 43
  },
  {
    title: '时间',
    dataIndex: 'logTime',
    key: 'logTime',
    render: (value, row, index)=>columnRender (value, row, index),
    fixed: 'left',
    width: 89,
    onCell: () => {}
  },
  {
    title: '模具号',
    dataIndex: 'moldNo',
    key: 'moldNo',
    render: (value, row, index)=>columnRender (value, row, index),
    fixed: 'left',
    width: 50
  },
  {
    title: '压射号',
    dataIndex: 'injectionNo',
    key: 'injectionNo',
    render: (value, row, index)=>columnRender (value, row, index),
    fixed: 'left',
    width: 50
  },
  {
    title: '流水号',
    dataIndex: 'serialNumber',
    key: 'serialNumber',
    width: 50
  },
  {
    title: 'SR1000二维码编号',
    dataIndex: 'barcodeData',
    key: 'barcodeData',
    width: 386
  },
  {
    title: 'SR1000二维码位数',
    dataIndex: 'barcodeDataLength',
    key: 'barcodeDataLength',
    width: 386
  },
  {
    title: '读码判定结果',
    dataIndex: 'codeReadingFunc',
    key: 'codeReadingFunc',
    width: 90,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '铝重差值',
    dataIndex: 'weightDifference',
    key: 'weightDifference',
    width: 69
  },
  {
    title: '铝重判定结果',
    dataIndex: 'weightResult',
    key: 'weightResult',
    width: 97,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '自动检测结果',
    dataIndex: 'autoInspectResult',
    key: 'autoInspectResult',
    width: 110,
    render: (value) => value?.includes('NG')? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '人工复检结果',
    dataIndex: 'manualReinspectResult',
    key: 'manualReinspectResult',
    width: 110
  },
  {
    title: '复检人员',
    dataIndex: 'reinspectBy',
    key: 'reinspectBy',
    width: 67
  },
  {
    title: '二维码字符提取',
    dataIndex: 'barcode',
    key: 'barcode',
    width: 135
  },
  {
    title: '二维码等级提取',
    dataIndex: 'barcodeGrade',
    key: 'barcodeGrade',
    width: 135
  },
  {
    title: '铝重公差上限设定值',
    dataIndex: 'maxWeightTolerance',
    key: 'maxWeightTolerance',
    width: 135
  },
  {
    title: '铝重公差下限设定值',
    dataIndex: 'minWeightTolerance',
    key: 'minWeightTolerance',
    width: 135
  },
  {
    title: '铝重压铸前',
    dataIndex: 'weightBeforeDieCasting',
    key: 'weightBeforeDieCasting',
    width: 135
  },
  {
    title: '铝重压铸后',
    dataIndex: 'weightAfterDieCasting',
    key: 'weightAfterDieCasting',
    width: 135
  },
  {
    title: '使用产品/穴数选择',
    dataIndex: 'holeSelection',
    key: 'holeSelection',
    width: 135
  },
  {
    title: '打标使用状态',
    dataIndex: 'codeReadingFunc',
    key: 'codeReadingFunc',
    width: 135,
    render: (value) => value?.includes('不合格') ? <div className='bg-red color-white'>{value}</div> : <div>{value}</div>
  },
  {
    title: '数据源',
    dataIndex: 'datasource',
    key: 'datasource',
    width: 135
  },
]

export default data;