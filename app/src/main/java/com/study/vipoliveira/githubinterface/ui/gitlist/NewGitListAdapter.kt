package com.study.vipoliveira.githubinterface.ui.gitlist

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.study.vipoliveira.githubinterface.R
import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.viewentity.GitHubResponse
import com.study.vipoliveira.githubinterface.viewentity.Status

class NewGitListAdapter(private val itemSelect: GitItemSelect, private val retryCallback: () -> Unit) : PagedListAdapter<GitItem, RecyclerView.ViewHolder>(ItemDiffCallback) {
    private var gitHubResponse: GitHubResponse? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType){
            R.layout.layout_github_user -> GitListViewHolder.create(parent)
            R.layout.layout_github_network_state -> NetworkResponseViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("Unknowm viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)){
            R.layout.layout_github_user -> (holder as GitListViewHolder).bind(getItem(position), itemSelect)
            R.layout.layout_github_network_state -> (holder as NetworkResponseViewHolder).bind(gitHubResponse)
        }
    }

    private fun hasExtraRow(): Boolean {
        return gitHubResponse != null && gitHubResponse!!.status != Status.SUCCESS
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.layout_github_network_state
        } else {
            R.layout.layout_github_user
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newGitHubResponse: GitHubResponse?) {
        if (currentList != null) {
            if (currentList!!.size != 0) {
                val previousState = this.gitHubResponse
                val hadExtraRow = hasExtraRow()
                this.gitHubResponse = newGitHubResponse
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newGitHubResponse) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }


        companion object {
        val ItemDiffCallback = object : DiffUtil.ItemCallback<GitItem>() {
            override fun areItemsTheSame(oldItem: GitItem, newItem: GitItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: GitItem, newItem: GitItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}