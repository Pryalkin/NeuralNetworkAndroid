package com.bsuir.neural_network.app.dto.utils

import com.bsuir.neural_network.app.dto.utils.Authority.ADMIN_AUTHORITIES
import com.bsuir.neural_network.app.dto.utils.Authority.SUBSCRIBER_AUTHORITIES
import com.bsuir.neural_network.app.dto.utils.Authority.USER_AUTHORITIES

enum class Role(vararg authorities: String) {
    ROLE_USER(*USER_AUTHORITIES),
    ROLE_SUBSCRIBER(*SUBSCRIBER_AUTHORITIES),
    ROLE_ADMIN(*ADMIN_AUTHORITIES);

    val authorities: Array<String>

    init {
        this.authorities = authorities as Array<String>
    }
}
