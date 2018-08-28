package com.study.vipoliveira.githubinterface.repositories

import com.study.vipoliveira.githubinterface.model.GitItemsResponse
import com.study.vipoliveira.githubinterface.model.PullRequest
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubRepository {
    @GET("search/repositories")
    fun getGitHubList(@Query("q") language: String, @Query("sort") sort: String, @Query("page") page: Int): Single<GitItemsResponse>

    @GET ("repos/{creator}/{project}/pulls")
    fun getPullRequestRepoList(@Path("creator") creator: String, @Path("project") project: String) : Single<MutableList<PullRequest>>
}