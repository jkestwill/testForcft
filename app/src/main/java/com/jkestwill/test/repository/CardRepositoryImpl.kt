package com.jkestwill.test.repository

import com.jkestwill.test.model.Card
import com.jkestwill.test.model.Response
import com.jkestwill.test.model.apiRequest
import com.jkestwill.test.service.CardService
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor (private var cardService: CardService):CardRepository {
    override suspend fun search(number: String): Response<Card> {
        return apiRequest {
            cardService.search(number)
        }
    }

}