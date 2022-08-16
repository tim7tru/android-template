package com.timmytruong.template.data

import com.timmytruong.template.data.model.Result
import com.timmytruong.template.data.model.Section
import com.timmytruong.template.data.remote.TopStoriesService
import javax.inject.Inject

class TopStoriesRepository @Inject constructor(
    private val topStoriesService: TopStoriesService
) {

    suspend fun getTopStories(section: Section): Result {
        val response = topStoriesService.getTopStories(section.apiName)

        return if (response.isSuccessful) {
            Result.Success(data = response.body())
        } else {
            Result.Error(message = response.errorBody()?.toString() ?: "Error")
        }
    }
}