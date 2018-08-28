package com.study.vipoliveira.githubinterface.ui.pullrequest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.study.vipoliveira.githubinterface.R
import com.study.vipoliveira.githubinterface.model.PullRequest
import com.study.vipoliveira.githubinterface.viewentity.PullRequestResponse
import com.study.vipoliveira.githubinterface.viewentity.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.layout_github_network_state.*
import javax.inject.Inject

class PullRequestListActivity : AppCompatActivity() {

    var viewModelFactory: PullRequestViewModelFactory? = null
    @Inject set

    private var viewModel: PullRequestViewModel? = null
    private val pullRequestList: MutableList<PullRequest> = mutableListOf()
    private val adapter: PullRequestListAdapter by lazy {
        PullRequestListAdapter(pullRequestList){
            openBrowser(it.htmlUrl)
        }
    }

    fun openBrowser(htmlUrl: String) {
        val uris = Uri.parse(htmlUrl)
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }

    private var creator: String? = null
    private var project: String? = null
    private var openIssues: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)
        setSupportActionBar(findViewById(R.id.toolbar_pr))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        creator = intent.getStringExtra(CREATOR_NAME)
        project = intent.getStringExtra(PROJECT_NAME)
        openIssues = intent.getIntExtra(OPEN_ISSUES, 0)
        title = creator
        if(openIssues != 0) toolbar_pr.subtitle = getString(R.string.open_issues_number, openIssues)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PullRequestViewModel::class.java)

        viewModel!!.pullRequestResponse().observe(this,
                Observer { pullRequestResponse -> processPullRequestResponse(pullRequestResponse!!) })

        val linearLayoutManager = LinearLayoutManager(this)
        pull_request_recyclerview.layoutManager = linearLayoutManager
        pull_request_recyclerview.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(pull_request_recyclerview.context,
                linearLayoutManager.orientation)
        pull_request_recyclerview.addItemDecoration(dividerItemDecoration)
        viewModel!!.getPullRequests(creator!!, project!!)
    }

    private fun processPullRequestResponse(pullRequestResponse: PullRequestResponse) {
        error_msg_txt.visibility = if (pullRequestResponse?.error != null) View.VISIBLE else View.GONE
        if (pullRequestResponse?.error?.message != null) {
            error_msg_txt.text = pullRequestResponse.error!!.message
        }

        retry_button.visibility = if (pullRequestResponse?.status == Status.ERROR) View.VISIBLE else View.GONE
        loading_progress_bar.visibility = if (pullRequestResponse?.status == Status.LOADING) View.VISIBLE else View.GONE

        if(pullRequestResponse?.status == Status.SUCCESS){
            if(pullRequestResponse.data!!.isEmpty()){
                error_msg_txt.visibility = View.VISIBLE
                error_msg_txt.text = getString(R.string.no_pull_request)
            } else {
                pullRequestList.clear()
                pullRequestList.addAll(pullRequestResponse.data!!)
                adapter.notifyDataSetChanged()
            }
        }
        retry_button.setOnClickListener { viewModel!!.getPullRequests(creator!!, project!!)}
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    companion object {
        const val CREATOR_NAME = "creatorName"
        const val PROJECT_NAME = "projectName"
        const val OPEN_ISSUES = "openIssues"
    }
}