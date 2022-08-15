package com.timmytruong.template.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmytruong.template.data.Result
import com.timmytruong.template.data.TopStoriesRepository
import com.timmytruong.template.data.model.Article
import com.timmytruong.template.data.model.NYTResponse
import com.timmytruong.template.data.model.Section
import com.timmytruong.template.ui.adapter.ArticleItem
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val topStoriesRepository: TopStoriesRepository = TopStoriesRepository()

    private val _stories: MutableLiveData<List<ArticleItem>> = MutableLiveData()
    val stories: LiveData<List<ArticleItem>> = _stories

    fun getStories(section: Section) = viewModelScope.launch {
        when (val result = topStoriesRepository.getTopStories(section)) {
            is Result.Success<*> -> {
                val response = result.data as NYTResponse
                val articles = response.results.map { it.listItem }
                _stories.postValue(articles)
            }
            is Result.Error -> {
                /**
                 * Handle Error Case
                 * **/
            }
        }
    }

    private val Article.listItem: ArticleItem get() = ArticleItem(
        title = title,
        abstract = abstract,
        url = url,
        byLine = byLine ?: ""
    )
}