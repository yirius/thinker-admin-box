## 基础配置

[application.yml](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/resources/application.yml)
内的配置一般无需改变，除了server.port

```yaml
server:
  servlet:
    # 当前项目运行基础路径，如(https://xxx/project/可读取的项目路径)，则需要填写/project/
    context-path: /
  # 打开的端口，一般调试阶段无需更改
  port: 4041
  # 对文件进行gzip压缩
  compression:
    enabled: true
    mime-types: application/javascript,text/css,text/plain,application/xml,text/html,text/xml
  # jar运行后的提交参数格式+最大可接受进程
  tomcat:
    uri-encoding: utf-8
    max-http-form-post-size: 20MB
    max-swallow-size: 20MB
    threads:
      max: 2000

spring:
  # 当前运行的是application-dev.yml还是其他的
  profiles:
    active: dev
  #大小限制
  servlet:
    multipart:
      max-file-size: 20MB
      max-request-size: 20MB
  #设置抛出报错
  mvc:
    throw-exception-if-no-handler-found: true
  #设置json解码
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  #aop切面
  aop:
    proxy-target-class: true
    auto: true
  #设置static可访问，其他在config中配置
  web:
    resources:
      static-locations: classpath:/static/

mybatis-plus:
  # 实体类扫描路径
  type-aliases-package: com.thinker.**.entity
  # xml 扫描路径
  mapper-locations: classpath:mapper/*/*.xml
  configuration:
    jdbc-type-for-null: null
    cache-enabled: true
  global-config:
    # 关闭 mybatis-plus的 banner
    banner: false
```

## 运行时配置
如果是打包配置，请参考[JAR打包和运行](/zh-cn/setting/jar.md)

```yaml
spring:
  datasource:
    dynamic:
      # 是否开启 SQL日志输出，生产环境建议关闭，有性能损耗
      p6spy: true
      hikari:
        connection-timeout: 30000
        max-lifetime: 600000
        max-pool-size: 15
        min-idle: 5
        connection-test-query: select 1
      # 配置默认数据源
      primary: base
      datasource:
        # 数据源-1，名称为 base
        base:
          username: localhost
          password: localhost
          driver-class-name: com.mysql.cj.jdbc.Driver
          # adminbox为数据库名称，需自行修改
          url: jdbc:mysql://127.0.0.1:3306/adminbox?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false&useJDBCCompliantTimezoneShift=true&useAffectedRows=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2b8

  redis:
    # Redis数据库索引（默认为 0）
    database: 1
    # Redis服务器地址
    host: 127.0.0.1
    # Redis服务器连接端口
    port: 6379
    # Redis 密码
    password:
    jedis:
      pool:
        # 连接池中的最小空闲连接
        min-idle: 10
        # 连接池中的最大空闲连接
        max-idle: 2000
        # 连接池最大连接数（使用负值表示没有限制）
        max-active: 2000
        # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-wait: 1000
    # 连接超时时间（毫秒）
    timeout: 1000

  # rabbit队列(如果没有引入thinker-admin-queue的话，可以删除这一段)
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
    virtual-host: /
    publisher-returns:  true #开启发送失败退回
    publisher-confirm-type: correlated #确认回调
    template:
      mandatory: true #保证监听有效
    listener:
      simple:
        retry:
          enabled: true  #支持重试/重发
        concurrency: 2 #同时支持最小2个并发
```