package com.timmytruong.template.data.model

import com.squareup.moshi.Json

data class Media(
    @field:Json(name = "url") val url: String,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "width") val width: Int,
    @field:Json(name = "type") val type: Type
) {
    enum class Type {
        IMAGE,
        UNKNOWN
    }
}
