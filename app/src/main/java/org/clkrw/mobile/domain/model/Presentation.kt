package org.clkrw.mobile.domain.model

data class Presentation(
    val id: Int,
    val previewId: Int,
    val title: String,
    val author: String,
    val date: String,
    val link: String,
    val slidesCount: Int,
)