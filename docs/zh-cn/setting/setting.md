> 配置主要由application(SpringBoot配置)和thinker(本框架配置)组成
> 

## application(SpringBoot配置)

thinker-admin-box-web项目的
- src/main/resources
  - [application.yml](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/resources/application.yml)
    (应用基础配置)
  - [application-dev.yml](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/resources/application-dev.yml)
    (本地开发环境配置)
  - [application-prod.yml](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/resources/application-prod.yml)
    (线上部署环境)

## thinker(本框架配置)
- src/main/resources
  - [thinker.yml](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/resources/thinker.yml)
    (本框架基础配置)
- src/main/java/com/thinker/adminweb/langs
  - [class Extend ILangInfo](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/java/com/thinker/adminweb/langs/ThinkerLangInfo.java)
    (语言包配置，所有继承了`ILangInfo`的`@Configuration`类都会被系统自动读取)
