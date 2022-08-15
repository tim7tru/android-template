package com.timmytruong.template.data

import com.timmytruong.template.data.model.Section

class TopStoriesRepository {

    private val service = NetworkProvider.getRetrofitService().create(TopStoriesService::class.java)

    suspend fun getTopStories(section: Section): Result {
        val response = service.getTopStories(section.apiName)

        return if (response.isSuccessful) {
            Result.Success(data = response.body())
        } else {
            Result.Error(message = response.errorBody()?.toString() ?: "Error")
        }
    }
}