import React, { PureComponent } from 'react';
import { Spin, Progress } from 'antd';
import ReactDOM from 'react-dom';

class Loading extends PureComponent {
	constructor(props) {
		super(props);
		this.state = {
			loading: false,
			percent: 0,
			isProgress: false,
		};
	}

	showProgress(percent) {
		this.setState({ loading: true, isProgress: true, percent });
	}

	show() {
		this.setState({ loading: true });
	}

	hidden() {
		this.setState({ loading: false, isProgress: false, percent: 0 });
	}

	render() {
		const { loading, isProgress, percent } = this.state;
		return (
			<div style={{ position: 'fixed', zIndex: 1001, left: 0, top: 0, width: '100%', height: '100%', display: `${loading ? 'flex' : 'none'}`, alignItems: 'center', justifyContent: 'center', background: 'rgba(255,255,255, 0.5)' }}>
				{
					isProgress ? <Progress type="circle" percent={percent} /> : <Spin spinning={loading} />
				}
			</div>
		);
	}
}

const div = document.createElement('div');
document.body.appendChild(div);
 
const Box = ReactDOM.render(React.createElement(Loading), div);
export default Box;