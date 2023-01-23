package com.jkestwill.test.di

import androidx.lifecycle.ViewModel
import com.jkestwill.test.ui.card.card.CardViewModel
import com.jkestwill.test.ui.card.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CardViewModel::class)
    abstract fun bindsCardViewModel(cardViewModel: CardViewModel):ViewModel
}