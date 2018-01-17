### 目标：简化spring mongo data jpa来进行复杂的查询

##### 技术栈：
    *  kotlin
    *  springboot2.0
    *  webflux, reactor
    *  reactive mongodb
    *  spring mongo data jpa
    
##### 总结：

    *  spring data jpa中虽然对查询基于方法命名的方式，但是再处理字段非常多的查询的时候会十分的繁琐，
       通过继承JpaMongoTemplate<T>，内部方法可以方便的实现基础查询和拓展查询

    *  通过tuple元组类作为函数式中的中间变量

    *  copyPropertiesWithoutNull()， copyPropertiesWithoutNullIgnoreId() 简化更新操作中的赋值

    *  添加方便的kotlin拓展函数

    *  添加bean时， 注意空类型和非空类型
