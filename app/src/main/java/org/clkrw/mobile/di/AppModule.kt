package org.clkrw.mobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.clkrw.mobile.data.presentations
import org.clkrw.mobile.data.showings
import org.clkrw.mobile.domain.model.Presentation
import org.clkrw.mobile.domain.model.Showing
import org.clkrw.mobile.domain.repository.PresentationRepository
import org.clkrw.mobile.domain.repository.ShowingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(): ShowingRepository {
        val repo = object : ShowingRepository {
            private val data = MutableStateFlow(showings)

            override fun getShowing(presentationId: Int): Flow<Showing> =
                data.map { it.getValue(presentationId) }

            override suspend fun nextSlide(presentationId: Int) {
                data.update {
                    it.toMutableMap().apply {
                        val old = it.getValue(presentationId)
                        put(presentationId, old.copy(slideNumber = old.slideNumber + 1))
                    }
                }
            }

            override suspend fun prevSlide(presentationId: Int) {
                data.update {
                    it.toMutableMap().apply {
                        val old = it.getValue(presentationId)
                        put(presentationId, old.copy(slideNumber = old.slideNumber - 1))
                    }
                }
            }

        }

        return repo
    }

    @Provides
    @Singleton
    fun provideNoteRepository(): PresentationRepository {
        val repo = object : PresentationRepository {
            private val data = MutableStateFlow(presentations)

            override fun getPresentations(): Flow<List<Presentation>> =
                data

            override suspend fun getPresentationById(id: Int): Presentation =
                presentations.first { it.id == id }
        }

        return repo
    }
}
