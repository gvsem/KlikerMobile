package org.clkrw.mobile.data.api


import org.clkrw.mobile.domain.model.Show
import org.clkrw.mobile.domain.model.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
 */
interface ClickerApi {
    @GET("/api/user/info")
    suspend fun getUserInfo(): User

    @GET("/api/user/grant/{showId}/to/{userEmail}/with/open")
    suspend fun openAccessToShow(
        @Path("showId") showId: String,
        @Path("userEmail") userEmail: String,
    )

    @GET("/api/user/grant/{showId}/to/{userEmail}/with/close")
    suspend fun closeAccessToShow(
        @Path("showId") showId: String,
        @Path("userEmail") userEmail: String,
    )

    @GET("/api/show/get")
    suspend fun getShows(): List<Show>

    @GET("/api/show/get/{showId}")
    suspend fun getShow(@Path("showId") showId: String): Show

    @GET("/clicker/{showId}/next")
    suspend fun nextSlide(@Path("showId") showId: String)

    @GET("/clicker/{showId}/prev")
    suspend fun prevSlide(@Path("showId") showId: String)

    @POST("/clicker/{showId}/laser")
    suspend fun laserEvent(@Path("showId") showId: String)
}
