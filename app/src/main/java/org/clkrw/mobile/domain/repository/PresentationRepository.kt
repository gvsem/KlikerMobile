package org.clkrw.mobile.domain.repository

import kotlinx.coroutines.flow.Flow
import org.clkrw.mobile.domain.model.Presentation

interface PresentationRepository {
    fun getPresentations(): Flow<List<Presentation>>

    suspend fun getPresentationById(id: Int): Presentation
}