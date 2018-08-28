package com.study.vipoliveira.githubinterface.usecases

import com.study.vipoliveira.githubinterface.model.PullRequest
import com.study.vipoliveira.githubinterface.repositories.GitHubRepository
import io.reactivex.Single

class PullRequestRepoListUseCase
constructor(private val gitHubRepository: GitHubRepository): IPullRequestRepoListUseCase {
    override fun getPullRequestList(creator: String, project: String): Single<MutableList<PullRequest>> {
        return gitHubRepository.getPullRequestRepoList(creator,project)
    }
}