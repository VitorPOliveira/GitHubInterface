package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.GitItem
import io.reactivex.Single

interface IGitHubListRepository {
    fun getGitList(page: Int): Single<MutableList<GitItem>>
}