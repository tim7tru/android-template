package com.timmytruong.template.ui.adapter

data class ArticleItem(
    val title: String,
    val abstract: String,
    val url: String,
    val imageUrl: String,
    val byLine: String,
    val onClick: (ArticleItem) -> Unit
)
