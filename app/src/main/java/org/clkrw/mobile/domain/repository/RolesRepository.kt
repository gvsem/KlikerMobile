package org.clkrw.mobile.domain.repository

interface RolesRepository {
    suspend fun grantAccess(showId: String, userEmail: String): ResponseCode
    suspend fun revokeAccess(showId: String, userEmail: String): ResponseCode
}