> Thinker-Admin-Box 是一个基于Spring Boot 2.6.0 & Mybatis-plus 3.4.3 构建的自建TOKEN的权限管理系统。

![image](https://img.shields.io/badge/build-success-brightgreen.svg?longCache=true&style=flat-square)
![image](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?longCache=true&style=flat-square)
![image](https://img.shields.io/badge/springboot-2.6.0-yellow.svg?longCache=true&style=popout-square)
![image](https://img.shields.io/badge/mybatisPlus-3.4.3-blue.svg?longCache=true&style=popout-square)

- Thinker-Admin-Box是一个简单高效的后台权限管理系统。
- JAVA框架采用 Spring Boot 2.6.0，引入大量新型机制。
- 数据库交互采用Mybatis/Mybatis-Plus，参考THINKPHP引入了自定义DATABASE组件，可进行快捷连表字段查询。
- 参考SA-TOKEN自定义TOKEN权限，可实现对按钮级别的权限控制。
- 界面使用[VUE-ADMIN-BOX](https://github.com/yirius/vue-admin-box/tree/thinker)构建，并提供多套配色以供选择。

## 功能模块
```bash
|-------------------------------------------|  
|  _____  _      _         _                |  
| /__   \| |__  (_) _ __  | | __ ___  _ __  |  
|   / /\/| '_ \ | || '_ \ | |/ // _ \| '__| |  
|  / /   | | | || || | | ||   <|  __/| |    |  
|  \/    |_| |_||_||_| |_||_|\_\\___||_|    |  
|             @Author: Yirius               |  
|           @QQ Group: 735838842            |  
|-------------------------------------------|
|--系统设置
|   |--规则管理
|   |--角色管理
|   |--成员管理
|--系统监控
|   |--登录日志
|   |--请求日志
|   |--系统日志
|   |--错误及告警日志
|   |--服务器监控
|   |--库表文件生成
```

# 技术选型
## 后端
- 后端
  - SpringBoot 2.6.0
  - 数据库链接: Mybatis-Plus 3.4.3
  - 数据库连接池: hikari
  - 数据缓存: Redis
  - 延迟通知队列: RabbitMq(若引用thinker-admin-queue)
  - 其他: HuTool All/FastJSON/lombok等

## 前端
- 前端([vue-admin-box](https://github.com/cmdparkour/vue-admin-box))
  - MVVM框架: vue3
  - 工程化管理: vite2
  - UI框架: element-plus
  - 路由管理: vue-router4
  - 状态管理: vuex4
  - 数据请求: axios
  - 实用工具库: @vueuse/core

## 开发环境
- 开发环境
  - JDK最低版本：1.8(JAVA 8)
  - 依赖管理：maven&pom
  - 数据库版本：mysql 5.5+
