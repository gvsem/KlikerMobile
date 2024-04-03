package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Presentation(
    val id: String,
    val foreignPresentationId: String,
    val title: String,
    val thumbnailUrl: String,
)