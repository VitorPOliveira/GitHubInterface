package com.study.vipoliveira.githubinterface.usecases

import com.study.vipoliveira.githubinterface.model.GitItem
import io.reactivex.Single

interface IGitHubListUseCase {
    fun getGitList(page: Int): Single<MutableList<GitItem>>
}