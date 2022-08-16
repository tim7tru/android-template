package com.timmytruong.template.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.timmytruong.template.Event
import com.timmytruong.template.data.model.Section
import com.timmytruong.template.databinding.ActivityMainBinding
import com.timmytruong.template.ui.WebViewActivity
import com.timmytruong.template.ui.adapter.ArticleItem
import com.timmytruong.template.ui.adapter.ArticlesAdapter

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var articleAdapter: ArticlesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        articleAdapter = ArticlesAdapter()
        binding.adapter.adapter = articleAdapter
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.stories.observe(this, ::handleArticles)
        mainViewModel.url.observe(this, ::handleUrl)
        mainViewModel.getStories(section = Section.ARTS)
    }

    override fun onDestroy() {
        articleAdapter = null
        super.onDestroy()
    }

    private fun handleArticles(articles: List<ArticleItem>) {
        articleAdapter?.setItems(articles)
    }

    private fun handleUrl(url: Event<String>) = url.get()?.let {
        val intent = Intent(this, WebViewActivity::class.java).putExtra(URL_KEY, it)
        startActivity(intent)
    }
}