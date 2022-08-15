package com.timmytruong.template.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.template.databinding.ItemArticleBinding

class ArticlesAdapter: RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private val articles = mutableListOf<ArticleItem>()

    class ArticleViewHolder(val view: ItemArticleBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        with(holder.view) {
            val article = articles[position]
            title.text = article.title
            byLine.text = byLine.text
            description.text = article.abstract
        }
    }

    override fun getItemCount(): Int = articles.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<ArticleItem>) {
        articles.clear()
        articles.addAll(list)
        notifyDataSetChanged()
    }
}