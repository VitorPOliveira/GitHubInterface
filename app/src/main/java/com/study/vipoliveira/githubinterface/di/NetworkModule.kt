package com.study.vipoliveira.githubinterface.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.study.vipoliveira.githubinterface.App
import com.study.vipoliveira.githubinterface.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(app: App): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        val cacheSize = 10 * 1024 * 1024 // 10 MB  
        val cache = Cache(app.cacheDir, cacheSize.toLong())
        return httpClient.cache(cache).build()
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
    }
}
