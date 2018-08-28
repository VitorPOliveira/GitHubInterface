package com.study.vipoliveira.githubinterface.ui.pullrequest

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.study.vipoliveira.githubinterface.model.PullRequest
import kotlinx.android.synthetic.main.layout_pull_resquest_item.view.*

class PullRequestListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: PullRequest, listener: (PullRequest) -> Unit) = with(itemView) {
        with(item){
            user_name.text = user.login
            pull_request_title.text = title
            pull_request_description.text = body
            creation_date.text = createdAt
            Glide.with(itemView).load(user.avatarUrl).apply(RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.DATA)).into(user_image)
            setOnClickListener { listener(item) }
        }
    }
}