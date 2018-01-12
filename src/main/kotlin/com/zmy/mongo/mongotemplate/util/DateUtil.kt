package com.bm001.oldermanagement.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter


//=================================时间格式化操作 =================================================

/**
 * 获取当前时间
 * @param  pattern:时间的格式 默认为yyyy-MM-dd HH:mm:ss
 * @return 当前格式表示
 */
fun LocalDateTime.getCurrentTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return this.format(DateTimeFormatter.ofPattern(pattern))
}

/**
 * 转化string为localDateTime
 * 使用："2017-01-01 00:00:00".parseLocalDateTime()
 * @param pattern： 默认格式
 * @return LocalDateTime
 */
fun String.parseLocalDateTime(pattern: String = "yyyy-MM-dd HH:mm:ss"): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))
}

/**
 * string  -> LocalDate
 */
fun String.parseLocalDate(pattern: String = "yyyy-MM-dd"): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))
}

// =====================================时间工具重载操作 =======================================

/**
 * 重载时间间隔操作
 */
operator fun LocalDate.minus(other: LocalDate): Period {
    return Period.between(other, this)
}


// ====================================生肖相关工具类 =================================================
val CHINESE_ZODIAC = arrayOf("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪")

val CONSTELLATION = arrayOf("白羊座","金牛座","双子座","巨蟹座","狮子座","处女座","天秤座","天蝎座","射手座","摩羯座","水瓶座","双鱼座")
/**
 * 获取1888年后生日的生肖
 */
fun LocalDate.zodiac(): String {
    return CHINESE_ZODIAC[(Math.abs((this - "1888-01-01".parseLocalDate()).years % 12))]
}

/**
 * 获取星座
 */
fun LocalDate.constellation(): String {
    return CONSTELLATION[this.monthValue - 1]
}

fun main(args: Array<String>) {
    data class Person(val name: String, val age: Int, val value: String)
    val list = listOf<Person>(Person("zmy", 11, "car"), Person("zmy", 22, "bus"), Person("java", 22, "car"))
    val b = list.groupBy { it.name }
    for ((k, v) in b) {
        print("$k -> $v")
    }
}


