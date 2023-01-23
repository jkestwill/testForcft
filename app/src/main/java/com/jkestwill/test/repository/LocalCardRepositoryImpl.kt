package com.jkestwill.test.repository

import androidx.lifecycle.LiveData
import com.jkestwill.test.db.dao.BankDao
import com.jkestwill.test.db.dao.CardDao
import com.jkestwill.test.db.dao.CountryDao
import com.jkestwill.test.db.dao.NumberDao
import com.jkestwill.test.db.model.CardLocal
import com.jkestwill.test.db.model.CardLocalOutput
import com.jkestwill.test.model.Card
import com.jkestwill.test.model.mapper.BankMapper
import com.jkestwill.test.model.mapper.CountryMapper
import com.jkestwill.test.model.mapper.NumberMapper
import javax.inject.Inject

class LocalCardRepositoryImpl @Inject constructor(
    private var cardDao: CardDao,
    private var bankDao: BankDao,
    private var countryDao: CountryDao,
    private var numberDao: NumberDao,
) : LocalCardRepository {

    override suspend fun insert(card: Card,number:String) {
        cardDao.insert(
            CardLocal(
                bankId = card.bank?.let {
                    bankDao.insert(BankMapper().transform(it))
                },
                countryId = card.country?.let {
                    countryDao.insert(CountryMapper().transform(it))
                },
                numberId = card.number?.let {
                    numberDao.insert(NumberMapper().transform(it))
                },
                brand = card.brand,
                prepaid = card.prepaid,
                scheme = card.scheme,
                type = card.type,
                cardNumber = number,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun getById(id: Long): LiveData<CardLocalOutput> {
        return cardDao.selectById(id)
    }

    override suspend fun getAll(): LiveData<List<CardLocalOutput>> {
        return cardDao.selectAll()
    }
}