package com.study.vipoliveira.githubinterface.model

import com.google.gson.annotations.SerializedName

data class GitItem(
        val name: String,
        val description: String,

        @SerializedName ("stargazers_count")
        val stargazersCount: Int,

        @SerializedName ("forks_count")
        val forkCount: Int,

        val owner: Owner,

        @SerializedName("open_issues")
        val openIssues: Int
)