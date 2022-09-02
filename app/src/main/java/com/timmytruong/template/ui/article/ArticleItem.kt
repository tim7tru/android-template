package com.timmytruong.template.ui.article

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage

data class ArticleItem(
    val title: String,
    val abstract: String,
    val url: String,
    val imageUrl: String,
    val byLine: String,
    val onClick: (ArticleItem) -> Unit
)

@Composable
fun ArticleItem(item: ArticleItem) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { item.onClick(item) }) {
        Column(modifier = Modifier.weight(3f)) {
            ArticleTitle(item.title)
            if (item.byLine.isNotEmpty()) ArticleByLine(item.byLine)
            ArticleAbstract(item.abstract)
        }
        GlideImage(item.imageUrl, modifier = Modifier.height(75.dp).weight(1f))
    }
}

@Composable
private fun ArticleTitle(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ArticleByLine(byLine: String) {
    Text(
        text = byLine,
        fontSize = 14.sp,
        maxLines = 1
    )
}

@Composable
private fun ArticleAbstract(abstract: String) {
    Text(
        text = abstract,
        fontSize = 14.sp,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
}