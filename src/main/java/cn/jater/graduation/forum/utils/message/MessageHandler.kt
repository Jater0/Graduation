package cn.jater.graduation.forum.utils.message

class MessageHandler<T>(val code: Int, val msg: String, val data: T?) {
    constructor(code: Int, data: T?): this(code, "request", data)
}