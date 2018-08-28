package com.study.vipoliveira.githubinterface.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.study.vipoliveira.githubinterface.model.GitItem

class GitListDataSourceFactory(private val gitListDataSource: GitListDataSource) : DataSource.Factory<Int, GitItem> {
    val gitListDataSourceLiveData = MutableLiveData<GitListDataSource>()


    override fun create(): DataSource<Int, GitItem> {
        gitListDataSourceLiveData.postValue(gitListDataSource);
        return gitListDataSource
    }

    fun getGitListDataSource(): GitListDataSource {
        return gitListDataSource
    }
}