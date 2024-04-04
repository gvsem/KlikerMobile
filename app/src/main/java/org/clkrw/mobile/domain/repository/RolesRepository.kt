package org.clkrw.mobile.domain.repository

import org.clkrw.mobile.domain.model.Grant

interface RolesRepository {
    suspend fun grantAccess(showId: String, userEmail: String)
    suspend fun revokeAccess(showId: String, userEmail: String)
}