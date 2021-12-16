## 前端开发规范

#### 一、项目用到的技术栈
1. react
2. react-router
3. antd 
4. axios
5. scss/less;（如果要修改antd主题颜色，最好是用less）
### 二、项目的目录
1. components 公共组件
2. config 接口的配置
3. pages 路由对应的组件
4. routes 路由
5. utils 公用方法等

#### 三、代码编写时注意点
##### (一)、命名规范

###### 1.1 目录命名

    全部采用小写方式， 以中划线分隔，有复数结构时，要采用复数命名法， 缩写不用复数
    正例： scripts / styles / components / images / utils / layouts 
    反例： script / style / demo_scripts / demoStyles 

###### 1.2  JS、CSS、SCSS、LESS、HTML、PNG 文件命名

    全部采用小写方式，以中划线分割
    正例： render-dom.js / signup.css / index.html / company-logo.png
    反例： renderDom.js / UserManagement.html

###### 1.3 命名严谨性

    代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式。 
    说明：正确的英文拼写和语法可以让阅读者易于理解，避免歧义。
    注意，即使纯拼音命名方式也要避免采用
    正例：henan / luoyang / rmb 等国际通用的名称，可视同英文。
    反例：DaZhePromotion [打折] / getPingfenByName() [评分] / int 某变量 = 3

  杜绝完全不规范的缩写，避免望文不知义：
  反例：AbstractClass“缩写”命名成 AbsClass；condition“缩写”命名成 condi，此类随意缩写严重降低了代码的可阅读性。

##### (二)、HTML规范

###### 2.1 类型
  推荐使用 HTML5 的文档类型声明：
  （建议使用 text/html 格式的 HTML。避免使用 XHTML。XHTML 以及它的属性，比如 application/xhtml+xml 在浏览器中的应用支持与优化空间都十分有限）。

* 规定字符编码
* IE 兼容模式     
* doctype 大写

```
  <!DOCTYPE html>
  <html>
    <head>
      <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
      <meta charset="UTF-8" />
      <title>Page title</title>
    </head>
    <body>
      <img src="images/company-logo.png" alt="Company" />
    </body>
  </html>
```
###### 2.2 缩进
* 缩进使用两个空格（一个tab）
* 嵌套的节点应该缩进

##### (三)、Less规范
###### 3.1 代码组织
1） 将公共less文件放置在style/less/common文件夹

    例：common.less/main.less
2） 按一下顺序组织
  * @import
  * 变量声明
  * 样式声明

    ```
      @import "";
      
      @default-text-color: #333;

      .page {
        width: 960px;
        margin: 0 auto;
      }
    ```
##### 3.2 避免嵌套层级过多
  * 将嵌套深度限制在3级
  * 避免大量的嵌套规则

##### (四)、JavaScript规范

###### 4.1 命名
1) 采用小写驼峰命名 lowerCamelCase, 代码中的命名均不能以下划线，也不能以下划线或美元符号结束
2) 方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase 风格，必须遵从驼峰形式。

*特此说明：增删改查、详情同意使用如下5个单词*
> add/delete/update/get/detail

###### 4.2 代码格式
1) 使用两个空格进行缩进（即一个tab）
2) 不同逻辑、不同语义、不同业务的代码之间插入一个空行分割开来以提升可读性
> 说明：任何情形，没有必要插入多个空行进行隔开。

###### 4.3 字符串
统一使用单引号(')，不要使用双引号(");
> let str = 'foo';

###### 4.4 对象声明
1) 使用字面值创建对象
> let user = {};
2) 使用字面量来代替对象构造器
```
  let user = {
    age: 18,
    name: 'demo'
  }
```

###### 4.5  使用 ES6,ES7
必须优先使用 ES6,ES7 中新增的语法糖和函数。这将简化你的程序，并让你的代码更加灵活和可复用。

> 必须强制使用 ES6, ES7 的新语法，比如箭头函数、await/async，解构，let，for…of 等等

###### 4.6 条件判断和循环最多三层

条件判断能使用三目运算符和逻辑运算符解决的，就不要使用条件判断，但是谨记不要写太长的三目运算符。如果超过 3 层请抽成函数，并写清楚注释。

##### (五)、react代码规范

###### 5.1 基础规则

1) 一个文件声明一个组件： 尽管可以在一个文件中声明多个 React 组件，但是最好不要这样做；推荐一个文件声明一个 React 组件，并只导出一个组件；

2) 使用 JSX 表达式： 不要使用 React.createElement 的写法；

3) 函数组件和 class 类组件的使用场景： 如果定义的组件不需要 props 和 state ，建议将组件定义成函数组件，否则定义成 class 类组件。

###### 5.2 组件声明

1) 组件名称和定义该组件的文件名称建议要保持一致；

    推荐
    > import Footer from './Footer';

    不推荐：
    >import Footer from './Footer/index';

2) 不要使用 displayName 属性来定义组件的名称，应该在 class 或者 function 关键字后面直接声明组件的名称。

    推荐：
    > export default class MyComponent extends React.Component {}

    不推荐：
    > export default React.Component({
        displayName: 'MyComponent',
      });

###### 5.3 React 中的命名

1) 组件名称： 推荐使用大驼峰命名;
2) 属性名称： React DOM 使用小驼峰命令来定义属性的名称，而不使用 HTML 属性名称的命名约定；
3) style 样式属性： 采用小驼峰命名属性的 JavaScript 对象；

    推荐：
    ```
    // 组件名称
    MyComponent
    // 属性名称
    onClick
    // 样式属性
    backgroundColor
    ```
###### 5.4 JSX 写法注意
1) 标签
   * 当标签没有子元素的时候，始终使用自闭合的标签 。
      > 推荐 `<Component />`
      > 不推荐 `<Component></Component>`
   * 如果标签有多行属性，关闭标签要另起一行 。
      推荐 
      ``` 
        <Component
          bar="bar"
          baz="baz"
        />
      ```
      不推荐
      ```
        <Component
          bar="bar"
          baz="baz" />
      ```
   * 当组件跨行时，要用括号包裹 JSX 标签。
      推荐：
      ```
        render() {
          return (
            <MyComponent className="long body" foo="bar">
              <MyChild />
            </MyComponent>
          );
        }
      ```
      不推荐
      ```
        render() {
          return <MyComponent className="long body" foo="bar">
                  <MyChild />
                </MyComponent>;
        }
      ```
###### 5.5 key 属性设置
key 帮助 React 识别哪些元素改变了，比如被添加或删除。因此你应当给数组中的每一个元素赋予一个确定的标识。当元素没有确定 id 的时候，万不得已你可以使用元素索引 index 作为 key，但是要主要如果列表项目的顺序可能会变化，如果使用索引来用作 key 值，因为这样做会导致性能变差，还可能引起组件状态的问题。

推荐：
```
  {todos.map(todo => (
    <Todo
      {...todo}
      key={todo.id}
    />
  ))}
```
不推荐：
```
  {todos.map((todo, index) =>
    <Todo
      {...todo}
      key={index}
    />
  )}
```
###### 5.6 State
* 不要直接修改 state
除了 state 初始化外，其它地方修改 state，需要使用 setState( ) 方法，否则如果直接赋值，则不会重新渲染组件。

  >推荐： this.setState({comment: 'Hello'});

  >不推荐：this.state.comment = 'hello';
* State 的更新可能是异步的
出于性能考虑，React 可能会把多个 setState( ) 调用合并成一个调用；因为 this.props 和 this.state 可能会异步更新，所以这种场景下需要让 setState() 接收一个函数而不是一个对象 。

  推荐：
  ```
  this.setState((state, props) => ({
    counter: state.counter + props.increment
  }));
  ```
  不推荐：
  ```
  this.setState({
    counter: this.state.counter + this.props.increment,
  });
  ```