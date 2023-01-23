package com.jkestwill.test.service

import com.jkestwill.test.model.Card
import retrofit2.http.GET
import retrofit2.http.Path

interface CardService {
    @GET("/{num}")
   suspend fun search(@Path("num") num:String):Card
}