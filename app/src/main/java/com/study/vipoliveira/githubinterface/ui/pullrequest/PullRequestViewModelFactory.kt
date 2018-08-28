package com.study.vipoliveira.githubinterface.ui.pullrequest

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.study.vipoliveira.githubinterface.repositories.IPullRequestRepoListRepository
import io.reactivex.disposables.CompositeDisposable

class PullRequestViewModelFactory (private val pullRequestRepoListRepository: IPullRequestRepoListRepository,
                                   private val disposable: CompositeDisposable): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullRequestViewModel::class.java)){
            return PullRequestViewModel(pullRequestRepoListRepository, disposable) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}