package com.jkestwill.test.repository

import com.jkestwill.test.model.Card
import com.jkestwill.test.model.Response

interface CardRepository {

    suspend fun search(number:String):Response<Card>
}