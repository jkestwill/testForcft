package com.jkestwill.test.repository

import androidx.lifecycle.LiveData
import com.jkestwill.test.db.model.CardLocalOutput
import com.jkestwill.test.model.Card

interface LocalCardRepository {
    suspend fun getAll(): LiveData<List<CardLocalOutput>>

    suspend fun insert(cardLocal: Card,number:String)

    suspend fun getById(id:Long):LiveData<CardLocalOutput>
}