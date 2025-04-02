package com.almax.dsalgorithms.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almax.dsalgorithms.domain.model.Category
import com.almax.dsalgorithms.domain.usecase.CategoryUseCase
import com.almax.dsalgorithms.presentation.base.UiState
import com.almax.dsalgorithms.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val useCase: CategoryUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Category>>>(UiState.Loading)
    val uiState: Flow<UiState<List<Category>>>
        get() = _uiState

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            useCase()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect { data ->
                    _uiState.value = UiState.Success(data)
                }
        }
    }
}