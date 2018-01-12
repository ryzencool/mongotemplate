package com.zmy.mongo.mongotemplate.util

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Query

/**
 * 分页条件，只分页
 */
fun Query.paging(pageRequest: PageRequest): Query {
    return this.with(pageRequest)
}

/**
 * 分页和排序，升序
 */
fun Query.sortAndPageAsc(pageRequest: PageRequest, property: String): Query? {
    return this.with(Sort.by(Sort.Direction.ASC, property)).paging(pageRequest)
}

/**
 * 分页和排序，降序
 */
fun Query.sortAndPageDesc(pageRequest: PageRequest, property: String): Query {
    return this.with(Sort.by(Sort.Direction.DESC, property)).paging(pageRequest)
}

/**
 * 分页对象
 */
class PageQuery(private val pageIndex: Int, // 页码
                private val pageSize: Int  // 单页条数
) {
    fun getPageIndexFromZero(): Int {
        return pageSize - 1
    }

    fun getPageRequest(): PageRequest {
        return PageRequest.of(this.pageIndex - 1, this.pageSize)
    }
}
