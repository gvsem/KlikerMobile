package org.clkrw.mobile.data.repository

import org.clkrw.mobile.data.api.ClickerApi
import org.clkrw.mobile.domain.model.User
import org.clkrw.mobile.domain.repository.UserRepository


class UserRepositoryImpl(
    private val clickerApi: ClickerApi,
) : UserRepository {
    override suspend fun getCurrentUser(): User {
        return clickerApi.getUserInfo()
    }
}
