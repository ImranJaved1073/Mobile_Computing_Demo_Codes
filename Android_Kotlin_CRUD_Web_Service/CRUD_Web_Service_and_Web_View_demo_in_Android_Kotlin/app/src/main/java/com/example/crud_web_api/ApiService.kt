package com.example.crud_web_api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("Home/FetchAll")
    suspend fun getAllStudents(): List<Student>

    @POST("Home/AddStudent")
    suspend fun addStudent(@Body student: Student): Response
}

// Define the data class for response
data class Response(val success: Boolean, val message: String)