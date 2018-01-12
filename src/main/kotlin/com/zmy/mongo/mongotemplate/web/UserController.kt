package com.zmy.mongo.mongotemplate.web

import com.bm001.oldermanagement.util.Result
import com.bm001.oldermanagement.util.getSuccessFlux
import com.bm001.oldermanagement.util.getSuccessMono
import com.zmy.mongo.mongotemplate.dao.repository.UserRepository
import com.zmy.mongo.mongotemplate.dao.repository.UserTemplate
import com.zmy.mongo.mongotemplate.domain.User
import com.zmy.mongo.mongotemplate.util.PageQuery
import com.zmy.mongo.mongotemplate.util.copyPropertiesWithoutNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * @Author: zmy
 * @Description:
 * @Date: Created in 17:05 2018/1/12
 */
@RestController
class UserController(val userRepository: UserRepository,
                     val userTemplate: UserTemplate) {

    /**
     * 此处能使用内联来提高性能， 所以lambda并没有使用默认值，不可得兼啊
     */
    @GetMapping("/get")
    fun get(user: User): Mono<Result>? {
        return userTemplate.queryGeneral(user, { it })
                .collectList()
                .map { getSuccessMono(it) }
    }


    /**
     * 带有额外条件的查询, 查询很复杂可抽取查询
     */
    @GetMapping("/get/condition")
    fun getCondition(user: User): Mono<Result>? {
//        val condition= {c : Criteria -> c.and("age").lte(10)}
        return userTemplate.queryGeneral(user, { it.and("age").lte(10) })
                .collectList()
                .map { getSuccessMono(it) }
    }

    /**
     * 对于复杂查询，可以使用Tuple.kt中的tuple类作为中间传递对象
     */
    @GetMapping("/get/page")
    fun getPage(user: User, pageQuery: PageQuery): Mono<Result>? {
        return userTemplate.queryGeneralSortAndPage(user, pageQuery.getPageRequest(), { it })
                .map {
                    it.age = 10
                    it
                }
                .collectList()
                .zipWith(userTemplate.countGeneral(user, { it }))
                .map { getSuccessFlux(it.t2, it.t1) }
    }

    @PostMapping("/add")
    fun add(user: User): Mono<Result>? {
        return userRepository.save(user).map { getSuccessMono(it) }
    }

    /**
     * 使用copyPropertiesWithoutNull()来完成剔除null
     */
    @PostMapping("/update")
    fun update(user: User): Mono<Result>? {
        return userRepository.findById(user.id).flatMap {
            copyPropertiesWithoutNull(user, it)
            userRepository.save(it)
        }.map {
            getSuccessMono(it)
        }
    }

    @PostMapping("/delete/{id}")
    fun delete(@PathVariable id: String): Mono<Result>? {
        return userRepository.deleteById(id)
                .map { getSuccessMono(it) }
    }
}