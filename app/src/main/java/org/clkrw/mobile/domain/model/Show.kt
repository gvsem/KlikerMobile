package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Show(
    val id: String,
    val currentSlideNo: Int,
    val maxSlidesCount: Int,
    val presentation: Presentation,
)