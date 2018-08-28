package com.study.vipoliveira.githubinterface.di

import android.content.Context
import com.study.vipoliveira.githubinterface.App
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class AppModule {
    @Provides
    fun provideContext(application: App) : Context {
        return application.applicationContext
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable{
        return CompositeDisposable()
    }
}