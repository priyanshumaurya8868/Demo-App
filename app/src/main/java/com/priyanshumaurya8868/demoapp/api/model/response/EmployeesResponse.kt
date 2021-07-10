package com.priyanshumaurya8868.demoapp.api.model.response


import com.priyanshumaurya8868.demoapp.api.model.entities.Employee
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeesResponse(
    @Json(name = "category")
    val category: String,
    @Json(name = "categoryid")
    val categoryid: Int,
    @Json(name = "data")
    val employees: List<Employee>? //bhai  iss variable se  kruga...
                                    //baki  ka bhagwan  janne  chale ga ki nhi
                                       //api  anee ke baad hi pata chalega..
)