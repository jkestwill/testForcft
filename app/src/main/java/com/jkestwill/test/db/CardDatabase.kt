package com.jkestwill.test.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jkestwill.test.db.dao.BankDao
import com.jkestwill.test.db.dao.CardDao
import com.jkestwill.test.db.dao.CountryDao
import com.jkestwill.test.db.dao.NumberDao
import com.jkestwill.test.db.model.BankLocal
import com.jkestwill.test.db.model.CardLocal
import com.jkestwill.test.db.model.CountryLocal
import com.jkestwill.test.db.model.NumberLocal

@Database(entities = [CardLocal::class, BankLocal::class, NumberLocal::class, CountryLocal::class], version = 5)
abstract class CardDatabase:RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun bankDao(): BankDao

    abstract fun countryDao(): CountryDao

    abstract fun numberDao(): NumberDao
}