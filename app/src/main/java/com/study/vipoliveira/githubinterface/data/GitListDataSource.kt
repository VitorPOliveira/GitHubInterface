package com.study.vipoliveira.githubinterface.data

import android.arch.paging.ItemKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton
import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.LiveData
import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.usecases.IGitHubListUseCase
import com.study.vipoliveira.githubinterface.viewentity.GitHubResponse
import io.reactivex.Completable
import io.reactivex.functions.Action


@Singleton
class GitListDataSource(private val gitHubListUseCase: IGitHubListUseCase,
                        private val disposable: CompositeDisposable): ItemKeyedDataSource<Int, GitItem>() {

    private var pageNumber = 1
    private val paginatedNetworkStateLiveData =  MutableLiveData<GitHubResponse>()
    private val initialLoadStateLiveData = MutableLiveData<GitHubResponse>()

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<GitItem>) {
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<GitItem>) {
        initialLoadStateLiveData.postValue(GitHubResponse.loading())


        disposable.add(gitHubListUseCase
                .getGitList(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    setRetry(null)
                    onGitListFetched(items)
                    callback.onResult(items)
                }, { t -> setRetry(Action { loadInitial(params, callback) })
                    initialLoadStateLiveData.postValue(GitHubResponse.error(t))}))    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<GitItem>) {
        paginatedNetworkStateLiveData.postValue(GitHubResponse.loading())
        disposable.add(gitHubListUseCase
                .getGitList(params.key)
                .subscribe({ items ->
                    setRetry(null)
                    onMoreGitListFetched(items)
                    callback.onResult(items)
                }, { t -> setRetry(Action { loadAfter(params, callback) })
                    paginatedNetworkStateLiveData.postValue(GitHubResponse.error(t)) }))
    }

    fun onGitListFetched(items: MutableList<GitItem>){
        initialLoadStateLiveData.postValue(GitHubResponse.success(items))
        pageNumber++
    }
    fun onMoreGitListFetched(items: MutableList<GitItem>){
        paginatedNetworkStateLiveData.postValue(GitHubResponse.success(items))
        pageNumber++
    }

    override fun getKey(item: GitItem): Int {
        return pageNumber
    }


    fun clear() {
        disposable.clear()
        pageNumber = 1
    }

    fun getPaginatedNetworkStateLiveData(): LiveData<GitHubResponse> {
        return paginatedNetworkStateLiveData
    }

    fun getInitialLoadStateLiveData(): LiveData<GitHubResponse> {
        return initialLoadStateLiveData
    }

    private var retryCompletable: Completable? = null

    fun retryPagination() {
        if (retryCompletable != null) {
            disposable.add(retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> }))
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) {
            this.retryCompletable = null
        } else {
            this.retryCompletable = Completable.fromAction(action)
        }
    }
}