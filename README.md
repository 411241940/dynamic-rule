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

    代码加载接口，提供load方法加载规则的代码
    
    通过spi实现动态扩展
- HandlerFactory

    创建规则实例，依赖注入
- Invoker
    
    规则调用

### springboot-example
基于springboot搭建的一个使用样例