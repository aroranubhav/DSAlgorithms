package com.almax.dsalgorithms.domain.usecase

import android.util.Log
import com.almax.dsalgorithms.domain.model.Category
import com.almax.dsalgorithms.domain.model.CategoryDto
import com.almax.dsalgorithms.domain.model.toCategoryDto
import com.almax.dsalgorithms.domain.repository.ProblemRepository
import com.almax.dsalgorithms.util.AppConstants.DIR_TYPE
import com.almax.dsalgorithms.util.AppConstants.FILE_TYPE
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProblemUseCase @Inject constructor(
    private val repository: ProblemRepository
) {

    operator fun invoke(category: String): Flow<List<CategoryDto>> {
        return flow {
            repository.getProblems(category)
                .catch {
                    Log.e(TAG, "invoke: ${it.message}")
                    emit(emptyList())
                }.collect { data ->
                    val categoryDtoList = segregateData(data)
                    emit(categoryDtoList)
                }
        }
    }

    private suspend fun segregateData(categories: List<Category>): MutableList<CategoryDto> {
        val dirsMap = mutableMapOf<String, Category>()
        val categoriesDtoList = mutableListOf<CategoryDto>()
        val files = mutableListOf<Category>()

        categories.forEach { category ->
            if (category.type == DIR_TYPE) {
                dirsMap[category.name] = category
                Log.d(TAG, "segregateData: ${category.name}")
            } else if (category.type == FILE_TYPE) {
                files.add(category)
            }
        }
        files.forEach { file ->
            coroutineScope {
                val properties = async {
                    Log.d(TAG, "segregateData file path: ${file.path}")
                    repository.getProblemProperties(file.path)
                        .catch { e ->
                            Log.e(TAG, "segregateData: ${e.message}")
                        }
                        .first()
                }
                val problemProperties = properties.await()
                val fileName = getLcProblemName(file.name)
                Log.d(TAG, "segregateData file name: $fileName")
                if (dirsMap.containsKey(fileName)) {
                    val currCategory = dirsMap[fileName]
                    val currCategoryDto: CategoryDto = currCategory?.toCategoryDto(
                        problemProperties.problemName, problemProperties.problemLcLink,
                        problemProperties.problemLcNumber, problemProperties.problemLcLevel,
                        problemProperties.askedInCompanies
                    ) ?: CategoryDto()
                    categoriesDtoList.add(currCategoryDto)
                } else {
                    Log.d(TAG, "segregateData: ")
                }
            }
        }
        return categoriesDtoList
    }

    private fun getLcProblemName(input: String): String {
        val tempName = input.replace('_', ' ').takeWhile {
            it != '.'
        }
        return tempName.split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
    }
}

const val TAG = "ProblemUseCaseTAG"