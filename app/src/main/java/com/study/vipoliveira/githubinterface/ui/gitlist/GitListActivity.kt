package com.study.vipoliveira.githubinterface.ui.gitlist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.study.vipoliveira.githubinterface.R
import com.study.vipoliveira.githubinterface.model.GitItem
import com.study.vipoliveira.githubinterface.ui.pullrequest.PullRequestListActivity
import com.study.vipoliveira.githubinterface.viewentity.GitHubResponse
import com.study.vipoliveira.githubinterface.viewentity.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_git_list.*
import kotlinx.android.synthetic.main.layout_github_network_state.*
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration



class GitListActivity : AppCompatActivity() , GitItemSelect {
    override fun onClick(item: GitItem) {
        val intent = Intent(this, PullRequestListActivity::class.java)
        intent.putExtra(PullRequestListActivity.CREATOR_NAME, item.owner.login)
        intent.putExtra(PullRequestListActivity.PROJECT_NAME, item.name)
        intent.putExtra(PullRequestListActivity.OPEN_ISSUES, item.openIssues)
        startActivity(intent)
    }

    var viewModelFactory: TestViewModelFactory? = null
        @Inject set

    private var viewModel: GitListViewModel? = null
    private lateinit var gitListAdapter: NewGitListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git_list)
        setSupportActionBar(findViewById(R.id.toolbar))
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GitListViewModel::class.java)
        viewModel!!.onScreenCreated()
        initAdapter()
        viewModel!!.initialLoadState().observe(this, Observer { response -> processGitResponse(response!!)})
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gitListAdapter = NewGitListAdapter(this) {
            viewModel!!.retry()
        }
        val dividerItemDecoration = DividerItemDecoration(git_user_recyclerview.context,
                linearLayoutManager.orientation)
        git_user_recyclerview.addItemDecoration(dividerItemDecoration)
        git_user_recyclerview.layoutManager = linearLayoutManager
        git_user_recyclerview.adapter = gitListAdapter
        viewModel!!.getItems()!!.observe(this, Observer<PagedList<GitItem>> { gitListAdapter.submitList(it) })
        viewModel!!.paginatedLoadState().observe(this, Observer<GitHubResponse> { gitListAdapter.setNetworkState(it) })
    }

    private fun processGitResponse(gitResponse: GitHubResponse) {
        error_msg_txt.visibility = if (gitResponse?.error != null) View.VISIBLE else View.GONE
        if (gitResponse?.error?.message != null) {
            error_msg_txt.text = gitResponse.error!!.message
        }

        retry_button.visibility = if (gitResponse?.status == Status.ERROR) View.VISIBLE else View.GONE
        loading_progress_bar.visibility = if (gitResponse?.status == Status.LOADING) View.VISIBLE else View.GONE

        retry_button.setOnClickListener { viewModel!!.retry() }
    }
}
