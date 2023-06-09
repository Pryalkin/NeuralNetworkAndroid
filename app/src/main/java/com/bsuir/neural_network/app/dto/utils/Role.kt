package com.bsuir.neural_network.app.dto.utils

import com.bsuir.neural_network.app.dto.utils.Authority.ADMIN_AUTHORITIES
import com.bsuir.neural_network.app.dto.utils.Authority.MANAGER_AUTHORITIES
import com.bsuir.neural_network.app.dto.utils.Authority.PERSON_AUTHORITIES
import com.bsuir.neural_network.app.dto.utils.Authority.USER_AUTHORITIES

enum class Role(vararg authorities: String) {

    ROLE_USER(*USER_AUTHORITIES),
    ROLE_PERSON(*PERSON_AUTHORITIES),
    ROLE_MANAGER(*MANAGER_AUTHORITIES),
    ROLE_ADMIN(*ADMIN_AUTHORITIES);

    val authorities: Array<String>

    init {
        this.authorities = authorities as Array<String>
    }
}
