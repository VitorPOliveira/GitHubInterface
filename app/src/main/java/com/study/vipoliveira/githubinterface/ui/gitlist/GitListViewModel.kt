package com.study.vipoliveira.githubinterface.ui.gitlist

import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData
import com.study.vipoliveira.githubinterface.data.GitListDataSourceFactory
import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.viewentity.GitHubResponse


class GitListViewModel(private val dataSourceFactory: GitListDataSourceFactory) : ViewModel() {
    private var items: LiveData<PagedList<GitItem>>? = null

    fun onScreenCreated() {
        val config = PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(40)
                .build()
        items = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        dataSourceFactory.getGitListDataSource().clear()
    }

    fun retry() {
        dataSourceFactory.gitListDataSourceLiveData.value!!.retryPagination()
    }

    fun getItems(): LiveData<PagedList<GitItem>>? {
        return items
    }

    fun initialLoadState(): LiveData<GitHubResponse> {
        return dataSourceFactory.getGitListDataSource().getInitialLoadStateLiveData()
    }

    fun paginatedLoadState(): LiveData<GitHubResponse> {
        return dataSourceFactory.getGitListDataSource().getPaginatedNetworkStateLiveData()
    }

}