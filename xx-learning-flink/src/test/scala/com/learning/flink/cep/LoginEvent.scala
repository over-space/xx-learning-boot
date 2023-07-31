package com.learning.flink.cep

/**
 * @author over.li
 * @since 2023/7/18
 */
case class LoginEvent(id: Long, username: String, loginType: String, loginTime: Long)

