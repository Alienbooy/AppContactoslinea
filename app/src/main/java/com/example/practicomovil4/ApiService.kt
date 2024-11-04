package com.example.practicomovil4

import retrofit2.Response
import retrofit2.http.*

 interface ApiService {

    @GET("personas")
    suspend fun getContacts(): Response<List<Contact>>

    @GET("api/search")
    suspend fun searchContacts(@Query("query") query: String): Response<List<Contact>>

    @POST("api/personas")
    suspend fun addContact(@Body contact: Contact): Response<Contact>

    @PUT("personas/{id}")
    suspend fun updateContact(@Path("id") id: Int, @Body contact: Contact): Response<Contact>

    @DELETE("personas/{id}")
    suspend fun deleteContact(@Path("id") id: Int): Response<Unit>

    @POST("phones")
    suspend fun addPhone(@Body phone: Phone): Response<Phone>

    @POST("emails")
    suspend fun addEmail(@Body email: Email): Response<Email>
 }