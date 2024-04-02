package org.clkr.mobile.api;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface API {

    @GET("/api/user/info")
    Call<UserInfo> getUserInfo();

    @GET("/api/user/grant/:showId/to/:userEmail/with/open")
    Call<Void> openAccessToShow(@Path("showId") String showId, @Path("userEmail") String userEmail);

    @GET("/api/user/grant/:showId/to/:userEmail/with/close")
    Call<Void> closeAccessToShow(@Path("showId") String showId, @Path("userEmail") String userEmail);

    @GET("/api/show/get")
    Call<JSONObject[]> getShows();

    @GET("/clicker/:showId/next")
    Call<JSONObject> nextSlide(@Path("showId") String showId);

    @GET("/clicker/:showId/prev")
    Call<JSONObject> prevSlide(@Path("showId") String showId);

    @POST("/clicker/:showId/laser")
    Call<Void> laserEvent(@Path("showId") String showId);

}