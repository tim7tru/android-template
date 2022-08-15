package com.timmytruong.template.data.model

import com.squareup.moshi.Json

data class NYTResponse(
    @field:Json(name="results") val results: List<Article>
)
