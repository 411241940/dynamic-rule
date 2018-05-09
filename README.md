## 简介
- 通过java与groovy整合，实现动态规则调用

## Quick Start
1. 直接运行springboot-example中的`WebApplication.mail()`
2. 访问http://127.0.0.1:8080

## 模块介绍
### rule-core
- bootstrap

    初始化
- Handler

    规则执行接口，提供一个handle方法实现业务逻辑
- Loader

    - 代码加载器，提供load方法加载规则的代码，add方法新增规则，update方法更新规则。
    
    - 可选择的加载器有：db、redis、file
    
    - 通过spi实现动态扩展
- HandlerFactory

    创建规则实例，依赖注入
- Invoker
    
    暴露规则操作的方法

- Serializer

    序列化工具，通过spi实现动态扩展

### rule-starter
自定义spring-boot-starter，项目只需依赖starter即可快速启动使用
```
<dependency>
    <groupId>com.bin</groupId>
    <artifactId>spring-boot-starter-dynamic-rule</artifactId>
    <version>1.0</version>
</dependency>
```

### springboot-example
基于springboot搭建的一个使用样例