package org.clkrw.mobile.data.repository

import org.clkrw.mobile.data.api.ClickerApi
import org.clkrw.mobile.domain.model.Grant
import org.clkrw.mobile.domain.repository.RolesRepository


class RolesRepositoryImpl(
    private val clickerApi: ClickerApi,
) : RolesRepository {
    override suspend fun grantAccess(showId: String, userEmail: String) {
        clickerApi.openAccessToShow(showId, userEmail)
    }


    override suspend fun revokeAccess(showId: String, userEmail: String) {
        clickerApi.closeAccessToShow(showId, userEmail)
    }
}
