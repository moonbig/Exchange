# 一、项目目标
1. ZTuo数字资产交易平台（以下简称“ZTuo”）是ZTuo团队历经了多个数字资产交易系统开发之后，通过不断的优化精简，首家开源的Java语言开发的数字货币交易所平台，支持当前多种主流的数字货币交易（测试环境受限于服务器的成本，目前支持USDT、ETH、BTC等主流货币，或根据实际情况开放一定测试币种）。
2. ZTuo平台采用前后端分离的开发模式，后端负责业务实现，前端负责数据展示，同时包含有对应的APP。
3. ZTuo团队遵循“技术共享”的理念，决定开源ZTuo平台，以供大家参考和学习（如用于商业化项目，对此所带来的法律和经济问题，ZTuo团队概不负责）。团队小伙伴的能力有限，不足之处也在所难免，这也是团队希望开源的最终目的，希望借助开源社区的程序猿/媛们的力量，对ZTuo平台提出更多的意见和想法，提出更好的改进方案和策略，在业务和技术上共同进步，共同提高！
4. 再次声明，ZTuo平台仅用于学习实验，如有用于商业化项目，请自行咨询法律政策，技术无罪，但请遵纪守法！同时，一切用于商业化项目所带来的法律和经济问题，ZTuo团队概不负责！

### ZTuo平台使用的技术
1. 后端：Spring、SpringMVC、SpringData、SpringCloud、SpringBoot
2. 数据库：Mysql、Mongodb
3. 其他：redis、kafka、阿里云OSS、腾讯防水校验
4. 前端：Vue、iView、less
5. 同时提供IOS和Android版本。

# 二、项目资源
1. [ZTuoExchange_framework](https://github.com/xunibidev/ZTuoExchange_framework
2. [ZTuoExchange_wallet](https://github.com/xunibidev/ZTuoExchange_wallet
3. [ZTuoExchange_web](https://github.com/xunibidev/ZTuoExchange_web
4. [ZTuoExchange_IOS](https://github.com/xunibidev/ZTuoExchange_IOS
5. [ZTuoExchange_android](https://github.com/xunibidev/ZTuoExchange_android
6. [ZTuoExchange_admin_web](https://github.com/xunibidev/ZTuoExchange_admin_web
7. [ZTuoExchange_操作手册](https://github.com/xunibidev/ZTuoExchange_manual

# 三、项目维护计划
1. ZTuoExchange_wallet项目计划2018年11月16日开源发布
2. 项目架构设计文档--待完善
3. 项目部署设计文档--待完善
4. 项目测试文档计划--待完善
5. 项目重点业务逻辑说明计划--待完善
6. 其他使用手册计划--待完善
7. 目前测试环境所用ETH、BTC、USDT节点均为真实币种节点，后面会完全采用私有节点来代替，具体时间根据开发人员进度发布，同时希望有能力的大神接入，共同维护，共同学习

# 四、测试环境
* 测试环境仅供学习参考！！！请各位学习的小伙伴合理运用，不要恶意的攻击。
* 开发的小伙伴正在拼命的搭建测试环境，请各位耐心等待。
* 测试环境地址见操作手册。

# 五、关于我们
* 为方便大家交流和学习，请各位小伙伴加入QQ交流群。
* 区块链交易所技术交流【QQ群：735446452】。


# 六、开源许可协议
* MIT
---

# 后端基础框架（ZTuoExchange_framework）

## 写在前面
1. 项目用了Lombok插件，无论用什么IDE工具，请务必先安装Lombok插件
2. 项目用了QueryDsl，如果遇见以Q开头的类找不到，请先编译一下对应的core模块，例如core、exchange-core、xxx-core这种模块
3. 找不到的jar包在项目jar文件夹下
4. jdk版本1.8以上
5. 初始化sql在sql文件夹中配置文件
配置文件打开这个设置会自动建表
#jpa
spring.jpa.hibernate.ddl-auto=update

## 修改配置
1. msyql数据库;
2. reids;
3. mongodb(主要存储K线图相关数据);
4. kafka
5. 阿里云OSS，图片资源上传
6. 短信配置
7. 邮件认证
8. 腾讯防水校验

## 模块说明
1. cloud
* 提供SpringCloud微服务注册中心功能，为基础模块，必须部署
* 依赖服务：无
2. ucenter-api
* 提供用户相关的接口（如登录、注册、资产列表）,该模块为基础为基础模块，必须部署
* 依赖服务：mysql,kafka,redis,mongodb,短信接口，邮箱账号
3. otc-api
* 提供场外交易功能接口，没有场外交易的可以不部署
* 依赖服务：mysql,redis,mongodb,短信接口
4. exchange-api
* 提供币币交易接口，没有币币交易的项目可以不部署
* 依赖服务：mysql,redis,mongodb,kafka
5. chat
* 提供实时通讯接口，基础模块，需要部署
* 依赖服务：mysql,redis,mongodb
6. admin
* 提供管理后台的所有服务接口，必须部署
* 依赖服务：mysql,redis,mongodb
7. wallet
* 提供充币、提币、获取地址等钱包服务，为基础模块，必须部署
* 依赖服务：mysql,mongodb,kafka,cloud
8. market
* 提供币种价格、k线、实时成交等接口服务，场外交易不需要部署
* 依赖服务：mysql,redis,mongodb,kafka,cloud
9. exchange
* 提供撮合交易服务，场外交易不需要部署
* 依赖服务：mysql,mongodb,kafka

## 项目启动说明
 1. 先启动cloud模块，再启动market，exchange模块，剩下的没有顺序
 2. 启动命令：java -jar cloud.java &
 
## 架构设计
请参考上面的“项目维护计划”

## 重点业务详解
请参考上面的“项目维护计划”

## 提问和建议
1. 使用Issuse


## 更新说明
1. 20181106 提交项目中缺少的模块
2. 20181112 开源ZTuoExchange_wallet代码
3. 20181112 发布ZTuoExchange_操作手册


捐赠支持

BTC：3AU5QZb591Vs63SAzdhebBZpQ6qj9GVCNm

LTC：33tZdhUBqhB2s3JoUEsuBhHQ75taW12WAc

ETH：0x38d7f0c2d5054941717bde236a7f0aa94e89d5af
