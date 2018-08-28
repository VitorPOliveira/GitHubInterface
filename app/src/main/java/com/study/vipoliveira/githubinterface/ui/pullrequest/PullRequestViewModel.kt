package com.study.vipoliveira.githubinterface.ui.pullrequest

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.study.vipoliveira.githubinterface.usecases.IPullRequestRepoListUseCase
import com.study.vipoliveira.githubinterface.viewentity.PullRequestResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PullRequestViewModel(private val pullRequestRepoListUseCase: IPullRequestRepoListUseCase,
                           private val disposable: CompositeDisposable) : ViewModel(){
    private val pullRequestResponse = MutableLiveData<PullRequestResponse>()

    fun pullRequestResponse(): MutableLiveData<PullRequestResponse> {
        return pullRequestResponse
    }
    fun getPullRequests(creator: String, project: String){
        disposable.add(
                pullRequestRepoListUseCase
                        .getPullRequestList(creator, project)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            _ -> pullRequestResponse.value = PullRequestResponse.loading()
                        }
                        .subscribe({
                            items -> pullRequestResponse.value = PullRequestResponse.success(items)
                        },
                                {
                                    t ->  pullRequestResponse.value = PullRequestResponse.error(t)
                                })

        )
    }
}