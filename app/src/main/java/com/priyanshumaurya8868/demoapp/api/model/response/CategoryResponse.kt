package com.priyanshumaurya8868.demoapp.api.model.response


import com.priyanshumaurya8868.demoapp.api.model.entities.Category
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    @Json(name = "data")
    val categories: List<Category>
)