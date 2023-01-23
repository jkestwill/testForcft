package com.jkestwill.test.di

import com.jkestwill.test.repository.CardRepository
import com.jkestwill.test.repository.CardRepositoryImpl
import com.jkestwill.test.repository.LocalCardRepository
import com.jkestwill.test.repository.LocalCardRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCardRepository(cardRepositoryImpl: CardRepositoryImpl):CardRepository

    @Binds
    @Singleton
    abstract fun bindLocalCardRepository(localCardRepository: LocalCardRepositoryImpl):LocalCardRepository
}