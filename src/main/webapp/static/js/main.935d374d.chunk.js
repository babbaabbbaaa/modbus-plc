(this["webpackJsonpfrontend-ui"]=this["webpackJsonpfrontend-ui"]||[]).push([[0],{206:function(e,t,a){},310:function(e,t,a){"use strict";a.r(t);var n=a(0),l=a.n(n),i=a(30),c=a.n(i),o=(a(205),a(206),a(98)),s=a(24),r=a(42),d=a(43),u=a(46),h=a(45),b=a(193),p=a(318),f=a(315),g=a(311),m=a(312),j=a(40),y=a(138),x=a(35),O=a(198),k=a(94),C=a(313),w=a(189),I=a(184),v=a(7),T=["onResize","width"],M={overflow:"hidden",whiteSpace:"nowrap",textOverflow:"ellipsis",cursor:"default"},S=function(e){var t=e.onResize,a=e.width,n=Object(O.a)(e,T);return a?Object(v.jsx)(I.Resizable,{width:a,height:0,onResize:t,draggableOpts:{enableUserSelectHack:!1},children:Object(v.jsx)("th",Object(x.a)({},n))}):Object(v.jsx)("th",Object(x.a)({},n))},R=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(r.a)(this,a),(n=t.call(this,e)).components={header:{cell:S}},n.handleResize=function(e){return function(t,a){var l=a.size;n.setState((function(t){var a=t.tableColumns,n=Object(y.a)(a);return n[e]=Object(x.a)(Object(x.a)({},n[e]),{},{width:l.width}),{tableColumns:n}}))}},n.state={expandedRows:[],tableColumns:e.columns},n}return Object(d.a)(a,[{key:"componentWillReceiveProps",value:function(){this.setState({expandedRows:[]})}},{key:"renderContent",value:function(e){var t=e.detail,a=this.props.detailColumns;return Object(v.jsx)("div",{className:"table-detail",children:Object(v.jsx)("ul",{className:"detail-item",children:a&&a.length>0&&a.map((function(e){var a=e.title,n=e.key,l=e.render;return Object(v.jsxs)("li",{children:[Object(v.jsx)("strong",{children:a}),l?l(t[n]):t[n]]},n)}))})})}},{key:"render",value:function(){var e=this,t=this.props,a=t.dataSource,n=t.rowSelection,l=t.total,i=t.pageIndex,c=t.pageSize,o=t.onPageChange,s=t.onSizeChange,r=t.detailColumns,d=t.rowClassName,u=t.showHeader,h=this.state,b=h.expandedRows,p=h.tableColumns,f=0,g=p.map((function(t,a){return f+=t.width||0,t.onCell=t.onCell?t.onCell:function(){return{style:Object(x.a)({},M)}},t.render=t.render?t.render:function(e){return Object(v.jsx)(k.a,{title:e,children:Object(v.jsx)("div",{style:Object(x.a)({},M),children:e})})},t.onHeaderCell=function(t){return{width:t.width,onResize:e.handleResize(a)}},t}));return Object(v.jsxs)("div",{className:"table-panel-wrapper",children:[Object(v.jsx)(C.a,{rowKey:"key",size:"small",columns:g,bordered:!0,dataSource:a,showHeader:u,components:this.components,expandedRowRender:r&&function(t){return e.renderContent(t)},pagination:!1,rowClassName:d,scroll:{x:f,y:"calc(100% - 44px)"},rowSelection:n||void 0,expandedRowKeys:b,onChange:this.tableChange,onExpandedRowsChange:function(t){e.setState({expandedRows:t})}}),l>0&&Object(v.jsx)("div",{className:"pagination-wrapper",children:Object(v.jsx)(w.a,{current:i,pageSize:c,total:l,showTotal:function(e){return"Total ".concat(e," ")},showSizeChanger:!0,onChange:o,onShowSizeChange:s,pageSizeOptions:["10","20","30","50","100"]})})]})}}]),a}(n.Component),F=a(66),N=a(190),D=a(107),P=a(314),z=a(316),H=N.a.TextArea,L=D.a.Option,V=P.a.RangePicker,E=function(e){var t=e.controlType,a=e.options,n=e.type,l=e.disabled,i=e.mode,c=e.disabledDate,o=e.defaultValue,s=e.changeValue,r=e.placeholder,d=e.showSearch,u=e.filterOption,h=e.allowClear,b=e.format;switch(t){case"TextArea":return Object(v.jsx)(H,{autoSize:{minRows:2,maxRows:6},allowClear:!0,placeholder:r,defaultValue:o,onChange:s,disabled:l});case"DatePicker":return Object(v.jsx)(P.a,{disabled:l,format:b,disabledDate:c,placeholder:r,onChange:s});case"RangePicker":return Object(v.jsx)(V,{disabled:l,format:b,placeholder:r,disabledDate:c,onChange:s});case"Select":return Object(v.jsx)(D.a,{mode:i,allowClear:void 0===h||h,defaultValue:o,onChange:s,maxTagCount:2,showSearch:d,optionFilterProp:"children",getPopupContainer:function(e){return e.parentElement},disabled:l,placeholder:r,filterOption:u,children:a&&a.length>0&&a.map((function(e){var t=e.value,a=e.label,n=e.disabled;return Object(v.jsx)(L,{value:t,disabled:n,children:a},"".concat(t).concat(a))}))});case"Input":return Object(v.jsx)(N.a,{allowClear:!0,type:n||"text",placeholder:r,defaultValue:o,onChange:s,disabled:l});case"InputNumber":return Object(v.jsx)(z.a,{disabled:l});default:return}},U=function(e){var t=e.isModalVisible,a=e.addConfigItem,l=e.title,i=e.modalContent,c=e.data,o=f.a.useForm(),s=Object(F.a)(o,1)[0];Object(n.useEffect)((function(){c&&"\u65b0\u589e\u914d\u7f6e"!==l?s.setFieldsValue(Object(x.a)({},c)):s.resetFields()}));var r=function(){s.validateFields().then((function(e){a(!0,e)})).catch((function(e){console.log(e)}))},d=function(){a(!1)};return Object(v.jsx)(p.a,{title:l,closable:!1,maskClosable:!1,visible:t,onOk:r,onCancel:d,footer:[Object(v.jsx)(j.a,{onClick:d,children:"\u53d6\u6d88"},"back"),Object(v.jsx)(j.a,{type:"primary",onClick:r,children:"\u786e\u5b9a"},"submit")],children:Object(v.jsx)(f.a,{className:"page-form",name:"search",form:s,children:Object(v.jsx)(g.a,{children:i.map((function(e){return Object(v.jsx)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:Object(v.jsx)(f.a.Item,{name:e.key,label:e.label,rules:e.rules,children:E(e)})},e.key)}))})})})},q="".concat(window.location.protocol,"//").concat(window.location.host),A="".concat({PROD:{baseUrl:q},UAT:{baseUrl:q},QA:{baseUrl:q},DEV:{baseUrl:q},LOCAL:{baseUrl:"http://192.168.17.37:8080/"}}.DEV.baseUrl),J={search:"".concat(A,"/plc/search"),export:"".concat(A,"/plc/export"),confirm:"".concat(A,"/plc/confirm"),configs:"".concat(A,"/plc/configs"),secureClear:"".concat(A,"/secure/clear"),secureConfig:"".concat(A,"/secure/config"),secureConfigs:"".concat(A,"/secure/configs"),secureLogin:"".concat(A,"/secure/login"),secureSignal:"".concat(A,"/plc/signal"),test:"".concat(A,"/plc/test")},K=a(155),B=a.n(K),Y=(a(307),a(317)),W=a(187),Q=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(r.a)(this,a),(n=t.call(this,e)).state={loading:!1,percent:0,isProgress:!1},n}return Object(d.a)(a,[{key:"showProgress",value:function(e){this.setState({loading:!0,isProgress:!0,percent:e})}},{key:"show",value:function(){this.setState({loading:!0})}},{key:"hidden",value:function(){this.setState({loading:!1,isProgress:!1,percent:0})}},{key:"render",value:function(){var e=this.state,t=e.loading,a=e.isProgress,n=e.percent;return Object(v.jsx)("div",{style:{position:"fixed",zIndex:1001,left:0,top:0,width:"100%",height:"100%",display:"".concat(t?"flex":"none"),alignItems:"center",justifyContent:"center",background:"rgba(255,255,255, 0.5)"},children:a?Object(v.jsx)(Y.a,{type:"circle",percent:n}):Object(v.jsx)(W.a,{spinning:t})})}}]),a}(n.PureComponent),G=document.createElement("div");document.body.appendChild(G);var X=c.a.render(l.a.createElement(Q),G),Z=a(156),$=a.n(Z),_=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(r.a)(this,a),(n=t.call(this,e)).formRef=l.a.createRef(),n.handleCancel=function(){n.setState({visible:!1})},n.handleOk=function(){n.formRef.current.validateFields().then((function(e){console.log(e.password),console.log($()(e.password)),oe($()(e.password)).then((function(e){0===e.code&&(n.setState({visible:!1}),window.location.reload())}))})).catch((function(e){console.log(e)}))},n.state={visible:!1},n}return Object(d.a)(a,[{key:"show",value:function(){this.setState({visible:!0})}},{key:"render",value:function(){var e=this.state.visible;return Object(v.jsx)(p.a,{title:"\u8bf7\u8f93\u5165\u5bc6\u7801",closable:!1,maskClosable:!1,visible:e,onOk:this.handleOk,onCancel:this.handleCancel,footer:[Object(v.jsx)(j.a,{onClick:this.handleCancel,children:"\u53d6\u6d88"},"back"),Object(v.jsx)(j.a,{type:"primary",onClick:this.handleOk,children:"\u786e\u5b9a"},"submit")],children:Object(v.jsx)(f.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(v.jsx)(g.a,{children:Object(v.jsx)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:Object(v.jsx)(f.a.Item,{name:"password",label:"\u5bc6\u7801",children:Object(v.jsx)(N.a,{type:"password"})})})})})})}}]),a}(n.PureComponent),ee=document.createElement("div");document.body.appendChild(ee);var te=c.a.render(l.a.createElement(_),ee),ae=0;var ne=B.a.create({timeout:6e4,withCredentials:!0}),le=[],ie=B.a.CancelToken;ne.interceptors.request.use((function(e){e.withCredentials&&(X.show(),ae+=1),e.uploadOrDownload&&(e.timeout=0);var t=JSON.stringify(e.url)+JSON.stringify(e.data)+"&"+e.method;if(e.cancelToken||!le.includes(t)?le.push(t):e.url.indexOf("vendor/all")<0?e.cancelToken=new ie((function(e){return e()})):e.cancelToken="","get"===e.method){var a=(new Date).getTime();-1===e.url.indexOf("?")?e.url="".concat(e.url,"?t=").concat(a):e.url="".concat(e.url,"&t=").concat(a)}return e}),(function(e){return Promise.reject(e)})),ne.interceptors.response.use((function(e){(ae-=1)<=0&&X.hidden();var t=JSON.stringify(e.config.url)+JSON.stringify(e.config.data)+"&"+e.config.method;le.splice(le.findIndex((function(e){return e===t})),1);var a=e.config,n=e.data,l=n.code,i=n.msg;return"[object Blob]"===Object.prototype.toString.call(n)?e:l&&200!==l?(b.b.error(i),Promise.reject(e.data)):a.uploadOrDownload?e:e.data}),(function(e){ae=0,X.hidden(),le=[];var t=e.response&&e.response.status||e.status,a=(e.response&&e.response.data||{}).debugMessage;return 403===t?te.show():e.response&&b.b.error(a||"error"),Promise.reject(e)}));var ce=ne,oe=function(e){return ce.get("".concat(J.secureLogin,"?x-api-access-key=").concat(e))},se=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(r.a)(this,a),(n=t.call(this,e)).formRef=l.a.createRef(),n.modalItem=[{label:"\u4ea7\u54c1\u7c7b\u578b",controlType:"Input",placeholder:"\u8bf7\u8f93\u5165",key:"productType",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u4ea7\u54c1\u7c7b\u578b"}]},{label:"\u4ea7\u54c1\u7c7b\u578bID",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"productTypeId",rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u4ea7\u54c1\u7c7b\u578bID"}]},{label:"\u4e8c\u7ef4\u7801\u622a\u53d6\u5f00\u59cb\u4f4d\u7f6e",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"start"},{label:"\u4e8c\u7ef4\u7801\u622a\u53d6\u7ed3\u675f\u4f4d\u7f6e",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"end"},{label:"\u6d4b\u9ad8\u7cfb\u6570",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"ratio"},{label:"\u5408\u683c\u4f4d\u8d77\u59cb\u4f4d\u7f6e",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"qualifiedStart"},{label:"\u5408\u683c\u4f4d\u7ed3\u675f\u4f4d\u7f6e",controlType:"InputNumber",placeholder:"\u8bf7\u8f93\u5165",key:"qualifiedEnd"}],n.getConfigs=function(){var e;ce.get("".concat(J.secureConfigs),{params:e}).then((function(e){0===e.code&&(e.data.length>0&&e.data.map((function(e,t){e.key=e.id})),n.setState({dataSource:e.data}))}))},n.addHandle=function(){n.setState({isModalVisible:!0,title:"\u65b0\u589e\u914d\u7f6e",editData:{}})},n.addConfigItem=function(e,t){var a;console.log(e,t),e?(a=t,ce.post("".concat(J.secureConfig),a)).then((function(e){b.b.success("".concat(n.state.title,"\u6210\u529f\uff01")),n.getConfigs(),n.setState({editData:{},isModalVisible:!1})})):n.setState({isModalVisible:!1})},n.clearHandle=function(){p.a.confirm({title:"\u60a8\u786e\u8ba4\u8981\u6e05\u9664\u5417\uff1f",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){var e;(e={productTypeId:n.state.selectedRows[0].productTypeId},ce.post("".concat(J.secureClear),e)).then((function(e){0===e.code?(b.b.success("\u6e05\u9664\u6210\u529f\uff01"),n.getConfigs()):b.b.error(e.message||"\u6e05\u9664\u5931\u8d25\uff01")}))}})},n.editHandle=function(){n.setState({title:"\u7f16\u8f91\u914d\u7f6e",isModalVisible:!0,editData:n.state.selectedRows[0]})},n.onSelectChange=function(e,t){n.setState({selectedRowKeys:e,selectedRows:t})},n.state={columns:[{title:"\u5e8f\u53f7",dataIndex:"id",key:"id",width:165},{title:"\u4ea7\u54c1\u7c7b\u578b",dataIndex:"productType",key:"productType",width:165},{title:"\u4ea7\u54c1\u7c7b\u578bID",dataIndex:"productTypeId",key:"productTypeId",width:165},{title:"\u4e8c\u7ef4\u7801\u622a\u53d6\u5f00\u59cb\u4f4d\u7f6e",dataIndex:"start",key:"start",width:165},{title:"\u4e8c\u7ef4\u7801\u622a\u53d6\u7ed3\u675f\u4f4d\u7f6e",dataIndex:"end",key:"end",width:165},{title:"\u6d4b\u9ad8\u7cfb\u6570",dataIndex:"ratio",key:"ratio",width:165},{title:"\u5408\u683c\u4f4d\u8d77\u59cb\u4f4d\u7f6e",dataIndex:"qualifiedStart",key:"qualifiedStart",width:165},{title:"\u5408\u683c\u4f4d\u7ed3\u675f\u4f4d\u7f6e",dataIndex:"qualifiedEnd",key:"qualifiedEnd",width:165}],dataSource:[],selectedRowKeys:[],selectedRows:[],editData:{},isLight:0,isModalVisible:!1},n}return Object(d.a)(a,[{key:"componentDidMount",value:function(){this.getConfigs()}},{key:"render",value:function(){var e=this.state,t=e.columns,a=e.dataSource,n=e.isModalVisible,l=e.title,i=e.selectedRowKeys,c=e.editData,o={onChange:this.onSelectChange};return Object(v.jsxs)("div",{className:"page-container",children:[Object(v.jsx)(f.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(v.jsx)(g.a,{children:Object(v.jsxs)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:[Object(v.jsx)(j.a,{className:"btn submit-btn",disabled:1!==i.length,onClick:this.clearHandle,children:" \u6e05\u9664\u6570\u636e\u5e93\u914d\u7f6e "}),Object(v.jsx)(j.a,{className:"btn submit-btn",disabled:1!==i.length,onClick:this.editHandle,children:" \u7f16\u8f91\u914d\u7f6e "}),Object(v.jsx)(j.a,{className:"btn submit-btn",onClick:this.addHandle,children:" \u65b0\u589e "})]})})}),Object(v.jsx)(R,{columns:t,rowSelection:o,rowClassName:function(){return"center"},showHeader:!0,dataSource:a}),Object(v.jsx)(U,{isModalVisible:n,title:l,data:c,modalContent:this.modalItem,addConfigItem:this.addConfigItem})]})}}]),a}(l.a.Component),re=a(120),de=[{title:"\u5e8f\u53f7",dataIndex:"index",key:"index",width:60},{title:"\u6570\u636e\u6e90",dataIndex:"dataSource",key:"dataSource",width:100},{title:"\u65f6\u95f4",dataIndex:"logTime",key:"logTime",width:170},{title:"SR1000\u4e8c\u7ef4\u7801\u7f16\u53f7",dataIndex:"barcodeData",key:"barcodeData",width:420},{title:"\u4e8c\u7ef4\u7801\u5b57\u7b26\u63d0\u53d6",dataIndex:"barcode",key:"barcode",width:135},{title:"\u901a\u89c4\u529f\u80fd",dataIndex:"generalFunc",key:"generalFunc",width:135},{title:"\u6d4b\u9ad8\u529f\u80fd",dataIndex:"heightFunc",key:"heightFunc",width:135},{title:"\u6253\u6807\u529f\u80fd",dataIndex:"flagFunc",key:"flagFunc",width:135},{title:"\u8bfb\u7801\u529f\u80fd",dataIndex:"scanFunc",key:"scanFunc",width:135},{title:"\u53f0\u98ce\u529f\u80fd",dataIndex:"typhoonFunc",key:"typhoonFunc",width:135},{title:"\u69fd\u6df1\u68c0\u6d4b\u529f\u80fd",dataIndex:"slotDepthFunc",key:"slotDepthFunc",width:165},{title:"\u65cb\u8f6c\u9519\u8bef\u68c0\u6d4b",dataIndex:"spinCheckFunc",key:"spinCheckFunc",width:165},{title:"\u710a\u7f1d\u529f\u80fd",dataIndex:"weldFunc",key:"weldFunc",width:135},{title:"\u6d4b\u9ad8\u6570\u503c1",dataIndex:"heightMeasure1",key:"heightMeasure1",width:100},{title:"\u6d4b\u9ad8\u6570\u503c2",dataIndex:"heightMeasure2",key:"heightMeasure2",width:100},{title:"\u6d4b\u9ad8\u6570\u503c3",dataIndex:"heightMeasure3",key:"heightMeasure3",width:100},{title:"\u6d4b\u9ad8\u6570\u503c4",dataIndex:"heightMeasure4",key:"heightMeasure4",width:100},{title:"\u6d4b\u9ad8\u6570\u503c5",dataIndex:"heightMeasure5",key:"heightMeasure5",width:100},{title:"\u6d4b\u9ad8\u6570\u503c6",dataIndex:"heightMeasure6",key:"heightMeasure6",width:100},{title:"\u6d4b\u9ad8\u6570\u503c7",dataIndex:"heightMeasure7",key:"heightMeasure7",width:100},{title:"\u6d4b\u9ad8\u6570\u503c8",dataIndex:"heightMeasure8",key:"heightMeasure8",width:100},{title:"\u6d4b\u9ad8\u6570\u503c9",dataIndex:"heightMeasure9",key:"heightMeasure9",width:100},{title:"\u6d4b\u9ad8\u6570\u503c10",dataIndex:"heightMeasure10",key:"heightMeasure10",width:100},{title:"\u6d4b\u9ad8\u6570\u503c11",dataIndex:"heightMeasure11",key:"heightMeasure11",width:100},{title:"\u6d4b\u9ad8\u6570\u503c12",dataIndex:"heightMeasure12",key:"heightMeasure12",width:100},{title:"\u6d4b\u9ad8\u6570\u503c13",dataIndex:"heightMeasure13",key:"heightMeasure13",width:100},{title:"\u6d4b\u9ad8\u6570\u503c14",dataIndex:"heightMeasure14",key:"heightMeasure14",width:100},{title:"\u6d4b\u9ad8\u6570\u503c15",dataIndex:"heightMeasure15",key:"heightMeasure15",width:100},{title:"\u6d4b\u9ad8\u6570\u503c16",dataIndex:"heightMeasure16",key:"heightMeasure16",width:100},{title:"\u6d4b\u9ad8\u6570\u503c17",dataIndex:"heightMeasure17",key:"heightMeasure17",width:100},{title:"\u6d4b\u9ad8\u6570\u503c18",dataIndex:"heightMeasure18",key:"heightMeasure18",width:100},{title:"\u6d4b\u9ad8\u6570\u503c19",dataIndex:"heightMeasure19",key:"heightMeasure19",width:100},{title:"\u6d4b\u9ad8\u6570\u503c20",dataIndex:"heightMeasure20",key:"heightMeasure20",width:100}];var ue=["C","D","E","F"],he=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(e){var n;return Object(r.a)(this,a),(n=t.call(this,e)).options={title:"\u64cd\u4f5c",dataIndex:"options",key:"options",width:85,render:function(e,t){if("DUP"===t.duplicated)return Object(v.jsx)(j.a,{className:"table-sure-btn",onClick:n.confirmHandle.bind(Object(re.a)(n),t),children:"\u786e\u8ba4"})}},n.formRef=l.a.createRef(),n.timer=null,n.getConfigOption=function(){ce.post("".concat(J.configs)).then((function(e){if(0===e.code){var t=e.data.length>0&&e.data.map((function(e,t){return 0===t&&n.formRef.current.setFieldsValue({productTypeId:e.productTypeId}),{label:e.productType,value:e.productTypeId}}));n.setState({productOptions:t})}}))},n.getTableList=function(){var e=n.state,t=e.searchParam,a=e.page,l=e.size,i=n.formRef.current.getFieldsValue();delete i.date,n.setState({searchParam:Object(x.a)(Object(x.a)({page:1,size:10},i),{},{end:t.end,from:t.from})}),function(e){return ce.post("".concat(J.search),e,{withCredentials:!1})}(Object(x.a)(Object(x.a)({page:a,size:l},i),{},{end:t.end,from:t.from})).then((function(e){var t=e.code,i=e.data;0===t&&(i.content.map((function(e,t){e.index=t+1+(a-1)*l})),n.setState({dataSource:i.content,totalCount:i.totalElements}))}))},n.searchHandle=function(){n.getTableList()},n.resetHandle=function(){n.formRef.current.resetFields(),n.getTableList()},n.exportHandle=function(){var e,t=n.state.searchParam;(e=t,ce.post("".concat(J.export),e,{responseType:"blob"})).then((function(e){e&&function(e){var t;try{t=(t=e.headers["content-disposition"]&&e.headers["content-disposition"].split(";")[1].split("=")[1]).substr(7),t=decodeURIComponent(decodeURIComponent(t))}catch(c){console.log("file name is empty")}var a,n=t&&t.split(".");a="zip"===(n&&n[n.length-1])?new Blob([e.data]):new Blob([e.data],{type:"application/vnd.ms-excel;charset=utf-8"});var l=window.URL.createObjectURL(a),i=document.createElement("a");i.style.display="none",i.href=l,i.setAttribute("download",t),document.body.appendChild(i),i.click(),document.body.removeChild(i),window.URL.revokeObjectURL(l)}(e)}))},n.confirmHandle=function(e){p.a.confirm({title:"\u60a8\u786e\u8ba4\u8981\u63d0\u4ea4\u5417\uff1f",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){var t;(t={barcode:e.barcode},ce.post("".concat(J.confirm),t)).then((function(e){0===e.code?(b.b.success("\u786e\u8ba4\u6210\u529f"),n.getTableList()):b.b.error(e.message||"\u786e\u8ba4\u5931\u8d25\uff01")}))}})},n.onPageChange=function(e){n.setState({page:e},n.getTableList)},n.onSizeChange=function(e,t){n.setState({page:1,size:t},n.getTableList)},n.dateChange=function(e,t){var a=Object(x.a)(Object(x.a)({},n.state.searchParam),{},{end:t[1],from:t[0]});n.setState({searchParam:a})},n.state={columns:[n.options].concat(Object(y.a)(de)),dataSource:[],page:1,size:10,totalCount:0,searchParam:{end:null,from:null,productTypeId:"",barcode:"",barcodeData:"",productOptions:[]}},n}return Object(d.a)(a,[{key:"componentDidMount",value:function(){var e=this;this.getTableList(),this.getConfigOption(),this.timer=setInterval((function(){e.getTableList()}),1e3)}},{key:"componentWillUnmount",value:function(){clearInterval(this.timer),this.timer=null}},{key:"render",value:function(){var e=this.state,t=e.columns,a=e.dataSource,n=e.totalCount,l=e.page,i=e.size,c=[{label:"\u4ea7\u54c1\u7c7b\u578b",controlType:"Select",placeholder:"\u8bf7\u9009\u62e9",key:"productTypeId",col:{xs:24,sm:12,md:10,lg:10,xl:8},options:e.productOptions},{label:"\u65f6\u95f4",controlType:"RangePicker",placeholder:["\u5f00\u59cb\u65f6\u95f4","\u7ed3\u675f\u65f6\u95f4"],format:"YYYY-MM-DD",key:"date",col:{xs:24,sm:12,md:8,lg:8,xl:6},changeValue:this.dateChange},{label:"SR1000\u4e8c\u7ef4\u7801\u7f16\u53f7",controlType:"Input",placeholder:"\u8bf7\u8f93\u5165",key:"barcodeData",col:{xs:24,sm:12,md:8,lg:8,xl:6}},{label:"\u4e8c\u7ef4\u7801\u5b57\u7b26\u63d0\u53d6",controlType:"Input",placeholder:"\u8bf7\u8f93\u5165",key:"barcode",col:{xs:24,sm:12,md:8,lg:8,xl:6}}];return Object(v.jsxs)("div",{className:"page-container",children:[Object(v.jsx)(f.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(v.jsxs)(g.a,{children:[c.map((function(e){return Object(v.jsx)(m.a,{xs:24,sm:12,md:8,lg:8,xl:6,children:Object(v.jsx)(f.a.Item,{name:e.key,label:e.label,children:E(e)})},e.key)})),Object(v.jsxs)(m.a,{xs:24,sm:12,md:8,lg:6,xl:6,children:[Object(v.jsx)(j.a,{className:"btn submit-btn",onClick:this.searchHandle,children:"\u641c\u7d22"}),Object(v.jsx)(j.a,{className:"btn reset-btn",onClick:this.resetHandle,children:"\u91cd\u7f6e"})]}),Object(v.jsx)(m.a,{xs:24,sm:12,md:8,lg:6,xl:6,children:Object(v.jsx)(j.a,{className:"btn submit-btn",onClick:this.exportHandle,children:"\u5bfc\u51fa"})})]})}),Object(v.jsx)(R,{columns:t,showHeader:!0,rowClassName:function(e){var t="";switch(e.duplicated){case"DUP":t="bg-red";break;case"CONFIRMED":t="bg-yellow"}return e.qualified&&ue.includes(e.qualified.toUpperCase())&&(t="bg-red"),t},dataSource:a,total:n,pageIndex:l,pageSize:i,onPageChange:this.onPageChange,onSizeChange:this.onSizeChange})]})}}]),a}(l.a.Component),be=function(){return ce.get("".concat(J.test))},pe=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(){var e;Object(r.a)(this,a);for(var n=arguments.length,i=new Array(n),c=0;c<n;c++)i[c]=arguments[c];return(e=t.call.apply(t,[this].concat(i))).formRef=l.a.createRef(),e.testFormList=[{label:"SR1000\u4e8c\u7ef4\u7801\u7f16\u53f7",controlType:"Input",key:"barcodeData",disabled:!0,labelCol:{span:9}},{label:"\u4e8c\u7ef4\u7801\u5b57\u7b26\u63d0\u53d6",controlType:"Input",key:"barcode",disabled:!0,labelCol:{span:8}},{label:"\u901a\u89c4\u529f\u80fd",controlType:"Input",key:"generalFunc",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u529f\u80fd",controlType:"Input",key:"heightFunc",disabled:!0,labelCol:{span:9}},{label:"\u65cb\u8f6c\u9519\u8bef\u68c0\u6d4b",controlType:"Input",key:"spinCheckFunc",disabled:!0,labelCol:{span:8}},{label:"\u8bfb\u7801\u529f\u80fd",controlType:"Input",key:"scanFunc",disabled:!0,labelCol:{span:8}},{label:"\u53f0\u98ce\u529f\u80fd",controlType:"Input",key:"typhoonFunc",disabled:!0,labelCol:{span:9}},{label:"\u69fd\u6df1\u68c0\u6d4b\u529f\u80fd",controlType:"Input",key:"slotDepthFunc",disabled:!0,labelCol:{span:8}},{label:"\u6253\u6807\u529f\u80fd",controlType:"Input",key:"flagFunc",disabled:!0,labelCol:{span:8}},{label:"\u710a\u7f1d\u529f\u80fd",controlType:"Input",key:"weldFunc",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c1",controlType:"Input",key:"heightMeasure1",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c2",controlType:"Input",key:"heightMeasure2",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c3",controlType:"Input",key:"heightMeasure3",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c4",controlType:"Input",key:"heightMeasure4",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c5",controlType:"Input",key:"heightMeasure5",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c6",controlType:"Input",key:"heightMeasure6",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c7",controlType:"Input",key:"heightMeasure7",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c8",controlType:"Input",key:"heightMeasure8",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c9",controlType:"Input",key:"heightMeasure9",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c10",controlType:"Input",key:"heightMeasure10",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c11",controlType:"Input",key:"heightMeasure11",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c12",controlType:"Input",key:"heightMeasure12",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c13",controlType:"Input",key:"heightMeasure13",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c14",controlType:"Input",key:"heightMeasure14",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c15",controlType:"Input",key:"heightMeasure15",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c16",controlType:"Input",key:"heightMeasure16",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c17",controlType:"Input",key:"heightMeasure17",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c18",controlType:"Input",key:"heightMeasure18",disabled:!0,labelCol:{span:9}},{label:"\u6d4b\u9ad8\u6570\u503c19",controlType:"Input",key:"heightMeasure19",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u6570\u503c20",controlType:"Input",key:"heightMeasure20",disabled:!0,labelCol:{span:8}},{label:"\u6d4b\u9ad8\u7cfb\u6570(\u914d\u7f6e)",controlType:"Input",key:"ratio",disabled:!0,labelCol:{span:9}},{label:"\u91cd\u7801\u6807\u8bb0(7002)",controlType:"Input",key:"duplicate",disabled:!0,labelCol:{span:8}},{label:"\u4ea7\u54c1\u7c7b\u578b(7003)",controlType:"Input",key:"productTypeId",disabled:!0,labelCol:{span:8}}],e.testHandle=function(){be().then((function(t){0===t.code&&e.formRef.current.setFieldsValue(Object(x.a)({},t.data))}))},e}return Object(d.a)(a,[{key:"render",value:function(){return Object(v.jsx)("div",{className:"page-container",children:Object(v.jsx)(f.a,{className:"page-form",name:"search",ref:this.formRef,children:Object(v.jsxs)(g.a,{children:[Object(v.jsx)(m.a,{xs:24,sm:24,md:24,lg:24,xl:24,children:Object(v.jsx)(j.a,{className:"btn submit-btn",onClick:this.testHandle,children:" \u6d4b\u8bd5 "})}),this.testFormList.map((function(e){return Object(v.jsx)(m.a,{xs:24,sm:24,md:12,lg:8,xl:8,children:Object(v.jsx)(f.a.Item,{name:e.key,labelCol:e.labelCol,label:e.label,rules:e.rules,children:E(e)})},e.key)}))]})})})}}]),a}(l.a.Component),fe=[{path:"/",component:he},{path:"/config",component:se},{path:"/test",component:pe}],ge=a(82),me=function(e){var t=Object(n.useRef)(),a=Object(n.useState)("config-tip config-tishi"),l=Object(F.a)(a,2),i=l[0],c=l[1];Object(n.useEffect)((function(){return t.current=setInterval((function(){s()}),500),o}));var o=function(){clearInterval(t.current)},s=function(){ce.get("".concat(J.secureSignal),{withCredentials:!1}).then((function(e){if(0===e.code){var t=0===e.data?"config-tip config-tishi":"config-tip config-tishi-active";c(t)}}))};return Object(v.jsx)("div",{className:i})},je=function(e){Object(u.a)(a,e);var t=Object(h.a)(a);function a(){var e;Object(r.a)(this,a);for(var n=arguments.length,l=new Array(n),i=0;i<n;i++)l[i]=arguments[i];return(e=t.call.apply(t,[this].concat(l))).pathname=window.location.hash.substr(2)||"filter",e.state={current:e.pathname},e.handleClick=function(t){console.log("click ",t),e.setState({current:t.key})},e}return Object(d.a)(a,[{key:"render",value:function(){var e=this.state.current;return Object(v.jsxs)("div",{className:"page-header",children:[Object(v.jsxs)(ge.a,{onClick:this.handleClick,selectedKeys:[e],mode:"horizontal",children:[Object(v.jsx)(ge.a.Item,{children:Object(v.jsx)(o.b,{to:"/",children:" PLC \u63a7\u5236\u5668 "})},"filter"),Object(v.jsx)(ge.a.Item,{children:Object(v.jsx)(o.b,{to:"/test",children:" PLC \u6d4b\u8bd5 "})},"test"),Object(v.jsx)(ge.a.Item,{children:Object(v.jsx)(o.b,{to:"/config",children:" \u5b89\u5168\u63a7\u5236\u5668 "})},"config")]}),Object(v.jsx)(me,{})]})}}]),a}(l.a.Component),ye=je;var xe=function(){return Object(v.jsxs)(o.a,{children:[Object(v.jsx)(ye,{}),Object(v.jsx)(s.c,{children:fe.map((function(e,t){return Object(v.jsx)(s.a,{path:e.path,element:Object(v.jsx)(e.component,{})},t)}))})]})};c.a.render(Object(v.jsx)(l.a.StrictMode,{children:Object(v.jsx)(xe,{})}),document.getElementById("root"))}},[[310,1,2]]]);
//# sourceMappingURL=main.935d374d.chunk.js.map