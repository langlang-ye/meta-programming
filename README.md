# 元编程
---

### 使用 Java(jdk11) 语言, 基于反射元编程等技术, 开启的造轮子之路...
###  概述

* tiga: 迪迦, 类似 Mybatis 的小框架, 目前仅支持单表的增删改查 一级缓存等, 使用 HikariCP 数据源. 迪迦像光一样快... 哈哈 配乐: 奇迹再现  
* tiga_test: 测试项目, 可以与tiga进行测试...

### 项目目标
tiga: 旨在通过一个简单易用的框架, 帮助掌握理解Mybatis 设计思想, 从而对Mybatis有更深的认识与理解, 在实际工作中可以有
更深刻的使用或者拓展原有的功能, 对工作排查问题有所帮助. 项目使用到工厂方法, 动态代理等设计模式也可以理解一下设计模式在项目中的使用.
tiga_test: 配合 tiga 进行测试的项目, 提供了初始化的sql 脚本,核心配置文件和mapper 配置文件, 另外包含项目测试的所有测试案例, 可以让初学者快速学习使用. 

### tiga 功能
1. 默认使用 HikariCP 数据源
2. 支持对单表的增删改查操作, 可以在 xml 文件写sql, 同时支持注解的方式
3. 支持配置JavaBean的短别名, 默认提供了Java基础类型的别名映射
4. 支持配置mapper包名
5. 默认开启支持一级缓存

### 在 maven 中使用

```xml
 <dependency>
    <groupId>com.langlang</groupId>
    <artifactId>tiga</artifactId>
    <version>1.0-SNAPSHOT</version>
 </dependency>
```

更具体的使用细节参见:
tiga_test/src/main/resources/sqlMapConfig.xml

### 项目可能存在未发现的bug, 而且并没有进行全面的测试. 建议项目可以用于个人学习研究, 不可用在生产环境, 以免造成不必要的麻烦!

### 致谢
* 欢迎诸位大佬指导.


