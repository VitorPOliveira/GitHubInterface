package com.study.vipoliveira.githubinterface.ui.gitlist

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.study.vipoliveira.githubinterface.data.GitListDataSourceFactory

class TestViewModelFactory(private val dataSourceFactory: GitListDataSourceFactory): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GitListViewModel::class.java)){
            return GitListViewModel(dataSourceFactory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}