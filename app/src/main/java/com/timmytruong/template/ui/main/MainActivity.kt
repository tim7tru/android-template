package com.timmytruong.template.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import com.timmytruong.template.R
import com.timmytruong.template.util.Event
import com.timmytruong.template.data.model.Section
import com.timmytruong.template.ui.WebViewActivity
import com.timmytruong.template.ui.article.ArticleList
import com.timmytruong.template.ui.theme.NYTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val URL_KEY = "url-key"
    }

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYTViewerApp(mainViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.url.observe(this, ::handleUrl)
        mainViewModel.getStories(section = Section.ARTS)
    }

    private fun handleUrl(url: Event<String>) = url.get()?.let {
        val intent = Intent(this, WebViewActivity::class.java).putExtra(URL_KEY, it)
        startActivity(intent)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NYTViewerApp(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    NYTTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primarySurface,
                    title = {
                        Text(
                            text = context.getString(R.string.app_name),
                            color = MaterialTheme.colors.primary
                        )
                    }
                )
            },
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    val articles by mainViewModel.stories.observeAsState(initial = emptyList())
                    ArticleList(articles = articles)
                }
            }
        )
    }
}