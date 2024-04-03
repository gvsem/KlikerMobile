package org.clkrw.mobile.data.repository

import org.clkrw.mobile.data.api.ClickerApi
import org.clkrw.mobile.domain.auth.AuthService
import org.clkrw.mobile.domain.model.Show
import org.clkrw.mobile.domain.repository.ShowRepository

class ShowRepositoryImpl(
    private val clickerApi: ClickerApi,
    private val authService: AuthService,
) : ShowRepository {
    override suspend fun getShows(): List<Show> =
        clickerApi.getShows(authService.getAuthToken() ?: error("No auth token!"))

    override suspend fun getShow(showId: String): Show =
        clickerApi.getShow(authService.getAuthToken() ?: error("No auth token!"), showId)

    override suspend fun nextSlide(showId: String) {
        clickerApi.nextSlide(authService.getAuthToken() ?: error("No auth token!"), showId)
    }

    override suspend fun prevSlide(showId: String) {
        clickerApi.prevSlide(authService.getAuthToken() ?: error("No auth token!"), showId)
    }
}