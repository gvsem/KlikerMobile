package org.clkrw.mobile.domain.model

data class Presentation(
    val id: String,
    val foreignPresentationId: String,
    val title: String,
    val thumbnailUrl: String,
)