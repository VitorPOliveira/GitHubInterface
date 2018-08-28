package com.study.vipoliveira.githubinterface.di

import com.study.vipoliveira.githubinterface.data.GitListDataSource
import com.study.vipoliveira.githubinterface.data.GitListDataSourceFactory
import com.study.vipoliveira.githubinterface.service.GitHubService
import com.study.vipoliveira.githubinterface.ui.gitlist.GitListViewModelFactory
import com.study.vipoliveira.githubinterface.ui.pullrequest.PullRequestViewModelFactory
import com.study.vipoliveira.githubinterface.repositories.GitHubListRepository
import com.study.vipoliveira.githubinterface.repositories.IGitHubListRepository
import com.study.vipoliveira.githubinterface.repositories.IPullRequestRepoListRepository
import com.study.vipoliveira.githubinterface.repositories.PullRequestRepoListRepository
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
    fun provideGitHubListRepository(gitHubService: GitHubService): IGitHubListRepository {
        return GitHubListRepository(gitHubService)
    }

    @Provides
    fun provideGitListViewModelFactory(dataSourceFactory: GitListDataSourceFactory): GitListViewModelFactory {
        return GitListViewModelFactory(dataSourceFactory)
    }

    @Provides
    fun provideGitListDataSourceFactory(gitListDataSource: GitListDataSource): GitListDataSourceFactory {
        return GitListDataSourceFactory(gitListDataSource)
    }

    @Provides
    fun provideGitListDataSource(gitHubListRepository: IGitHubListRepository,
                                 disposable: CompositeDisposable): GitListDataSource {
        return GitListDataSource(gitHubListRepository, disposable)
    }

    @Provides
    fun providePullRequestRepoListRepository(gitHubService: GitHubService): IPullRequestRepoListRepository {
        return PullRequestRepoListRepository(gitHubService)
    }

    @Provides
    fun providePullRequestViewModelFactory(pullRequestRepoListRepository: IPullRequestRepoListRepository,
                                           disposable: CompositeDisposable): PullRequestViewModelFactory {
        return PullRequestViewModelFactory(pullRequestRepoListRepository, disposable)
    }
}