package com.study.vipoliveira.githubinterface.viewentity

import com.study.vipoliveira.githubinterface.model.PullRequest


class PullRequestResponse private constructor(val status: Status,
                                              val data: MutableList<PullRequest>?,
                                              val error: Throwable?){
    companion object {
        fun loading(): PullRequestResponse {
            return PullRequestResponse(Status.LOADING, null, null)
        }

        fun success(data: MutableList<PullRequest>): PullRequestResponse {
            return PullRequestResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): PullRequestResponse {
            return PullRequestResponse(Status.ERROR, null, error)
        }
    }
}