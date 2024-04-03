package org.clkrw.mobile.data.repository

import org.clkrw.mobile.data.api.ClickerApi
import org.clkrw.mobile.domain.model.Show
import org.clkrw.mobile.domain.repository.ShowRepository

class ShowRepositoryImpl(
    val clickerApi: ClickerApi,
) : ShowRepository {
    override suspend fun getShows(): List<Show> =
        clickerApi.getShows()

    override suspend fun getShow(showId: String): Show =
        clickerApi.getShow(showId)

    override suspend fun nextSlide(showId: String) {
        clickerApi.nextSlide(showId)
    }

    override suspend fun prevSlide(showId: String) {
        clickerApi.prevSlide(showId)
    }
}