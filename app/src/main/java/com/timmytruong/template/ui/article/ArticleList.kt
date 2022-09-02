package com.timmytruong.template.ui.article

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArticleList(articles: List<ArticleItem>) {
    LazyColumn(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
    ) {
        items(articles) { article ->
            ArticleItem(article)
            Divider(color = Color.White, modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}
