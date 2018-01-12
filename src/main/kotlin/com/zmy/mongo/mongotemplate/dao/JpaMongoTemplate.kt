package com.zmy.mongo.mongotemplate.dao

import com.zmy.mongo.mongotemplate.config.MongoConfig
import com.zmy.mongo.mongotemplate.util.sortAndPageDesc
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.reflect.ParameterizedType

/**
 * @Author: zmy
 * @Description: 方便模糊查询的jpa查询方法封装
 * @Date: Created in 16:36 2018/1/10
 */

open class JpaMongoTemplate<T> {

    // 获取泛型的类型
    private val clazz: Class<*> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>

    /**
     * 返回查询条件， 该条件是所有的模糊查询和准确查询条件
     * @param condition: 查询orm条件对象
     */
    fun criteria(condition: Any?): Criteria {
        val criteria = Criteria()
        val fields = clazz.declaredFields
        try {
            for (field in fields) {
                field.isAccessible = true
                if (field.get(condition) != null) {
                    if (field.type.name == java.lang.String::class.java.name) {
                        criteria.and(field.name).regex(field.get(condition) as String)
                    } else if (field.type.name == Int::class.java.name
                            || field.type.name == Double::class.java.name
                            || field.type.name == Float::class.java.name
                            || field.type.name == Long::class.java.name) {
                        criteria.and(field.name).`is`(field.get(condition))
                    }
                }
            }
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e.message)
        }
        return criteria
    }

    /**
     * 数据库简便查询，如果有特殊查询，拓展查询再lambda中做
     * @param condition: 数据库实体对象
     * @param criteriaExtend: 添加查询条件的lambda
     */
    inline fun <reified T> queryGeneral(condition: T, criteriaExtend: (e: Criteria) -> Criteria): Flux<T> {
        return MongoConfig.mongoTemplate.find(Query.query(criteriaExtend(criteria(condition))), T::class.java)
    }


    /**
     * 数据库查询，条数
     * 如果为了开发方便可以再lambda加入noinline设置默认值，但是这样会降低性能
     * @param condition: 数据库实体对象
     * @param criteriaExtend: 添加查询条件的lambda
     */
    inline fun <reified T> countGeneral(condition: T, criteriaExtend: (e: Criteria) -> Criteria): Mono<Long> {
        return MongoConfig.mongoTemplate.count(
                Query.query(
                        criteriaExtend(criteria(condition)
                        )),
                T::class.java)
    }


    /**
     * 分页和排序查询
     * 如果为了开发方便可以再lambda加入noinline设置默认值，但是这样会降低性能
     * @param condition: 数据库实体对象
     * @param pageRequest: 分页信息
     * @param sortProperty: 排序依据属性
     * @param criteriaExtend: 添加查询条件的lambda,如果是简单查询，使用默认值
     */
    inline fun <reified T> queryGeneralSortAndPage(condition: T, pageRequest: PageRequest, criteriaExtend: (e: Criteria) -> Criteria, sortProperty: String = "id"): Flux<T> {
        return MongoConfig.mongoTemplate.find(Query.query(criteriaExtend(criteria(condition))).sortAndPageDesc(pageRequest, sortProperty), T::class.java)
    }
}