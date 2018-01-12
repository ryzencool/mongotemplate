package com.zmy.mongo.mongotemplate.dao.repository

import com.zmy.mongo.mongotemplate.dao.JpaMongoTemplate
import com.zmy.mongo.mongotemplate.domain.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

/**
 * @Author: zmy
 * @Description:
 * @Date: Created in 16:56 2018/1/12
 */
/**
 * 使用jpa自己的api完成
 */
@Repository
interface UserRepository : ReactiveMongoRepository<User, String>


/**
 * 使用自定义继承类完成
 */
@Component
class UserTemplate : JpaMongoTemplate<User>()

