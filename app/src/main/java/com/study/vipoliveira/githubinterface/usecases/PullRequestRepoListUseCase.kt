package com.study.vipoliveira.githubinterface.usecases

import com.study.vipoliveira.githubinterface.model.PullRequest
import com.study.vipoliveira.githubinterface.service.GitHubService
import io.reactivex.Single

class PullRequestRepoListUseCase
constructor(private val gitHubService: GitHubService): IPullRequestRepoListUseCase {
    override fun getPullRequestList(creator: String, project: String): Single<MutableList<PullRequest>> {
        return gitHubService.getPullRequestRepoList(creator,project)
    }
}