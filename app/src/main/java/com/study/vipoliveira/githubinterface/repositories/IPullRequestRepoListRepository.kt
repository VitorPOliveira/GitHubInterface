package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.PullRequest
import io.reactivex.Single

interface IPullRequestRepoListRepository {
    fun getPullRequestList(creator: String, project: String): Single<MutableList<PullRequest>>
}