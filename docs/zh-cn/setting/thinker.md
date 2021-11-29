## thinker.yml配置

[thinker.yml](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/resources/thinker.yml)
(本框架基础配置)

```yaml
thinker:
  sharding:
    # 需要分表的表名: 分表类型(day/week/month/year)
    validmap: day
  token:
    # 是否开启aop切面，可使用@CheckLoginAspect/@CheckRoleAspect/@CheckRuleAspect
    aop: false
    # 是否单点登录，true的话会顶掉其他人
    singleLogin: false
    # 是否可以续期，若可以，则每次验证TOKEN时，会进行同等比例的时间延长
    renewal: true
    # token生成使用的类，当前只定义了jwt，可自行定义
    tokenimpl: jwt
    # 继承了LoginAbstract的登录代码，分别为[执行类class/jwt秘钥/过期时间]
    authTable:
      - action: com.thinker.framework.admin.login.AdminLogin
        secret: 198rhn1pr1u7041012n181j2
        expire: 86400
    # id字段
    idKey: userId
    # jwt对应TOKEN字段
    tokenKey: Access-Token
    # 多重角色区分字段
    typeKey: Access-Type
    # 加密的aspect需要验证
    signAspect:
      - com.thinker.framework.framework.abstracts.defaultimpl.SignAspectDefaultImpl
  # 配置参数
  config:
    # 登录的地址
    loginUrl: admin.html
    # 不过滤的xss名称，否则<p>等会被过滤
    xssNames:
      - content
    # 所有的资源路径，List<String>
    resourcePaths:
      # - pdfs #如添加，则系统会在启动时在同级目录下创建./pdfs/文件夹
    # 跨域地址设置, string
    allowedOrigins: "*"
    # 上传文件地址
    uploadPath: uploads/
    # 上传图片可用后缀
    uploadImageSuffix:
      - jpg
      - jpeg
      - png
      - bmp
      - gif
    # 上传图片大小，1024*1024*10=10M
    uploadImageSize: 10000000
    # 上传文件可用后缀
    uploadFileSuffix:
      - jpg
      - jpeg
      - png
      - bmp
      - gif
      - flv
      - mkv
      - avi
      - rmvb
      - mpeg
      - mov
      - wmv
      - mp4
      - mp3
      - wav
      - rar
      - zip
      - 7z
      - doc
      - docx
      - xls
      - xlsx
      - ppt
      - pptx
      - pdf
      - txt
      - skp
    # 上传文件大小，1024*1024*10=10M
    uploadFileSize: 10000000
    # 默认交换机 (引入thinker-admin-queue必须，其他情况下可为空)
    rabbitExchange: thinker_box
    # 默认队列 (引入thinker-admin-queue必须，其他情况下可为空)
    rabbitQueueDefault: thinker_box_default
    # 订单队列 (引入thinker-admin-queue必须，其他情况下可为空)
    rabbitQueueOrder: thinker_box_order
    # 队列失败重试次数 (引入thinker-admin-queue必须，其他情况下可为空)
    rabbitRetry: 5
    # 队列失败后，延迟x秒进行重试 (引入thinker-admin-queue必须，其他情况下可为空)
    rabbitRetryDelay: 60

  # 微信相关配置 todo
  wechat:
    minapp:
      - appid:
        secret:
        token:
        aesKey:
        msgDataFormat: JSON
```

## 语言包配置

[ThinkerLangInfo](https://github.com/yirius/thinker-admin-box-web/blob/master/src/main/java/com/thinker/adminweb/langs/ThinkerLangInfo.java)
(语言包配置，所有继承了`ILangInfo`的`@Configuration`类都会被系统自动读取)

> 设置后，可以在vue环境下直接使用
> 
> this环境下: this.$t("message.system.title")
> 
> setup环境下: 
> 
>   import i18n from "@/locale";  
>   i18n.global.t("message.system.title")

```java
package com.thinker.adminweb.langs;

import cn.hutool.core.lang.Dict;
import com.thinker.framework.framework.langs.ILangInfo;
import org.springframework.context.annotation.Configuration;

// 需要添加，让springboot自行管理bean
@Configuration
public class ThinkerLangInfo extends ILangInfo {

  @Override
  public Dict getZhCn() {
    return Dict.create().set(
            "system",
            Dict.create()
                    .set("title", "网站title")
                    .set("subTitle", "网站子标题")
    );
  }

  @Override
  public Dict getEn() {
    return Dict.create().set(
            "system",
            Dict.create()
                    .set("title", "Title")
                    .set("subTitle", "Subtitle")
    );
  }
}
```