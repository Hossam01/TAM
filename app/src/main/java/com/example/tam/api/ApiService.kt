package com.example.tam.api

import com.example.tam.models.login
import com.example.tam.models.response
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login/")
    @Headers("lang:en","apiKey:fd94f23499b954d6cea823567a606a3f")
    suspend fun login(@Body hashMap: HashMap<String,String>):Response<login>



    @POST("getHome/")
    @Headers("lang:en","apiKey:fd94f23499b954d6cea823567a606a3f")
    suspend fun data():Response<response>

}