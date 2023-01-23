package com.jkestwill.test.di

import com.google.gson.Gson
import com.jkestwill.test.BuildConfig
import com.jkestwill.test.service.CardService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideCardService(retrofit: Retrofit): CardService {
        return retrofit.create(CardService::class.java)
    }
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson,httpLoggingInterceptor: HttpLoggingInterceptor):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).callTimeout(1000,TimeUnit.MILLISECONDS).build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    }
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
      return HttpLoggingInterceptor().apply {
           this.level=HttpLoggingInterceptor.Level.BODY
       }
    }
    @Provides
    @Singleton
    fun provideGson():Gson = Gson()
}