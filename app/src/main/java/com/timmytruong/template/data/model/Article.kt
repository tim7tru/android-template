package com.timmytruong.template.data.model

import com.squareup.moshi.Json

data class Article(
    @field:Json(name = "section") val section: Section,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "abstract") val abstract: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "uri") val uri: String,
    @field:Json(name = "byLine") val byLine: String,
//    @field:Json(name = "multimedia") val multimedia: List<Media>
)
