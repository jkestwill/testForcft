package com.jkestwill.test.di

import android.content.Context
import com.jkestwill.test.ui.utils.PermissionUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Singleton
    @Provides
    fun providePermissionUtil(context: Context):PermissionUtil = PermissionUtil(context)
}