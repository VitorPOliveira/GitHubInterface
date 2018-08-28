package com.study.vipoliveira.githubinterface.ui.gitlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.study.vipoliveira.githubinterface.R
import com.study.vipoliveira.githubinterface.viewentity.GitHubResponse
import com.study.vipoliveira.githubinterface.viewentity.Status
import kotlinx.android.synthetic.main.layout_github_network_state.view.*

class NetworkResponseViewHolder(val view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retry_button.setOnClickListener { retryCallback() }
    }

    fun bind(gitHubResponse: GitHubResponse?){
        itemView.error_msg_txt.visibility = if (gitHubResponse?.error?.message != null) View.VISIBLE else View.GONE
        if (gitHubResponse?.error?.message != null) {
            itemView.error_msg_txt.text = gitHubResponse.error?.message
        }

        itemView.retry_button.visibility = if (gitHubResponse?.status == Status.ERROR) View.VISIBLE else View.GONE
        itemView.loading_progress_bar.visibility = if (gitHubResponse?.status == Status.LOADING) View.VISIBLE else View.GONE
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): NetworkResponseViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.layout_github_network_state, parent, false)
            return NetworkResponseViewHolder(view, retryCallback)
        }
    }
}
