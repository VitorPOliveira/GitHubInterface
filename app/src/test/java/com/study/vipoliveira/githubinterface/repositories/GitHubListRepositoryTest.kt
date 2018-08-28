package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.model.GitItemsResponse
import com.study.vipoliveira.githubinterface.model.Owner
import com.study.vipoliveira.githubinterface.service.GitHubService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

class GitHubListRepositoryTest {
    private lateinit var owner: Owner
    private lateinit var gitItem: GitItem
    private lateinit var gitHubService: GitHubService
    private lateinit var gitItemsResponse: GitItemsResponse
    private lateinit var repo: GitHubListRepository

    @Before
    fun setup() {
        owner = Owner("vipoliveira", "html")
        gitItem = GitItem("test", "test", 1, 2, owner, 3)
        gitHubService = mockk(relaxed = true)
        repo = GitHubListRepository(gitHubService)

    }

    @Test
    fun whenServiceReturnsComplete() {
        every { gitHubService.getGitHubList(any(), any(), any()) } returns Single.just(createResponse(mutableListOf(gitItem)))

        val observer = repo.getGitList(anyInt()).test()

        observer.assertComplete()
    }

    @Test
    fun whenServiceReturnsData() {
        every { gitHubService.getGitHubList(any(), any(), any()) } returns Single.just(createResponse(mutableListOf(gitItem)))

        val observer = repo.getGitList(anyInt()).test()

        observer.assertValue(mutableListOf(gitItem))
    }

    private fun createResponse(data: MutableList<GitItem>) = GitItemsResponse(data)
}