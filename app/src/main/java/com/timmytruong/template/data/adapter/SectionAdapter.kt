package com.timmytruong.template.data.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.timmytruong.template.data.model.Section

class SectionAdapter {

    @ToJson
    fun toJson(type: Section): String = type.apiName

    @FromJson
    fun fromJson(value: String): Section = Section.values().find { it.apiName == value } ?: Section.ARTS
}