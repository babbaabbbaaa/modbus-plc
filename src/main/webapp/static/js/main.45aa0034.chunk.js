(this["webpackJsonpfrontend-ui"]=this["webpackJsonpfrontend-ui"]||[]).push([[0],{206:function(e,t,a){},310:function(e,t,a){"use strict";a.r(t);var n=a(0),c=a.n(n),i=a(30),l=a.n(i),o=(a(205),a(206),a(98)),r=a(24),s=a(42),d=a(43),u=a(46),h=a(45),b=a(193),p=a(318),g=a(315),f=a(311),m=a(312),j=a(40),y=a(66),x="".concat(window.location.protocol,"//").concat(window.location.host),O="".concat({PROD:{baseUrl:x},UAT:{baseUrl:x},QA:{baseUrl:x},DEV:{baseUrl:x},LOCAL:{baseUrl:"http://192.168.17.37:8080/"}}.DEV.baseUrl),k={search:"".concat(O,"/plc/search"),export:"".concat(O,"/plc/export"),confirm:"".concat(O,"/plc/confirm"),configs:"".concat(O,"/plc/configs"),secureClear:"".concat(O,"/secure/clear"),secureConfig:"".concat(O,"/secure/config"),secureConfigs:"".concat(O,"/secure/configs"),secureLogin:"".concat(O,"/secure/login"),secureSignal:"".concat(O,"/secure/signal"),test:"".concat(O,"/plc/test")},w=a(153),I=a.n(w),C=(a(224),a(317)),v=a(185),T=a(7),M=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(s.a)(this,a),(n=t.call(this,e)).state={loading:!1,percent:0,isProgress:!1},n}return Object(d.a)(a,[{key:"showProgress",value:function(e){this.setState({loading:!0,isProgress:!0,percent:e})}},{key:"show",value:function(){this.setState({loading:!0})}},{key:"hidden",value:function(){this.setState({loading:!1,isProgress:!1,percent:0})}},{key:"render",value:function(){var e=this.state,t=e.loading,a=e.isProgress,n=e.percent;return Object(T.jsx)("div",{style:{position:"fixed",zIndex:1001,left:0,top:0,width:"100%",height:"100%",display:"".concat(t?"flex":"none"),alignItems:"center",justifyContent:"center",background:"rgba(255,255,255, 0.5)"},children:a?Object(T.jsx)(C.a,{type:"circle",percent:n}):Object(T.jsx)(v.a,{spinning:t})})}}]),a}(n.PureComponent),S=document.createElement("div");document.body.appendChild(S);var R=l.a.render(c.a.createElement(M),S),F=a(190),N=a(155),D=a.n(N),P=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(s.a)(this,a),(n=t.call(this,e)).formRef=c.a.createRef(),n.handleCancel=function(){n.setState({visible:!1})},n.handleOk=function(){n.formRef.current.validateFields().then((function(e){console.log(e.password),console.log(D()(e.password)),J(D()(e.password)).then((function(e){0===e.code&&(n.setState({visible:!1}),window.location.reload())}))})).catch((function(e){console.log(e)}))},n.state={visible:!1},n}return Object(d.a)(a,[{key:"show",value:function(){this.setState({visible:!0})}},{key:"render",value:function(){var e=this.state.visible;return Object(T.jsx)(p.a,{title:"\u8bf7\u8f93\u5165\u5bc6\u7801",closable:!1,maskClosable:!1,visible:e,onOk:this.handleOk,onCancel:this.handleCancel,footer:[Object(T.jsx)(j.a,{onClick:this.handleCancel,children:"\u53d6\u6d88"},"back"),Object(T.jsx)(j.a,{type:"primary",onClick:this.handleOk,children:"\u786e\u5b9a"},"submit")],children:Object(T.jsx)(g.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(T.jsx)(f.a,{children:Object(T.jsx)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:Object(T.jsx)(g.a.Item,{name:"password",label:"\u5bc6\u7801",children:Object(T.jsx)(F.a,{type:"password"})})})})})})}}]),a}(n.PureComponent),z=document.createElement("div");document.body.appendChild(z);var H=l.a.render(c.a.createElement(P),z),L=0;var V=I.a.create({timeout:6e4,withCredentials:!0}),U=[],E=I.a.CancelToken;V.interceptors.request.use((function(e){e.withCredentials&&(R.show(),L+=1),e.uploadOrDownload&&(e.timeout=0);var t=JSON.stringify(e.url)+JSON.stringify(e.data)+"&"+e.method;if(e.cancelToken||!U.includes(t)?U.push(t):e.url.indexOf("vendor/all")<0?e.cancelToken=new E((function(e){return e()})):e.cancelToken="","get"===e.method){var a=(new Date).getTime();-1===e.url.indexOf("?")?e.url="".concat(e.url,"?t=").concat(a):e.url="".concat(e.url,"&t=").concat(a)}return e}),(function(e){return Promise.reject(e)})),V.interceptors.response.use((function(e){(L-=1)<=0&&R.hidden();var t=JSON.stringify(e.config.url)+JSON.stringify(e.config.data)+"&"+e.config.method;U.splice(U.findIndex((function(e){return e===t})),1);var a=e.config,n=e.data,c=n.code,i=n.msg;return"[object Blob]"===Object.prototype.toString.call(n)?e:c&&200!==c?(b.b.error(i),Promise.reject(e.data)):a.uploadOrDownload?e:e.data}),(function(e){L=0,R.hidden(),U=[];var t=e.response&&e.response.status||e.status,a=(e.response&&e.response.data||{}).debugMessage;return 403===t?H.show():e.response&&b.b.error(a||"error"),Promise.reject(e)}));var A=V,J=function(e){return A.get("".concat(k.secureLogin,"?x-api-access-key=").concat(e))},K=function(e){var t=Object(n.useRef)(),a=Object(n.useState)("config-tip config-tishi"),c=Object(y.a)(a,2),i=c[0],l=c[1];Object(n.useEffect)((function(){return t.current=setInterval((function(){r()}),500),o}));var o=function(){clearInterval(t.current)},r=function(){A.get("".concat(k.secureSignal),{withCredentials:!1}).then((function(e){if(0===e.code){var t=0===e.data?"config-tip config-tishi":"config-tip config-tishi-active";l(t)}}))};return Object(T.jsx)("div",{className:i})},B=a(138),Y=a(35),q=a(198),W=a(94),Q=a(313),G=a(189),X=a(187),Z=["onResize","width"],$={overflow:"hidden",whiteSpace:"nowrap",textOverflow:"ellipsis",cursor:"default"},_=function(e){var t=e.onResize,a=e.width,n=Object(q.a)(e,Z);return a?Object(T.jsx)(X.Resizable,{width:a,height:0,onResize:t,draggableOpts:{enableUserSelectHack:!1},children:Object(T.jsx)("th",Object(Y.a)({},n))}):Object(T.jsx)("th",Object(Y.a)({},n))},ee=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(s.a)(this,a),(n=t.call(this,e)).components={header:{cell:_}},n.handleResize=function(e){return function(t,a){var c=a.size;n.setState((function(t){var a=t.tableColumns,n=Object(B.a)(a);return n[e]=Object(Y.a)(Object(Y.a)({},n[e]),{},{width:c.width}),{tableColumns:n}}))}},n.state={expandedRows:[],tableColumns:e.columns},n}return Object(d.a)(a,[{key:"componentWillReceiveProps",value:function(){this.setState({expandedRows:[]})}},{key:"renderContent",value:function(e){var t=e.detail,a=this.props.detailColumns;return Object(T.jsx)("div",{className:"table-detail",children:Object(T.jsx)("ul",{className:"detail-item",children:a&&a.length>0&&a.map((function(e){var a=e.title,n=e.key,c=e.render;return Object(T.jsxs)("li",{children:[Object(T.jsx)("strong",{children:a}),c?c(t[n]):t[n]]},n)}))})})}},{key:"render",value:function(){var e=this,t=this.props,a=t.dataSource,n=t.rowSelection,c=t.total,i=t.pageIndex,l=t.pageSize,o=t.onPageChange,r=t.onSizeChange,s=t.detailColumns,d=t.rowClassName,u=t.showHeader,h=this.state,b=h.expandedRows,p=h.tableColumns,g=0,f=p.map((function(t,a){return g+=t.width||0,t.onCell=t.onCell?t.onCell:function(){return{style:Object(Y.a)({},$)}},t.render=t.render?t.render:function(e){return Object(T.jsx)(W.a,{title:e,children:Object(T.jsx)("div",{style:Object(Y.a)({},$),children:e})})},t.onHeaderCell=function(t){return{width:t.width,onResize:e.handleResize(a)}},t}));return Object(T.jsxs)("div",{className:"table-panel-wrapper",children:[Object(T.jsx)(Q.a,{rowKey:"key",size:"small",columns:f,bordered:!0,dataSource:a,showHeader:u,components:this.components,expandedRowRender:s&&function(t){return e.renderContent(t)},pagination:!1,rowClassName:d,scroll:{x:g,y:"calc(100% - 44px)"},rowSelection:n||void 0,expandedRowKeys:b,onChange:this.tableChange,onExpandedRowsChange:function(t){e.setState({expandedRows:t})}}),c>0&&Object(T.jsx)("div",{className:"pagination-wrapper",children:Object(T.jsx)(G.a,{current:i,pageSize:l,total:c,showTotal:function(e){return"Total ".concat(e," ")},showSizeChanger:!0,onChange:o,onShowSizeChange:r,pageSizeOptions:["10","20","30","50","100"]})})]})}}]),a}(n.Component),te=a(107),ae=a(314),ne=a(316),ce=F.a.TextArea,ie=te.a.Option,le=ae.a.RangePicker,oe=function(e){var t=e.controlType,a=e.options,n=e.type,c=e.disabled,i=e.mode,l=e.disabledDate,o=e.defaultValue,r=e.changeValue,s=e.placeholder,d=e.showSearch,u=e.filterOption,h=e.allowClear,b=e.format;switch(t){case"TextArea":return Object(T.jsx)(ce,{autoSize:{minRows:2,maxRows:6},allowClear:!0,placeholder:s,defaultValue:o,onChange:r,disabled:c});case"DatePicker":return Object(T.jsx)(ae.a,{disabled:c,format:b,disabledDate:l,placeholder:s,onChange:r});case"RangePicker":return Object(T.jsx)(le,{disabled:c,format:b,placeholder:s,disabledDate:l,onChange:r});case"Select":return Object(T.jsx)(te.a,{mode:i,allowClear:void 0===h||h,defaultValue:o,onChange:r,maxTagCount:2,showSearch:d,optionFilterProp:"children",getPopupContainer:function(e){return e.parentElement},disabled:c,placeholder:s,filterOption:u,children:a&&a.length>0&&a.map((function(e){var t=e.value,a=e.label,n=e.disabled;return Object(T.jsx)(ie,{value:t,disabled:n,children:a},"".concat(t).concat(a))}))});case"Input":return Object(T.jsx)(F.a,{allowClear:!0,type:n||"text",placeholder:s,defaultValue:o,onChange:r,disabled:c});case"InputNumber":return Object(T.jsx)(ne.a,{disabled:c});default:return}},re=function(e){var t=e.isModalVisible,a=e.addConfigItem,c=e.title,i=e.modalContent,l=e.data,o=g.a.useForm(),r=Object(y.a)(o,1)[0];Object(n.useEffect)((function(){l&&"\u65b0\u589e\u914d\u7f6e"!==c?r.setFieldsValue(Object(Y.a)({},l)):r.resetFields()}));var s=function(){r.validateFields().then((function(e){a(!0,e)})).catch((function(e){console.log(e)}))},d=function(){a(!1)};return Object(T.jsx)(p.a,{title:c,closable:!1,maskClosable:!1,visible:t,onOk:s,onCancel:d,footer:[Object(T.jsx)(j.a,{onClick:d,children:"\u53d6\u6d88"},"back"),Object(T.jsx)(j.a,{type:"primary",onClick:s,children:"\u786e\u5b9a"},"submit")],children:Object(T.jsx)(g.a,{className:"page-form",name:"search",form:r,children:Object(T.jsx)(f.a,{children:i.map((function(e){return Object(T.jsx)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:Object(T.jsx)(g.a.Item,{name:e.key,label:e.label,rules:e.rules,children:oe(e)})},e.key)}))})})})},se=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(s.a)(this,a),(n=t.call(this,e)).formRef=c.a.createRef(),n.modalItem=[{label:"\u4ea7\u54c1\u7c7b\u578b",controlType:"Input",placeholder:"\u8bf7\u8f93\u5165",key:"productType",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u4ea7\u54c1\u7c7b\u578b"}]},{label:"\u4ea7\u54c1\u7c7b\u578bID",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"productTypeId",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u4ea7\u54c1\u7c7b\u578bID"}]},{label:"\u5f00\u59cb\u4f4d\u7f6e",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"start"},{label:"\u7ed3\u675f\u4f4d\u7f6e",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"end"}],n.getConfigs=function(){var e;A.get("".concat(k.secureConfigs),{params:e}).then((function(e){0===e.code&&(e.data.length>0&&e.data.map((function(e,t){e.key=e.id})),n.setState({dataSource:e.data}))}))},n.addHandle=function(){n.setState({isModalVisible:!0,title:"\u65b0\u589e\u914d\u7f6e",editData:{}})},n.addConfigItem=function(e,t){var a;console.log(e,t),e?(a=t,A.post("".concat(k.secureConfig),a)).then((function(e){b.b.success("".concat(n.state.title,"\u6210\u529f\uff01")),n.getConfigs(),n.setState({editData:{},isModalVisible:!1})})):n.setState({isModalVisible:!1})},n.clearHandle=function(){p.a.confirm({title:"\u60a8\u786e\u8ba4\u8981\u6e05\u9664\u5417\uff1f",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){var e;(e={productTypeId:n.state.selectedRows[0].productTypeId},A.post("".concat(k.secureClear),e)).then((function(e){0===e.code?(b.b.success("\u6e05\u9664\u6210\u529f\uff01"),n.getConfigs()):b.b.error(e.message||"\u6e05\u9664\u5931\u8d25\uff01")}))}})},n.editHandle=function(){n.setState({title:"\u7f16\u8f91\u914d\u7f6e",isModalVisible:!0,editData:n.state.selectedRows[0]})},n.onSelectChange=function(e,t){n.setState({selectedRowKeys:e,selectedRows:t})},n.state={columns:[{title:"\u5e8f\u53f7",dataIndex:"id",key:"id",width:165},{title:"\u4ea7\u54c1\u7c7b\u578b",dataIndex:"productType",key:"productType",width:165},{title:"\u4ea7\u54c1\u7c7b\u578bID",dataIndex:"productTypeId",key:"productTypeId",width:165},{title:"\u5f00\u59cb\u4f4d\u7f6e",dataIndex:"start",key:"start",width:165},{title:"\u7ed3\u675f\u4f4d\u7f6e",dataIndex:"end",key:"end",width:165}],dataSource:[],selectedRowKeys:[],selectedRows:[],editData:{},isLight:0,isModalVisible:!1},n}return Object(d.a)(a,[{key:"componentDidMount",value:function(){this.getConfigs()}},{key:"render",value:function(){var e=this.state,t=e.columns,a=e.dataSource,n=e.isModalVisible,c=e.title,i=e.selectedRowKeys,l=e.editData,o=(e.isLight,{onChange:this.onSelectChange});return Object(T.jsxs)("div",{className:"page-container",children:[Object(T.jsx)(g.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(T.jsx)(f.a,{children:Object(T.jsxs)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:[Object(T.jsx)(j.a,{className:"btn submit-btn",disabled:1!==i.length,onClick:this.clearHandle,children:"\u6e05\u9664\u6570\u636e\u5e93\u914d\u7f6e"}),Object(T.jsx)(j.a,{className:"btn submit-btn",disabled:1!==i.length,onClick:this.editHandle,children:"\u7f16\u8f91\u914d\u7f6e"}),Object(T.jsx)(j.a,{className:"btn submit-btn",onClick:this.addHandle,children:"\u65b0\u589e"}),Object(T.jsx)(K,{})]})})}),Object(T.jsx)(ee,{columns:t,rowSelection:o,rowClassName:function(){return"center"},showHeader:!0,dataSource:a}),Object(T.jsx)(re,{isModalVisible:n,title:c,data:l,modalContent:this.modalItem,addConfigItem:this.addConfigItem})]})}}]),a}(c.a.Component),de=a(120),ue=[{title:"\u5e8f\u53f7",dataIndex:"index",key:"index",width:60},{title:"\u6570\u636e\u6e90",dataIndex:"dataSource",key:"dataSource",width:100},{title:"\u65f6\u95f4",dataIndex:"logTime",key:"logTime",width:170},{title:"SR1000\u4e8c\u7ef4\u7801\u7f16\u53f7",dataIndex:"barcodeData",key:"barcodeData",width:420},{title:"\u4e8c\u7ef4\u7801\u5b57\u7b26\u63d0\u53d6",dataIndex:"barcode",key:"barcode",width:135},{title:"\u901a\u89c4\u529f\u80fd",dataIndex:"generalFunc",key:"generalFunc",width:135},{title:"\u6d4b\u9ad8\u529f\u80fd",dataIndex:"heightFunc",key:"heightFunc",width:135},{title:"\u6253\u6807\u529f\u80fd",dataIndex:"flagFunc",key:"flagFunc",width:135},{title:"\u8bfb\u7801\u529f\u80fd",dataIndex:"scanFunc",key:"scanFunc",width:135},{title:"\u53f0\u98ce\u529f\u80fd",dataIndex:"typhoonFunc",key:"typhoonFunc",width:135},{title:"\u69fd\u6df1\u68c0\u6d4b\u529f\u80fd",dataIndex:"slotDepthFunc",key:"slotDepthFunc",width:165},{title:"\u65cb\u8f6c\u9519\u8bef\u68c0\u6d4b",dataIndex:"spinCheckFunc",key:"spinCheckFunc",width:165},{title:"\u710a\u7f1d\u529f\u80fd",dataIndex:"weldFunc",key:"weldFunc",width:135},{title:"\u6d4b\u9ad8\u6570\u503c1",dataIndex:"heightMeasure1",key:"heightMeasure1",width:100},{title:"\u6d4b\u9ad8\u6570\u503c2",dataIndex:"heightMeasure2",key:"heightMeasure2",width:100},{title:"\u6d4b\u9ad8\u6570\u503c3",dataIndex:"heightMeasure3",key:"heightMeasure3",width:100},{title:"\u6d4b\u9ad8\u6570\u503c4",dataIndex:"heightMeasure4",key:"heightMeasure4",width:100},{title:"\u6d4b\u9ad8\u6570\u503c5",dataIndex:"heightMeasure5",key:"heightMeasure5",width:100},{title:"\u6d4b\u9ad8\u6570\u503c6",dataIndex:"heightMeasure6",key:"heightMeasure6",width:100},{title:"\u6d4b\u9ad8\u6570\u503c7",dataIndex:"heightMeasure7",key:"heightMeasure7",width:100},{title:"\u6d4b\u9ad8\u6570\u503c8",dataIndex:"heightMeasure8",key:"heightMeasure8",width:100},{title:"\u6d4b\u9ad8\u6570\u503c9",dataIndex:"heightMeasure9",key:"heightMeasure9",width:100},{title:"\u6d4b\u9ad8\u6570\u503c10",dataIndex:"heightMeasure10",key:"heightMeasure10",width:100},{title:"\u6d4b\u9ad8\u6570\u503c11",dataIndex:"heightMeasure11",key:"heightMeasure11",width:100},{title:"\u6d4b\u9ad8\u6570\u503c12",dataIndex:"heightMeasure12",key:"heightMeasure12",width:100},{title:"\u6d4b\u9ad8\u6570\u503c13",dataIndex:"heightMeasure13",key:"heightMeasure13",width:100},{title:"\u6d4b\u9ad8\u6570\u503c14",dataIndex:"heightMeasure14",key:"heightMeasure14",width:100},{title:"\u6d4b\u9ad8\u6570\u503c15",dataIndex:"heightMeasure15",key:"heightMeasure15",width:100},{title:"\u6d4b\u9ad8\u6570\u503c16",dataIndex:"heightMeasure16",key:"heightMeasure16",width:100},{title:"\u6d4b\u9ad8\u6570\u503c17",dataIndex:"heightMeasure17",key:"heightMeasure17",width:100},{title:"\u6d4b\u9ad8\u6570\u503c18",dataIndex:"heightMeasure18",key:"heightMeasure18",width:100},{title:"\u6d4b\u9ad8\u6570\u503c19",dataIndex:"heightMeasure19",key:"heightMeasure19",width:100},{title:"\u6d4b\u9ad8\u6570\u503c20",dataIndex:"heightMeasure20",key:"heightMeasure20",width:100}];var he=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(s.a)(this,a),(n=t.call(this,e)).options={title:"\u64cd\u4f5c",dataIndex:"options",key:"options",width:85,render:function(e,t){if("DUP"===t.duplicated)return Object(T.jsx)(j.a,{className:"table-sure-btn",onClick:n.confirmHandle.bind(Object(de.a)(n),t),children:"\u786e\u8ba4"})}},n.formRef=c.a.createRef(),n.timer=null,n.getConfigOption=function(){A.post("".concat(k.configs)).then((function(e){if(0===e.code){var t=e.data.length>0&&e.data.map((function(e,t){return 0===t&&n.formRef.current.setFieldsValue({productTypeId:e.productTypeId}),{label:e.productType,value:e.productTypeId}}));n.setState({productOptions:t})}}))},n.getTableList=function(){var e=n.state,t=e.searchParam,a=e.page,c=e.size,i=n.formRef.current.getFieldsValue();delete i.date,n.setState({searchParam:Object(Y.a)(Object(Y.a)({page:1,size:10},i),{},{end:t.end,from:t.from})}),function(e){return A.post("".concat(k.search),e,{withCredentials:!1})}(Object(Y.a)(Object(Y.a)({page:a,size:c},i),{},{end:t.end,from:t.from})).then((function(e){var t=e.code,i=e.data;0===t&&(i.content.map((function(e,t){e.index=t+1+(a-1)*c})),n.setState({dataSource:i.content,totalCount:i.totalElements}))}))},n.searchHandle=function(){n.getTableList()},n.resetHandle=function(){n.formRef.current.resetFields(),n.getTableList()},n.exportHandle=function(){var e,t=n.state.searchParam;(e=t,A.post("".concat(k.export),e,{responseType:"blob"})).then((function(e){e&&function(e){var t;try{t=(t=e.headers["content-disposition"]&&e.headers["content-disposition"].split(";")[1].split("=")[1]).substr(7),t=decodeURIComponent(decodeURIComponent(t))}catch(l){console.log("file name is empty")}var a,n=t&&t.split(".");a="zip"===(n&&n[n.length-1])?new Blob([e.data]):new Blob([e.data],{type:"application/vnd.ms-excel;charset=utf-8"});var c=window.URL.createObjectURL(a),i=document.createElement("a");i.style.display="none",i.href=c,i.setAttribute("download",t),document.body.appendChild(i),i.click(),document.body.removeChild(i),window.URL.revokeObjectURL(c)}(e)}))},n.confirmHandle=function(e){p.a.confirm({title:"\u60a8\u786e\u8ba4\u8981\u63d0\u4ea4\u5417\uff1f",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){var t;(t={barcode:e.barcode},A.post("".concat(k.confirm),t)).then((function(e){0===e.code?(b.b.success("\u786e\u8ba4\u6210\u529f"),n.getTableList()):b.b.error(e.message||"\u786e\u8ba4\u5931\u8d25\uff01")}))}})},n.onPageChange=function(e){n.setState({page:e},n.getTableList)},n.onSizeChange=function(e,t){n.setState({page:1,size:t},n.getTableList)},n.dateChange=function(e,t){var a=Object(Y.a)(Object(Y.a)({},n.state.searchParam),{},{end:t[1],from:t[0]});n.setState({searchParam:a})},n.state={columns:[n.options].concat(Object(B.a)(ue)),dataSource:[],page:1,size:10,totalCount:0,searchParam:{end:null,from:null,productTypeId:"",barcode:"",barcodeData:"",productOptions:[]}},n}return Object(d.a)(a,[{key:"componentDidMount",value:function(){var e=this;this.getTableList(),this.getConfigOption(),this.timer=setInterval((function(){e.getTableList()}),1e3)}},{key:"componentWillUnmount",value:function(){clearInterval(this.timer),this.timer=null}},{key:"render",value:function(){var e=this.state,t=e.columns,a=e.dataSource,n=e.totalCount,c=e.page,i=e.size,l=[{label:"\u4ea7\u54c1\u7c7b\u578b",controlType:"Select",placeholder:"\u8bf7\u9009\u62e9",key:"productTypeId",col:{xs:24,sm:12,md:10,lg:10,xl:8},options:e.productOptions},{label:"\u65f6\u95f4",controlType:"RangePicker",placeholder:["\u5f00\u59cb\u65f6\u95f4","\u7ed3\u675f\u65f6\u95f4"],format:"YYYY-MM-DD",key:"date",col:{xs:24,sm:12,md:8,lg:8,xl:6},changeValue:this.dateChange},{label:"SR1000\u4e8c\u7ef4\u7801\u7f16\u53f7",controlType:"Input",placeholder:"\u8bf7\u8f93\u5165",key:"barcodeData",col:{xs:24,sm:12,md:8,lg:8,xl:6}},{label:"\u4e8c\u7ef4\u7801\u5b57\u7b26\u63d0\u53d6",controlType:"Input",placeholder:"\u8bf7\u8f93\u5165",key:"barcode",col:{xs:24,sm:12,md:8,lg:8,xl:6}}];return Object(T.jsxs)("div",{className:"page-container",children:[Object(T.jsx)(g.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(T.jsxs)(f.a,{children:[l.map((function(e){return Object(T.jsx)(m.a,{xs:24,sm:12,md:8,lg:8,xl:6,children:Object(T.jsx)(g.a.Item,{name:e.key,label:e.label,children:oe(e)})},e.key)})),Object(T.jsxs)(m.a,{xs:24,sm:12,md:8,lg:6,xl:6,children:[Object(T.jsx)(j.a,{className:"btn submit-btn",onClick:this.searchHandle,children:"\u641c\u7d22"}),Object(T.jsx)(j.a,{className:"btn reset-btn",onClick:this.resetHandle,children:"\u91cd\u7f6e"})]}),Object(T.jsx)(m.a,{xs:24,sm:12,md:8,lg:6,xl:6,children:Object(T.jsx)(j.a,{className:"btn submit-btn",onClick:this.exportHandle,children:"\u5bfc\u51fa"})})]})}),Object(T.jsx)(ee,{columns:t,showHeader:!0,rowClassName:function(e){var t="";switch(e.duplicated){case"DUP":t="bg-red";break;case"CONFIRMED":t="bg-yellow"}return t},dataSource:a,total:n,pageIndex:c,pageSize:i,onPageChange:this.onPageChange,onSizeChange:this.onSizeChange})]})}}]),a}(c.a.Component),be=function(){return A.get("".concat(k.test))},pe=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(){var e;Object(s.a)(this,a);for(var n=arguments.length,i=new Array(n),l=0;l<n;l++)i[l]=arguments[l];return(e=t.call.apply(t,[this].concat(i))).formRef=c.a.createRef(),e.testFormList=[{label:"SR1000\u4e8c\u7ef4\u7801\u7f16\u53f7",controlType:"Input",key:"barcodeData",disabled:!0},{label:"\u4e8c\u7ef4\u7801\u5b57\u7b26\u63d0\u53d6",controlType:"Input",key:"barcode",disabled:!0},{label:"\u901a\u89c4\u529f\u80fd",controlType:"Input",key:"generalFunc",disabled:!0},{label:"\u6d4b\u9ad8\u529f\u80fd",controlType:"Input",key:"heightFunc",disabled:!0},{label:"\u65cb\u8f6c\u9519\u8bef\u68c0\u6d4b",controlType:"Input",key:"spinCheckFunc",disabled:!0},{label:"\u8bfb\u7801\u529f\u80fd",controlType:"Input",key:"scanFunc",disabled:!0},{label:"\u53f0\u98ce\u529f\u80fd",controlType:"Input",key:"typhoonFunc",disabled:!0},{label:"\u69fd\u6df1\u68c0\u6d4b\u529f\u80fd",controlType:"Input",key:"slotDepthFunc",disabled:!0},{label:"\u6253\u6807\u529f\u80fd",controlType:"Input",key:"flagFunc",disabled:!0},{label:"\u710a\u7f1d\u529f\u80fd",controlType:"Input",key:"weldFunc",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c1",controlType:"Input",key:"heightMeasure1",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c2",controlType:"Input",key:"heightMeasure2",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c3",controlType:"Input",key:"heightMeasure3",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c4",controlType:"Input",key:"heightMeasure4",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c5",controlType:"Input",key:"heightMeasure5",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c6",controlType:"Input",key:"heightMeasure6",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c7",controlType:"Input",key:"heightMeasure7",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c8",controlType:"Input",key:"heightMeasure8",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c9",controlType:"Input",key:"heightMeasure9",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c10",controlType:"Input",key:"heightMeasure10",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c11",controlType:"Input",key:"heightMeasure11",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c12",controlType:"Input",key:"heightMeasure12",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c13",controlType:"Input",key:"heightMeasure13",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c14",controlType:"Input",key:"heightMeasure14",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c15",controlType:"Input",key:"heightMeasure15",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c16",controlType:"Input",key:"heightMeasure16",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c17",controlType:"Input",key:"heightMeasure17",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c18",controlType:"Input",key:"heightMeasure18",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c19",controlType:"Input",key:"heightMeasure19",disabled:!0},{label:"\u6d4b\u9ad8\u6570\u503c20",controlType:"Input",key:"heightMeasure20",disabled:!0},{label:"\u6d4b\u9ad8\u7cfb\u6570(\u914d\u7f6e)",controlType:"Input",key:"ratio",disabled:!0},{label:"\u91cd\u7801\u6807\u8bb0(7002)",controlType:"Input",key:"duplicate",disabled:!0},{label:"\u4ea7\u54c1\u7c7b\u578b(7003)",controlType:"Input",key:"productTypeId",disabled:!0}],e.testHandle=function(){be().then((function(t){0===t.code&&e.formRef.current.setFieldsValue(Object(Y.a)({},t.data))}))},e}return Object(d.a)(a,[{key:"render",value:function(){return Object(T.jsx)("div",{className:"page-container",children:Object(T.jsx)(g.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(T.jsxs)(f.a,{children:[Object(T.jsx)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:Object(T.jsx)(j.a,{className:"btn submit-btn",onClick:this.testHandle,children:"\u6d4b\u8bd5"})}),this.testFormList.map((function(e){return Object(T.jsx)(m.a,{xs:24,sm:24,md:12,lg:8,xl:8,children:Object(T.jsx)(g.a.Item,{name:e.key,label:e.label,rules:e.rules,children:oe(e)})},e.key)}))]})})})}}]),a}(c.a.Component),ge=[{path:"/",component:he},{path:"/config",component:se},{path:"/test",component:pe}],fe=a(82),me=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(){var e;Object(s.a)(this,a);for(var n=arguments.length,c=new Array(n),i=0;i<n;i++)c[i]=arguments[i];return(e=t.call.apply(t,[this].concat(c))).pathname=window.location.hash.substr(2)||"filter",e.state={current:e.pathname},e.handleClick=function(t){console.log("click ",t),e.setState({current:t.key})},e}return Object(d.a)(a,[{key:"render",value:function(){var e=this.state.current;return Object(T.jsxs)(fe.a,{className:"page-header",onClick:this.handleClick,selectedKeys:[e],mode:"horizontal",children:[Object(T.jsx)(fe.a.Item,{children:Object(T.jsx)(o.b,{to:"/",children:"PLC \u63a7\u5236\u5668"})},"filter"),Object(T.jsx)(fe.a.Item,{children:Object(T.jsx)(o.b,{to:"/test",children:"PLC \u6d4b\u8bd5"})},"test"),Object(T.jsx)(fe.a.Item,{children:Object(T.jsx)(o.b,{to:"/config",children:"\u5b89\u5168\u63a7\u5236\u5668"})},"config")]})}}]),a}(c.a.Component),je=me;var ye=function(){return Object(T.jsxs)(o.a,{children:[Object(T.jsx)(je,{}),Object(T.jsx)(r.c,{children:ge.map((function(e,t){return Object(T.jsx)(r.a,{path:e.path,element:Object(T.jsx)(e.component,{})},t)}))})]})};l.a.render(Object(T.jsx)(c.a.StrictMode,{children:Object(T.jsx)(ye,{})}),document.getElementById("root"))}},[[310,1,2]]]);
//# sourceMappingURL=main.45aa0034.chunk.js.map