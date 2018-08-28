package com.study.vipoliveira.githubinterface.ui.gitlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.study.vipoliveira.githubinterface.R
import com.study.vipoliveira.githubinterface.model.GitItem
import kotlinx.android.synthetic.main.layout_github_user.view.*

class GitListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: GitItem?, itemSelect: GitItemSelect) {
            itemView.user_name.text = item?.owner?.login
            itemView.project_description.text = item?.description
            itemView.project_name.text = item?.name
            itemView.fork_count.text = item?.forkCount.toString()
            itemView.star_count.text = item?.stargazersCount.toString()
            Glide.with(itemView).load(item?.owner?.avatarUrl).apply(RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.DATA)).into(itemView.user_image)
            itemView.setOnClickListener {
                if (item != null) {
                    itemSelect.onClick(item)
                }
            }
    }

    companion object {
        fun create(parent: ViewGroup): GitListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.layout_github_user, parent, false)
            return GitListViewHolder(view)
        }
    }
}




