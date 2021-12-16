/* eslint-disable array-callback-return */
import React, { Component } from 'react';
import { Table, Pagination,Tooltip } from 'antd';
import { Resizable } from 'react-resizable';
// import 'react-resizable/css/styles.css';


const maxColWith = 160;
const overHidden = {
	overflow: 'hidden',
	whiteSpace: 'nowrap',
	textOverflow: 'ellipsis',
	cursor: 'default',
};

const ResizeableTitle = (props) => {
	const { onResize, width, ...restProps } = props;
	if ( !width ) {
		return <th {...restProps} />
	}
	return (
		<Resizable width={width} height={0} onResize={onResize} draggableOpts={{ enableUserSelectHack: false }}>
			<th {...restProps} />
		</Resizable>		
	)
}

class TablePanel extends Component {
	constructor(props) {
		super(props);
		this.state = {
			expandedRows: [],
			tableColumns: props.columns
		};
	}
	
	components = {
		header: {
			cell: ResizeableTitle,
	 	},
	}

	handleResize = index => (e, { size }) => {
		this.setState(({ tableColumns }) => {
			const nextColumns = [...tableColumns];
			nextColumns[index] = {
			...nextColumns[index],
			width: size.width,
		};
			return { tableColumns: nextColumns };
		});
	};
	componentWillReceiveProps() {
		this.setState({ expandedRows: [] });
	}

	renderContent(record) {
		const dailyTrackingDetail = record.detail;
		const { detailColumns } = this.props;
		return (
			<div className="table-detail">
				<ul className="detail-item">
					{
						detailColumns && detailColumns.length > 0
						&& detailColumns.map(({ title, key, render }) => (
							<li key={key}>
								<strong>{title}</strong>
								{ render ? render(dailyTrackingDetail[key]) : dailyTrackingDetail[key]}
							</li>
						))
					}
				</ul>
			</div>
		);
	}

	render() {
		let {
			dataSource,
			rowSelection,
			total,
			pageIndex,
			pageSize,
			onPageChange,
			onSizeChange,
			detailColumns,
			rowClassName,
			showHeader
		} = this.props;
		const { expandedRows,tableColumns } = this.state;
		let tableWidth = 0;
		const columns = tableColumns.map((item, index) =>{
			tableWidth += item.width || 0;
			item.onCell = item.onCell ? item.onCell : () => {
				return {
					style: {
						...overHidden
					}
				}
			}
			item.render = item.render?item.render:(text) => {
				
				return <Tooltip title={text}>
					<div style={{...overHidden}}>{text}</div>
				</Tooltip>
			}
			item.onHeaderCell = column => ({
				width: column.width,
				onResize: this.handleResize(index),
			})
			return item;
		});
		return (
			<div className="table-panel-wrapper">
				<Table
					rowKey="key"
					size="small"
					columns={columns}
					bordered={true}
					dataSource={dataSource}
					showHeader={showHeader}
					components={this.components}
					expandedRowRender={detailColumns && ((record) => this.renderContent(record))}
					pagination={false}
					rowClassName={rowClassName}
					scroll={{ x: tableWidth, y: 'calc(100% - 44px)' }}
					rowSelection={rowSelection || undefined}
					expandedRowKeys={expandedRows}
					onChange={this.tableChange}
					onExpandedRowsChange={(rows) => { this.setState({ expandedRows: rows }); }}
				/>
				{
					total > 0 && (
						<div className="pagination-wrapper">
							<Pagination
								current={pageIndex}
								pageSize={pageSize}
								total={total}
								showTotal={(totalCount) => `Total ${totalCount} `}
								showSizeChanger
								onChange={onPageChange}
								onShowSizeChange={onSizeChange}
								pageSizeOptions={['10', '20', '30', '50','100']}
							/>
						</div>
					)
				}
			</div>
		);
	}
};
export default TablePanel;
