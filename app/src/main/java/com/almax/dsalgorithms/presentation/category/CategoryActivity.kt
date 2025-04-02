package com.almax.dsalgorithms.presentation.category

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.almax.dsalgorithms.MainApplication
import com.almax.dsalgorithms.databinding.ActivityCategoryBinding
import com.almax.dsalgorithms.di.component.CategoryComponent
import com.almax.dsalgorithms.di.component.DaggerCategoryComponent
import com.almax.dsalgorithms.di.module.CategoryModule
import com.almax.dsalgorithms.presentation.base.UiState
import com.almax.dsalgorithms.util.updateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryActivity : AppCompatActivity() {

    private lateinit var component: CategoryComponent

    private lateinit var binding: ActivityCategoryBinding

    @Inject
    lateinit var viewModel: CategoryViewModel

    @Inject
    lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        binding.rvCategory.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity)
            setHasFixedSize(true)
            adapter = this@CategoryActivity.adapter
            addItemDecoration(
                DividerItemDecoration(
                    this@CategoryActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.svCategory.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
        binding.fabCategory.setOnClickListener {
            //add network check
            viewModel.getCategories()
        }
        observeDataAndUpdateUi()
    }

    private fun observeDataAndUpdateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            Log.d(TAG, "observeDataAndUpdateUi: ")
                            adapter.setData(state.data as ArrayList)
                            binding.pbCategory.updateVisibility(false)
                        }

                        is UiState.Error -> {
                            Snackbar
                                .make(
                                    binding.root,
                                    state.error,
                                    Snackbar.LENGTH_LONG
                                )
                                .show()
                            binding.pbCategory.updateVisibility(false)
                        }

                        is UiState.Loading -> {
                            binding.pbCategory.updateVisibility(true)
                        }
                    }

                }
            }
        }
    }

    private fun injectDependencies() {
        component = DaggerCategoryComponent
            .builder()
            .applicationComponent((application as MainApplication).component)
            .categoryModule(CategoryModule(this@CategoryActivity))
            .build()
        component.inject(this@CategoryActivity)
    }
}

const val TAG = "MainActivityTAG"