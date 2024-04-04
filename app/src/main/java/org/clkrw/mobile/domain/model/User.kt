package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdAt: String,
    val pictureURL: String,
    val googleId: String,
)