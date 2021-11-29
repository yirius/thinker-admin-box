- framework
    - abstracts
        - renders
            - AssemblyAbstract.class *:Base Class For /renders/form/assemblys/*.class, All Like Input/Switch/Select Use This*
            - AssemblyLayoutAbstract.class *:Assembly's Layout like ThinkerForm/Page/Table/Tab Use This*
        - LayoutAbstract.class *:All render's class base this*
        - LoginAbstract.class *:/admin/login/AdminLogin.class is a simple*
    - aspect 
        - logs
            - LogAopAdvice.class *:use log.warn to record*  
              *params: value(string)*
            - RedisLogAopAdvice.class *:use Redis to record*  
              *params: value(string)*
            - TableLogAopAdvice.class *:use TkLogs Table to record*  
              *params: value(string)*
            - ThinkerLogAspect.class
            - ThinkerRedisLogAspect.class
            - ThinkerTableLogAspect.class
        - sign
            - SignAopAdvice.class *:use signAspect list<String> to run sign verify*
            - ThinkerSignAspect.class
    - config
        - mybatis
            - MybatisAutoFillConfig.class *注入创建和更新时间*
            - MybatisPlusConfig.class *设置分表操作, day/week/month/year*
        - redis
            - RedisConfig.class *Redis配置文件*
        - ThinkerConfig.class *注册xss过滤*
        - WebAppConfig.class *注册跨域和文件夹过滤*
    - database
        - entity
            - ThinkerEntity.class *基准Entity，有id/createTime/updateTime三个参数*
        - exceptions
            - LazyWithFillException.class *延迟加载连表，异常错误*
            - UpdateException.class *更新数据异常*
        - mybatis
            - ThinkerIService.class *基础Service*
            - ThinkerMapper.class *基础Mapper*
            - ThinkerServiceImpl.class *Impl的基础实现*
        - relation
            - parser
                - EntityConfig.class *Entity的相关配置，保存时需要*
                - ParserHasEntity.class *对HasManyEntity和HasOneEntity进行处理*
            - HasManyEntity.class *拥有多个子Entity*
            - HasOneEntity.class *拥有多个一个Entity*
        - services
          - pagelist
              - PageListService.class *ThinkerServiceImpl.pageList()调用*
          - BasePageServices.class *PageServices的基础类*
          - PageServiceResult.class *返回的result类型*
        - sharding
            - ShardingTypes.class *内部所有处理类的基类*
            - DaySharding.class *按天分表*
            - WeekSharding.class *按周分表*
            - MonthSharding.class *按月分表*
            - YearSharding.class *按年分表*
        - utils
            - DatabaseUtil.class *Db相关操作类*
            - ParamsParserUtil.class *对eq/like/in等进行格式化*
            - ShardingUtil.class *分表的便捷操作*
        - wrapper
            - ThinkerQueryChainWrapper.class *Impl.thinkerQuery()对应*
            - ThinkerWrapper.class *替换基础wrapper*
    - entity
        - bo
            - LoginResult.class *login返回的参数，格式化后方便其他继承*
        - vo
            - LabelValue.class *{label:'',value:''}*
            - TextValue.class *{text:'',value:''}*
            - WrapperValue.class *{alias:'',expression:'',field:''}*

****Admin Return Code****

### base code
***
fail: 0 | success: 1
***
###admin: 200-299
message.thinker.admin.emptyUsername: 201
message.thinker.admin.userNotFound: 202
message.thinker.admin.userStatusFail: 203
message.thinker.admin.passwordIncorrect: 204
message.thinker.admin.emptyPassword: 205
message.thinker.admin.notDelete: 206
###uploads: 300-399
message.thinker.uploads.mkdirError: 360
message.thinker.uploads.uploadError: 361
message.thinker.uploads.sizeMaxError: 362
message.thinker.uploads.suffixError: 363
###exception: 400 - 499
message.thinker.exceptions.fillNoField: 440  
message.thinker.exceptions.updateNoParam: 450  
message.thinker.exceptions.updateNoWhere: 451
message.thinker.exceptions.systemError: 0
###token: 500 - 599
message.thinker.token.noPermission: 501  
message.thinker.token.tokenMulti: 502 | 1001
message.thinker.token.inValid: 503 | 1001
message.thinker.token.emptyToken: 504 | 1001
message.thinker.token.emptyVercode: 505
message.thinker.token.vercodeIncorrect: 506