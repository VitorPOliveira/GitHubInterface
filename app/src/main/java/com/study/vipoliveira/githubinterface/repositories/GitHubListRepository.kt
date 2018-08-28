package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.service.GitHubService
import io.reactivex.Single

class GitHubListRepository
constructor(private val gitHubService: GitHubService) : IGitHubListRepository {
    override fun getGitList(page: Int): Single<MutableList<GitItem>> {
        return gitHubService.getGitHubList("language:Java","stars", page).map { it.items }
    }
}