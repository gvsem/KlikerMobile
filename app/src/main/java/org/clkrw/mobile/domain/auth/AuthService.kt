package org.clkrw.mobile.domain.auth

interface AuthService {
    fun getAuthToken(): String?
    fun setAuthToken(token: String?)
}