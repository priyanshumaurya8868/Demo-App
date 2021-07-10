package com.priyanshumaurya8868.demoapp.api.model.entities


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Employee(
    @Json(name = "address")
    val address: String,
    @Json(name = "contactno")
    val contactno: String,
    @Json(name = "department")
    val department: String,
    @Json(name = "designation")
    val designation: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "empid")
    val empid: Int,
    @Json(name = "empname")
    val empname: String,
    @Json(name = "intercomoffice")
    val intercomoffice: String,
    @Json(name = "intercomresidence")
    val intercomresidence: String,
    @Json(name = "photopath")
    val photopath: String
):Serializable