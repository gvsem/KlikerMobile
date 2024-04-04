package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ClickerSseEvent(
    val slideNo: Int,
    val totalSlides: Int,
    val displaysOnline: Int,
    val animCount : Int
)