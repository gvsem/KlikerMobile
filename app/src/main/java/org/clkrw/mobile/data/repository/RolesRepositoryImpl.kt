package org.clkrw.mobile.data.repository

import org.clkrw.mobile.data.api.ClickerApi
import org.clkrw.mobile.domain.model.Grant
import org.clkrw.mobile.domain.repository.ResponseCode
import org.clkrw.mobile.domain.repository.RolesRepository
import org.clkrw.mobile.domain.repository.toResponseCode


class RolesRepositoryImpl(
    private val clickerApi: ClickerApi,
) : RolesRepository {
    override suspend fun grantAccess(showId: String, userEmail: String): ResponseCode {
        val code = clickerApi.openAccessToShow(showId, userEmail).code()

        return toResponseCode(code)
    }


    override suspend fun revokeAccess(showId: String, userEmail: String): ResponseCode {
        val code = clickerApi.closeAccessToShow(showId, userEmail).code()

        return toResponseCode(code)
    }
}
