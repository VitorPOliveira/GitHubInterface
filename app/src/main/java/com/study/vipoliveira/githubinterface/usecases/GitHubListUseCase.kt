package com.study.vipoliveira.githubinterface.usecases

import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.service.GitHubService
import io.reactivex.Single

class GitHubListUseCase
constructor(private val gitHubService: GitHubService) : IGitHubListUseCase {
    override fun getGitList(page: Int): Single<MutableList<GitItem>> {
        return gitHubService.getGitHubList("language:Java","stars", page).map { it.items }
    }
}