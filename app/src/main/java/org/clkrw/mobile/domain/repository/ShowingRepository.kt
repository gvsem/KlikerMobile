package org.clkrw.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import org.clkrw.mobile.domain.model.Showing

interface ShowingRepository {
    fun getShowing(presentationId: Int): Flow<Showing>
    suspend fun nextSlide(presentationId: Int)
    suspend fun prevSlide(presentationId: Int)
}