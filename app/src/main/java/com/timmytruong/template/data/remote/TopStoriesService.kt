package com.timmytruong.template.data.remote

import com.timmytruong.template.data.model.NYTResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TopStoriesService {

    @GET("svc/topstories/v2/{section}.json")
    suspend fun getTopStories(@Path("section") section: String): Response<NYTResponse>
}