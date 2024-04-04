package org.clkrw.mobile.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LaserEvent(
    val type: String,
    val x : Float,
    val y : Float
)