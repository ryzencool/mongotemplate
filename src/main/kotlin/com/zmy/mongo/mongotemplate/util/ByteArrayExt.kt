package com.zmy.mongo.mongotemplate.util

import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory

/**
 * byte转databuffer
 */
fun ByteArray.toDataBuffer(dataBufferFactory: DataBufferFactory): DataBuffer? {
    val df = dataBufferFactory.allocateBuffer(600)
    df.write(this)
    return df
}