package com.bm001.oldermanagement.util

import org.springframework.http.HttpStatus

/**
 * 默认成功返回信息
 */
const val DEFAULT_SUCCESS_MESSAGE = "success"
const val DEFAULT_FAIL_MESSAGE = "fail"

/**
 * 返回码
 */
enum class ResultCode(var code: Int) {
    // 操作成功：所有的成功
    SUCCESS(200),
    // 操作失败：宽泛的失败
    FAIL(400),
    // 未登录：没有token
    NOT_LOGIN(202),
    // 未授权：没有权限
    UNAUTHORIZED(401),
    // 未认证：账户或密码错误
    UNAUTHENTICATED(402),
    // 未发现资源：查询不到资源
    NOT_FOUND(404),
    // 删除失败
    FAIL_DELETE(405),
    // 创建失败
    FAIL_CREATE(406),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500)
}



data class Result(val code: Number, val data: Any?, val message: String?)


/**
 * 成功返回一条Mono
 */
fun getSuccessMono(data: Any?): Result {
    return Result(ResultCode.SUCCESS.code, data, DEFAULT_SUCCESS_MESSAGE)
}

/**
 * 成功返回列表flux
 */
fun getSuccessFlux(count: Number?, list: Any?): Result {
    return Result(ResultCode.SUCCESS.code, mapOf("count" to count, "list" to list), DEFAULT_SUCCESS_MESSAGE)
}

/**
 * 失败返回信息
 */
fun getFail(message: String = DEFAULT_FAIL_MESSAGE, status: ResultCode = ResultCode.FAIL): Result {
    return Result(status.code, null, message)
}








