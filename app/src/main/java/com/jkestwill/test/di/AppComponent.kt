package com.jkestwill.test.di

import android.content.Context
import com.jkestwill.test.ui.card.card.CardFragment
import com.jkestwill.test.ui.card.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    ViewModelProviderFactoryModule::class,
    ViewModelModule::class,
    RepositoryModule::class,
    DatabaseModule::class,
    UtilModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder{
        @dagger.BindsInstance
        fun context(context: Context):Builder
        fun build():AppComponent
    }

    fun inject(fragment: MainFragment)
    fun inject(fragment: CardFragment)

}