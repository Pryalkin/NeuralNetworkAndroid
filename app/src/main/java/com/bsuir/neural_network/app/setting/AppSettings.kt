package com.bsuir.neural_network.app.setting

interface AppSettings {

    /**
     * Get auth token of the current logged-in user.
     */
    fun getCurrentToken(): String?

    /**
     * Set auth token of the logged-in user.
     */
    fun setCurrentToken(token: String?)

    fun getCurrentUsername(): String?

    fun setCurrentUsername(username: String?)

    fun setCurrentRole(role: String?)

    fun getCurrentRole(): String?


}