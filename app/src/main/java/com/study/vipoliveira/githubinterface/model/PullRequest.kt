package com.study.vipoliveira.githubinterface.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
        val title: String,
        val body: String,

        @SerializedName("created_at")
        val createdAt: String,

        @SerializedName("html_url")
        val htmlUrl: String,

        val user: Owner
        )