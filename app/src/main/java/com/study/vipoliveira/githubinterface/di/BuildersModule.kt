package com.study.vipoliveira.githubinterface.di

import com.study.vipoliveira.githubinterface.ui.gitlist.GitListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector(modules = [(GitModule::class)])
    abstract fun bindGitListActivity(): GitListActivity
}
