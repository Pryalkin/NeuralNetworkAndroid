package com.bsuir.neural_network.app.dto.utils

object  Authority {
    val USER_AUTHORITIES = arrayOf("user:read")
    val PERSON_AUTHORITIES = arrayOf("user:read", "user:create_app")
    val MANAGER_AUTHORITIES = arrayOf("user:read", "user:edit", "user:decorate")
    val ADMIN_AUTHORITIES = arrayOf("user:read", "user:create_app", "user:edit", "user:decorate")
}

