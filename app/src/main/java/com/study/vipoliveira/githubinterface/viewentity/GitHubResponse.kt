package com.study.vipoliveira.githubinterface.viewentity

import com.study.vipoliveira.githubinterface.model.GitItem


class GitHubResponse private constructor(val status: Status,
                                         val data: MutableList<GitItem>?,
                                         val error: Throwable?){
    companion object {
        fun loading(): GitHubResponse {
            return GitHubResponse(Status.LOADING, null, null)
        }

        fun success(data: MutableList<GitItem>): GitHubResponse {
            return GitHubResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): GitHubResponse {
            return GitHubResponse(Status.ERROR, null, error)
        }
    }
}