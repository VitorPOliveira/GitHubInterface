package com.study.vipoliveira.githubinterface.ui.pullrequest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.study.vipoliveira.githubinterface.R
import com.study.vipoliveira.githubinterface.model.PullRequest

class PullRequestListAdapter (val items : MutableList<PullRequest>, val listener: (PullRequest) -> Unit) : RecyclerView.Adapter<PullRequestListViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PullRequestListViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_pull_resquest_item, parent, false)
        return PullRequestListViewHolder(view)
    }
}