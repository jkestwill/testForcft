package com.jkestwill.test.di

import android.content.Context
import androidx.room.Room
import com.jkestwill.test.db.CardDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCardDao(cardDatabase: CardDatabase) = cardDatabase.cardDao()

    @Provides
    @Singleton
    fun provideBankDao(cardDatabase: CardDatabase) = cardDatabase.bankDao()

    @Provides
    @Singleton
    fun provideNumberDao(cardDatabase: CardDatabase) = cardDatabase.numberDao()

    @Provides
    @Singleton
    fun provideCountryDao(cardDatabase: CardDatabase) = cardDatabase.countryDao()

    @Provides
    @Singleton
    fun provideCardDatabase(context: Context):CardDatabase {
        return Room.databaseBuilder(context, CardDatabase::class.java, "card_db")
            .fallbackToDestructiveMigration().build()
    }
}