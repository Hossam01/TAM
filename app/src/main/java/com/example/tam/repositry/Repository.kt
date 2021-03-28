package com.example.tam.repositry

import com.example.tam.api.ApiService
import javax.inject.Inject

class Repository@Inject constructor(
    private val apiService: ApiService
){
    suspend fun login(hashMap: HashMap<String,String>) = apiService.login(hashMap)
    suspend fun data() = apiService.data()


}