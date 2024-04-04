package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val id: String,
    val presentation: Presentation,
    val currentSlideNo: Int,
    val maxSlidesCount: Int,
    val updatedAt: String,
    val shorts: List<ShortUrl>,
    val grants: List<Grant>,
    val owner: User,
)