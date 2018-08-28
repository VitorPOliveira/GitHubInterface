package com.study.vipoliveira.githubinterface.usecases

import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.repositories.GitHubRepository
import io.reactivex.Single

class GitHubListUseCase
constructor(private val gitHubRepository: GitHubRepository) : IGitHubListUseCase {
    override fun getGitList(page: Int): Single<MutableList<GitItem>> {
        return gitHubRepository.getGitHubList("language:Java","stars", page).map { it.items }
    }
}