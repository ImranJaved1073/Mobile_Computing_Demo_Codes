package com.example.crud_web_api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @GET("Home/FetchAll")
    suspend fun getAllStudents(): List<Student>

    @POST("Home/AddStudent")
    suspend fun addStudent(@Body student: Student): Response

    @PUT("Home/UpdateStudent")
    suspend fun updateStudent(@Body student: Student): Response

    @DELETE("Home/DeleteStudent")
    suspend fun deleteStudent(@Query("rollNo") rollNo: String): Response
}

// Define the data class for response
data class Response(val success: Boolean, val message: String)