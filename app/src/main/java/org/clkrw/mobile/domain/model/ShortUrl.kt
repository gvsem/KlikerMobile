package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortUrl(
    val shortIdentifier: String,
)
