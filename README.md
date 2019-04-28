## 简介
- 通过java与groovy整合，实现动态规则调用

## Quick Start
1. 直接运行springboot-example中的`WebApplication.main()`
2. 访问http://127.0.0.1:8080

## 模块介绍
### rule-core
- bootstrap

    - 初始化
    
    - 根据配置，选择加载的SPI实现类
    
- broadcast

![](https://raw.githubusercontent.com/411241940/dynamic-rule/master/images/Broadcaster类图.png)

    - 广播模块，当规则修改后，通知到每个监听服务进行规则刷新
    
    - 目前使用zookeeper Watcher机制
    
    - 可通过spi实现动态扩展

- Handler

    规则执行接口，提供一个handle方法实现业务逻辑
    
- Loader

![](https://raw.githubusercontent.com/411241940/dynamic-rule/master/images/Loader类图.png)

    - 代码加载器，提供load方法加载规则的代码，add方法新增规则，update方法更新规则。
    
    - 已支持的加载器有：db、redis、file
    
    - 可通过spi实现动态扩展
    
- HandlerFactory

    创建规则实例，依赖注入

- Invoker
    
    暴露规则操作的方法

- Serializer

![](https://raw.githubusercontent.com/411241940/dynamic-rule/master/images/Serializer类图.png)

    - 序列化工具
    
    - 已支持的序列化工具有：JavaSerializer、KryoSerializer
    
    - 可通过spi实现动态扩展

### rule-starter
自定义spring-boot-starter，项目只需依赖starter即可快速启动使用
```
<dependency>
    <groupId>com.bin</groupId>
    <artifactId>spring-boot-starter-dynamic-rule</artifactId>
    <version>1.0</version>
</dependency>
```

#### 支持的 Spring Boot 配置
```java
@ConfigurationProperties(prefix = "rule")
public class RuleProperties {

    // 选择使用的 loader ,默认为 FileLoader
    private String loader = "file";

    // 选择使用的 serializer ,默认为 KryoSerializer
    private String serializer = "kryo";

    // redis 相关配置
    private String redisHost;
    private int redisPort;
    private String redisPassword;

    // db 相关配置
    private String dbUrl;
    private String dbDriverClassName;
    private String dbUsername;
    private String dbPassword;

    // 选择使用的 broadcaster ,默认为 ZkBroadcaster
    private String broadcaster = "zk";

     // zk 配置
    private String zkServer;
	
}
```

#### 配置示例
`application.properties`
```properties
rule.loader=redis
rule.serializer=kryo

rule.zkServer=127.0.0.1:2181

rule.redisHost=127.0.0.1
rule.redisPort=6379
```

### springboot-example
基于springboot搭建的一个使用样例

## 流程

### 调用规则时序
![](https://raw.githubusercontent.com/411241940/dynamic-rule/master/images/调用规则时序.jpg)

### 添加规则时序
![](https://raw.githubusercontent.com/411241940/dynamic-rule/master/images/添加规则时序.jpg)

### 更新规则时序
![](https://raw.githubusercontent.com/411241940/dynamic-rule/master/images/更新规则时序.jpg)