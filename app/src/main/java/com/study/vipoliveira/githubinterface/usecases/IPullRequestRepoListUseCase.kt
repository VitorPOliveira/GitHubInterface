package com.study.vipoliveira.githubinterface.usecases

import com.study.vipoliveira.githubinterface.model.PullRequest
import io.reactivex.Single

interface IPullRequestRepoListUseCase {
    fun getPullRequestList(creator: String, project: String): Single<MutableList<PullRequest>>
}