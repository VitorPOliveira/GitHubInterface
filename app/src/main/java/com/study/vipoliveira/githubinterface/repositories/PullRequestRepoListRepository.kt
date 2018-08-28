package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.PullRequest
import com.study.vipoliveira.githubinterface.service.GitHubService
import io.reactivex.Single

class PullRequestRepoListRepository
constructor(private val gitHubService: GitHubService): IPullRequestRepoListRepository {
    override fun getPullRequestList(creator: String, project: String): Single<MutableList<PullRequest>> {
        return gitHubService.getPullRequestRepoList(creator,project)
    }
}