package com.jkestwill.test.di

import androidx.lifecycle.ViewModelProvider
import com.jkestwill.test.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelProviderFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}