package com.almax.dsalgorithms.presentation.problem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almax.dsalgorithms.domain.model.CategoryDto
import com.almax.dsalgorithms.domain.usecase.ProblemUseCase
import com.almax.dsalgorithms.presentation.base.UiState
import com.almax.dsalgorithms.util.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ProblemViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val useCase: ProblemUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<CategoryDto>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CategoryDto>>>
        get() = _uiState

    fun getProblems(category: String) {
        viewModelScope.launch {
            useCase(category)
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