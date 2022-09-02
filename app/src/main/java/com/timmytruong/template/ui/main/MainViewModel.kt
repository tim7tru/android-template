package com.timmytruong.template.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmytruong.template.util.Event
import com.timmytruong.template.data.model.Result
import com.timmytruong.template.data.TopStoriesRepository
import com.timmytruong.template.data.model.Article
import com.timmytruong.template.data.model.NYTResponse
import com.timmytruong.template.data.model.Section
import com.timmytruong.template.ui.article.ArticleItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val topStoriesRepository: TopStoriesRepository
): ViewModel() {

    private val _url: MutableLiveData<Event<String>> = MutableLiveData()
    val url: LiveData<Event<String>> = _url

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
        imageUrl = multimedia.first().url,
        byLine = byLine ?: "",
        onClick = { _url.postValue(Event(it.url)) }
    )
}