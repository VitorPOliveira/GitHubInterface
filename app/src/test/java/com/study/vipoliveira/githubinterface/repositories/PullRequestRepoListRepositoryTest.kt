package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.Owner
import com.study.vipoliveira.githubinterface.model.PullRequest
import com.study.vipoliveira.githubinterface.service.GitHubService
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*

class PullRequestRepoListRepositoryTest {
    private lateinit var item: Owner
    private lateinit var pullRequest: PullRequest
    private lateinit var gitHubService: GitHubService
    private lateinit var repo: PullRequestRepoListRepository

    @Before
    fun setup() {
        item = Owner("vipoliveira", "html")
        pullRequest = PullRequest("test", "test", "date", "html", item)
        gitHubService = mockk(relaxed = true)
        repo = PullRequestRepoListRepository(gitHubService)

    }

    @Test
    fun whenServiceReturnsComplete() {
        every { gitHubService.getPullRequestRepoList(any(), any()) } returns Single.just(createResponse(mutableListOf(pullRequest)))

        val observer = repo.getPullRequestList(anyString(), anyString()).test()

        observer.assertComplete()
    }

    @Test
    fun whenServiceReturnsData() {
        every { gitHubService.getPullRequestRepoList(any(), any()) } returns Single.just(createResponse(mutableListOf(pullRequest)))

        val observer = repo.getPullRequestList(anyString(), anyString()).test()

        observer.assertValue(mutableListOf(pullRequest))
    }

    private fun createResponse(data: MutableList<PullRequest>) = data
}