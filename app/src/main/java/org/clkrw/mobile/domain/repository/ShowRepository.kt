package org.clkrw.mobile.domain.repository

import org.clkrw.mobile.domain.model.Show

interface ShowRepository {
    suspend fun getShows(): List<Show>
    suspend fun getShow(showId: String): Show
    suspend fun nextSlide(showId: String)
    suspend fun prevSlide(showId: String)
    suspend fun laser(showId: String, type: Boolean, x: Float, y : Float)
}