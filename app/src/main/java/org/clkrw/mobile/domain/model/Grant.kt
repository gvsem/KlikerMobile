package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Grant(
    val id: String,
    val createdAt: String,
    val toWhomGranted: User,
)
