package com.almax.dsalgorithms.presentation.problem

import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.almax.dsalgorithms.MainApplication
import com.almax.dsalgorithms.databinding.ActivityProblemBinding
import com.almax.dsalgorithms.di.component.DaggerProblemComponent
import com.almax.dsalgorithms.di.component.ProblemComponent
import com.almax.dsalgorithms.di.module.ProblemModule
import com.almax.dsalgorithms.presentation.base.UiState
import com.almax.dsalgorithms.util.AppConstants.ALGORITHM_CATEGORY
import com.almax.dsalgorithms.util.updateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProblemActivity : AppCompatActivity() {

    private lateinit var component: ProblemComponent

    private lateinit var binding: ActivityProblemBinding

    @Inject
    lateinit var viewModel: ProblemViewModel

    @Inject
    lateinit var adapter: ProblemAdapter

    private var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityProblemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        category = intent.getStringExtra(ALGORITHM_CATEGORY) ?: ""
        if (category.isNotEmpty()) {
            viewModel.getProblems(category)
        }
        binding.rvProblem.apply {
            layoutManager = LinearLayoutManager(this@ProblemActivity)
            adapter = this@ProblemActivity.adapter
            addItemDecoration(
                DividerItemDecoration(
                    this@ProblemActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.fabProblem.setOnClickListener {
            viewModel.getProblems(category)
        }
        adapter.solutionClickListener = { url ->
            viewSolution(url)
        }
        binding.svProblem.setOnQueryTextListener(object: OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
        observeDataAndUpdateUi()
    }

    private fun observeDataAndUpdateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            if (state.data.isNotEmpty()) {
                                adapter.setData(state.data as ArrayList)
                            } else {
                                updateViewsVisibility()
                            }
                            binding.pbProblem.updateVisibility(false)
                        }

                        is UiState.Error -> {
                            Snackbar.make(
                                binding.root,
                                state.error,
                                Snackbar.LENGTH_LONG
                            ).show()
                            binding.pbProblem.updateVisibility(false)
                        }

                        is UiState.Loading -> {
                            binding.pbProblem.updateVisibility(true)
                        }
                    }
                }
            }
        }
    }

    private fun viewSolution(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(this@ProblemActivity, Uri.parse(url))
    }

    private fun updateViewsVisibility() {
        binding.apply {
            vInProgress.clMain.visibility = VISIBLE
            svProblem.visibility = GONE
            fabProblem.visibility = GONE
        }
    }

    private fun injectDependencies() {
        component = DaggerProblemComponent
            .builder()
            .applicationComponent((application as MainApplication).component)
            .problemModule(ProblemModule(this@ProblemActivity))
            .build()
        component.inject(this@ProblemActivity)
    }
}