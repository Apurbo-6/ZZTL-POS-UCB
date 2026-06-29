package com.example.ucbpos.api

import com.example.ucbpos.model.HomeResponse
import com.example.ucbpos.model.MenuItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("home.php")
    fun getHome(): Call<HomeResponse>

    @GET("menu.php")
    fun getMenu(): Call<List<MenuItem>>
}