# OOAD 3-4

## 须知

记得先启动 eureka-server 服务器，不然有的模块跑不起来

## 规定

1. 标准组的代码独立在 standard 模块，在各个模块的 domain 层进行继承（取名为 Mall + 标准组的类名），然后在其它地方都使用自己的 domain。
2. 自己的 domain 层应该有一个别名（取名为 mall + 该类的名字），注意：定义 Po 的类为和数据库映射的类，应该有别名，如 MallProductPo 的别名为 mallProductPo。
3. 测试代码不要提交到 github 上，提交过的就算了。
4. 在自己写的类上加上 `@author 自己的名字`，或者添加在别人的后面。
5. 配置文件也不要提交到 github 上。

## 说明

### 各模块及端口号

- [X] eureka 9000
- [X] common 公共模块 无端口
- [X] standard 标准组模块 无端口
- [X] log 8082 待测试
- [ ] cart 8083 
- [X] topic 8084 待测试
- [X] ad 8086 
- [X] user 8888
- [ ] goods-info 8081

### 日志

- 2019-12-07
    
    - goods 洪永团负责
    - brand 张有坤负责
    - goods category 吴啸明负责
    - product 沈湘越负责
    
    maven 配置最好和我同步，已经排除很多版本冲突问题。
- 2019-12-08
    
    - topic log 洪永团负责
    - cart 张有坤负责
    
- 2019-12-12
    
    - [ ] feign 第一次启动失败问题
    - [ ] 用户管理员权限问题
    - [ ] 写日志问题
    - [ ] 完成 Goods 模块