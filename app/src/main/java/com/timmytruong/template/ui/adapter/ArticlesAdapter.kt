package com.timmytruong.template.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.timmytruong.template.databinding.ItemArticleBinding

class ArticlesAdapter: RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    companion object {
        private const val THUMBNAIL_WIDTH = 750
        private const val THUMBNAIL_HEIGHT = 200
    }

    private val articles = mutableListOf<ArticleItem>()

    private lateinit var context: Context

    class ArticleViewHolder(val view: ItemArticleBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder.view) {
            val article = articles[position]
            title.text = article.title
            byLine.text = article.byLine
            description.text = article.abstract
            loadImage(article.imageUrl)
            root.setOnClickListener { article.onClick(article) }
        }
    }

    private fun ItemArticleBinding.loadImage(url: String) = Glide
        .with(context)
        .load(url)
        .apply(RequestOptions().override(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT))
        .into(thumbnail)

    override fun getItemCount(): Int = articles.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<ArticleItem>) {
        articles.clear()
        articles.addAll(list)
        notifyDataSetChanged()
    }
}