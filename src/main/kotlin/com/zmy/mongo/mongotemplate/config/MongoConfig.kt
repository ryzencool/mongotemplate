package com.zmy.mongo.mongotemplate.config

import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import javax.annotation.PostConstruct

/**
 * @Author: zmy
 * @Description: mongodb 配置类
 * @Date: Created in 16:38 2018/1/10
 */

@Configuration
class MongoConfig {

    @Value("\${spring.data.mongodb.host}")
    lateinit var host: String

    @Value("\${spring.data.mongodb.port}")
    lateinit var port: String

    @Value("\${spring.data.mongodb.database}")
    lateinit var database: String

//    @Value("\${spring.data.mongodb.username}")
//    lateinit var username: String
//
//    @Value("\${spring.data.mongodb.password}")
//    lateinit var password: String

    /**
     * 静态属性
     */
    companion object {
        lateinit var HOST: String
        lateinit var PORT: String
        lateinit var DATABASE: String
//        lateinit var USERNAME: String
//        lateinit var PASSWORD: String
        lateinit var mongoTemplate: ReactiveMongoTemplate
    }

    //    初始化静态属性
    @PostConstruct
    fun init() {
        HOST = host
        PORT = port
        DATABASE = database
//        USERNAME = username
//        PASSWORD = password
//        mongoTemplate = ReactiveMongoTemplate(MongoClients.create("mongodb://${this.username}:${this.password}@${this.host}:${this.port}/${this.database}"), database)
        mongoTemplate = ReactiveMongoTemplate(MongoClients.create("mongodb://${this.host}:${this.port}/${this.database}"), database)
    }
}