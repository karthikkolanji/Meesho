package com.meesho.jakewarton.data.remote

import com.meesho.jakewarton.data.entity.Session
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("submit-session")
    suspend fun submitSession(@Body session:Session): ResponseBody
}