# OOAD 3-4

## 规定

1. 标准组的代码独立在 standard 模块，在各个模块的 domain 层进行继承（取名为 Mall + 标准组的类名），然后在其它地方都使用自己的 domain。
2. 自己的 domain 层应该有一个别名（取名为 mall + 该类的名字），注意：定义 Po 的类为和数据库映射的类，应该有别名，如 MallProductPo 的别名为 mallProductPo。
3. 测试代码不要提交到 github 上，提交过的就算了。
4. 在自己写的类上加上 `@author 自己的名字`，或者添加在别人的后面。
5. 配置文件也不要提交到 github 上。

## 说明

### 端口号

- goods-info 8081
- log 8082
- cart 8083
- topic 8084
- eureka-server 9000

### 日志

- 2019-12-07
    
    - goods 洪永团负责
    - brand 张有坤负责
    - goods category 吴啸明
    - product 沈湘越
    
    maven 配置最好和我同步，已经排除很多版本冲突问题。
- 