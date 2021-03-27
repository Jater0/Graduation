## Implement

**小程序通过调用`/forum/create_article/{id}/{{classify}}`接口Post数据给后端， 后端显示获取文章链接中的内容**

> 为什么不直接传递文章内容: 
>
> ​	通过性能测试: 在系统的网速条件下, 当我们向服务器传递的是整篇文章的情况下使用的时间和CPU使用率都是要比先调用OSS接口传输文档然后系统调用解析URL获取文章的情况要多得多

**获取完内容之后, 调用系统审核模块, 如果不通过返回`requestCode` 400 告诉小程序内容审核不通过, 如果通过的话, 开辟两个线程`Thread1`调用`Lucene`解析文章分词和创建索引;`Thread2`调用`MyBatis`接口, `MyBatis`会调用在`Mysql`服务器预编译的自定义Function`create_article_user`**

> **使用Custom Function的目的**
>
> **MyBatis设计复杂的SQL语句会导致性能不佳, 但是自定义的函数会比在MyBatis设计复杂SQL的性能高, 而且Mysql会存有这个Function的缓存, 当我们再一次调用这个函数的时候, 会比上次的时间短, 再搭配上MyBatis的缓存, 更是大大提高请求接口的性能**







# Step by Step to implement article or topic

**在系统设计之初, 采用了话题和文章的两种模式, 虽然在浏览上面存在着区别, 但是在编辑话题和文章系统的方法使用顺序基本是一致的, 只是调用的接口和方法不同, 所以下面段落合并的解释如何创建话题和文章**

**首先在小程序方面, 当用户在完成话题或文章的编辑的时候, 小程序会先将文本内容转换为一个临时的File对象, 但我们完成的请求这个File对象会被清空, 节约用户的使用内存; 生成File对象之后, 小程序调用OSS接口将文章发送到阿里云的OSS服务器.成功之后,小程序会调用接口将将链接和相关的信息Post给后端服务器**

**在服务器方面, 系统会先去请求链接中的文本内容, 调用完成之后, 将文本信息通过系统的审核接口先进行文本的审核,审核不通过----系统返回状态码400 告诉用户审核没有通过, 审核通过 ---- 开辟两个线程, 线程1将文本、标题和作者通过Lucene分词并索引, 线程2将文本的链接和其他信息通过MyBatis来调用Mysql中自定义的函数和过程来存入数据库**

> **为什么使用OSS**
>
> - **使用OSS中间件的速率会比直接将文本传输给后端的执行速率快**
> - **使用OSS链接存入数据库不会有文本字数限制要求**
>
> **为什么使用自定义函数和自定义过程**
>
> - **使用MyBatis设计复杂的SQL函数虽然和自定义函数/过程的执行效率差不多, 但是Mysql编写的自定义函数和过程会被缓存,再搭配上MyBatis的缓存, 那么在大量请求的前提下, 使用的执行效率会比单独使用其中一个要快**



## Administrator System Design

**使用了ElectronJS和VueJS技术进行开发**

**关于Electron**

**是一套使用HTML5技术进行桌面应用开发的技术, 通过在不同的平台编译, 可以生成在不同平台可运行的安装包**

**额外的在Ubuntu中, 加入了go脚本对服务器进行请求, 由于Javascript一些原生的特性, 使用Go脚本可以**





**微信小程序端采用的是uni-app框架进行设计，包含浏览、搜索、编辑、评论、信息收集、个人信息管理、作品管理、用户关注管理和反馈等9个模块的功能。**

**Wechat mini program system is designed with uni-app framework, which includes nine modules including browsing, searching, editing, commenting, information collection, personal information management, works management, user followed management and feedback**



**后台管理端采用的是Electron + Vue.Js + Bootstrap技术开发，包含统计信息查询、个人信息管理、管理员管理、创建新的管理员、用户管理、编辑、反馈处理、浏览文章、添加标签和发布通知等10个模块的功能。**

**Back-end Management system is designed with Electron + Vue.Js + Bootstrap, whice includes ten modules including statistical information query, personal information management, administrator management, create a new administrator account, user management, editing, handling feedback, browsing articles, add new label and publish notification**

**服务器使用SpringBoot + MyBatis + Mysql + Redis + Lucene等框架进行开发，为前端提供API，为持久化服务、文本审核和搜索等提供技术支持**

**Server system is design with frameworks of SpringBoot + MyBatis + Mysql + Redis + Lucene, support the API for the front-end system; provide technical support for persistence services, text auditing and search**

**用户桌面端采用的是Electron + Vue.Js + Bootstrap技术开发，包含修改个人信息和编辑的功能，因为微信小程序端编辑文本的不便利性，所以开发桌面端为用户提供良好的编辑体验**

**Desktop App is designed with Electron + Vue.js + Bootstrap, includes personal information management and editing; because Wechat mini program can't provide editing function perfectly, therefore, the development of desktop application provides users with a good editing experience**

