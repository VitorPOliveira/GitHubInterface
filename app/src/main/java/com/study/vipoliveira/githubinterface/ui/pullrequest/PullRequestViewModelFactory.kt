package com.study.vipoliveira.githubinterface.ui.pullrequest

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.study.vipoliveira.githubinterface.usecases.IPullRequestRepoListUseCase
import io.reactivex.disposables.CompositeDisposable

class PullRequestViewModelFactory (private val pullRequestRepoListUseCase: IPullRequestRepoListUseCase,
                                   private val disposable: CompositeDisposable): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PullRequestViewModel::class.java)){
            return PullRequestViewModel(pullRequestRepoListUseCase, disposable) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}