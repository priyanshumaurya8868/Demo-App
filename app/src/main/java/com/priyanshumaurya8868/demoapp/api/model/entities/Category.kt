package com.priyanshumaurya8868.demoapp.api.model.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "category")
    val category: String,
    @Json(name = "categoryid")
    val categoryid: Int
)