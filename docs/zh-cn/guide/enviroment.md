## JDK
> MINJDK: 1.8(JAVA8)
> 
> JDK 8官方下载地址：https://www.oracle.com/technetwork/java/javase/downloads

# Redis
> 项目启动需要Redis，所以必须安装。
> 
> Redis 下载地址：https://github.com/MicrosoftArchive/redis/releases
> 
> Windows可以安装[PHPSTUDY](https://www.xp.cn/download.html), MacOs也可以直接 brew install redis或安装[MxSrvs](http://www.xsrvs.com/docs.html)

# Mysql
> 项目启动需要Mysql，所以必须安装。
>
> Redis 下载地址：https://dev.mysql.com/downloads/windows/installer/5.6.html
>
> Windows可以安装[PHPSTUDY](https://www.xp.cn/download.html), MacOs也可以直接 brew install mysql@5.6或安装[MxSrvs](http://www.xsrvs.com/docs.html)

# Rabbitmq
> 如果不需要使用队列，则无需参考该章节
> 
> 具体安装请参考https://blog.csdn.net/qq_26718271/article/details/76577356
> 
> 启动命令rabiitmq-server
> 
> thinker-admin-box-web项目中使用必须引入delaymessage，前往https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases
> 
> 放倒plugins目录中，然后rabbitmq-plugin enable delayed-message-exchange(根据list自行读取)