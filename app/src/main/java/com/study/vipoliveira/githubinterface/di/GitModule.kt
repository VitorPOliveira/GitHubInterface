package com.study.vipoliveira.githubinterface.di

import com.study.vipoliveira.githubinterface.data.GitListDataSource
import com.study.vipoliveira.githubinterface.data.GitListDataSourceFactory
import com.study.vipoliveira.githubinterface.service.GitHubService
import com.study.vipoliveira.githubinterface.ui.gitlist.TestViewModelFactory
import com.study.vipoliveira.githubinterface.ui.pullrequest.PullRequestViewModelFactory
import com.study.vipoliveira.githubinterface.usecases.GitHubListUseCase
import com.study.vipoliveira.githubinterface.usecases.IGitHubListUseCase
import com.study.vipoliveira.githubinterface.usecases.IPullRequestRepoListUseCase
import com.study.vipoliveira.githubinterface.usecases.PullRequestRepoListUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
class GitModule {
    @Provides
    fun provideGitHubRepository(retrofit: Retrofit): GitHubService {
        return retrofit.create<GitHubService>(GitHubService::class.java)
    }

    @Provides
    fun provideGitHubListUseCase(gitHubService: GitHubService): IGitHubListUseCase {
        return GitHubListUseCase(gitHubService)
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

    @Provides
    fun providePullRequestRepoListUseCase(gitHubService: GitHubService): IPullRequestRepoListUseCase {
        return PullRequestRepoListUseCase(gitHubService)
    }

    @Provides
    fun providePullRequestViewModelFactory(pullRequestRepoListUseCase: IPullRequestRepoListUseCase,
                                           disposable: CompositeDisposable): PullRequestViewModelFactory {
        return PullRequestViewModelFactory(pullRequestRepoListUseCase, disposable)
    }
}