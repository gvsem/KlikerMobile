package org.clkrw.mobile.domain.repository

import org.clkrw.mobile.domain.model.User


interface UserRepository {
    suspend fun getCurrentUser(): User
}
