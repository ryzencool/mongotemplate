package com.zmy.mongo.mongotemplate.domain

import com.zmy.mongo.mongotemplate.annotation.NoArg
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 *
 * @Author: zmy
 * @Description: 用户类
 * @Date: Created in 16:57 2018/1/12
 */

@NoArg
@Document
data class User(@Id val id: String, val name: String?, var age: Int?)