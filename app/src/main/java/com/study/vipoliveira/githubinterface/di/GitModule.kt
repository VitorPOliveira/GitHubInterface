package com.study.vipoliveira.githubinterface.di

import com.study.vipoliveira.githubinterface.data.GitListDataSource
import com.study.vipoliveira.githubinterface.data.GitListDataSourceFactory
import com.study.vipoliveira.githubinterface.repositories.GitHubRepository
import com.study.vipoliveira.githubinterface.ui.gitlist.TestViewModelFactory
import com.study.vipoliveira.githubinterface.usecases.GitHubListUseCase
import com.study.vipoliveira.githubinterface.usecases.IGitHubListUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
class GitModule {
    @Provides
    fun provideGitHubRepository(retrofit: Retrofit): GitHubRepository {
        return retrofit.create<GitHubRepository>(GitHubRepository::class.java)
    }

    @Provides
    fun provideGitHubListUseCase(gitHubRepository: GitHubRepository): IGitHubListUseCase {
        return GitHubListUseCase(gitHubRepository)
    }

    @Provides
    fun provideTestViewModelFactory(dataSourceFactory: GitListDataSourceFactory): TestViewModelFactory {
        return TestViewModelFactory(dataSourceFactory)
    }

    @Provides
    fun provideGitListDataSourceFactory(gitListDataSource: GitListDataSource): GitListDataSourceFactory {
        return GitListDataSourceFactory(gitListDataSource)
    }

    @Provides
    fun provideGitListDataSource(gitHubListUseCase: IGitHubListUseCase,
                                 disposable: CompositeDisposable): GitListDataSource {
        return GitListDataSource(gitHubListUseCase, disposable)
    }
}