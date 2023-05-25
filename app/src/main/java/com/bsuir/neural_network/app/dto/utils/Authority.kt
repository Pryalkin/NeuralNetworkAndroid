package com.bsuir.neural_network.app.dto.utils

object Authority {
    val USER_AUTHORITIES = arrayOf("user:read")
    val SUBSCRIBER_AUTHORITIES = arrayOf("user:read", "user:watch")
    val ADMIN_AUTHORITIES = arrayOf("user:read", "user:watch", "user:create")
}
