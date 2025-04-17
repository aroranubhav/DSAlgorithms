package com.almax.dsalgorithms.presentation.problem

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.almax.dsalgorithms.databinding.RowQuestionBinding
import com.almax.dsalgorithms.domain.model.CategoryDto
import com.almax.dsalgorithms.util.AppConstants.EASY
import com.almax.dsalgorithms.util.AppConstants.HARD
import com.almax.dsalgorithms.util.AppConstants.KOTLIN_FILE_TYPE
import com.almax.dsalgorithms.util.AppConstants.MEDIUM
import com.almax.dsalgorithms.util.AppConstants.PYTHON_FILE_TYPE
import com.almax.dsalgorithms.util.AppConstants.SOLUTIONS_FILES_BASE_URL
import com.almax.dsalgorithms.util.SolutionClickListener

class ProblemAdapter(
    private val questions: ArrayList<CategoryDto>
) : RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>(), Filterable {

    private var questionsFilteredList = arrayListOf<CategoryDto>()

    lateinit var solutionClickListener: SolutionClickListener<String>

    inner class ProblemViewHolder(
        private val binding: RowQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(question: CategoryDto) {
            binding.apply {
                question.apply {
                    val questionText = "${properties.problemLcNumber}. ${category.name}"
                    txtQuestion.text = questionText
                    txtLink.text = properties.problemLcLink
                    setProblemLevelColor(properties.problemLcLevel)

                    ivKotlin.setOnClickListener {
                        solutionClickListener(
                            getFileUrl(
                                category.path,
                                category.name,
                                KOTLIN_FILE_TYPE
                            )
                        )
                    }

                    ivPython.setOnClickListener {
                        solutionClickListener(
                            getFileUrl(
                                category.path,
                                category.name,
                                PYTHON_FILE_TYPE
                            )
                        )
                    }
                }
            }
        }

        private fun setProblemLevelColor(problemLcLevel: String) {
            val context = binding.root.context
            when (problemLcLevel) {
                HARD -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(binding.ivProblemLevel.background),
                        ContextCompat.getColor(context, android.R.color.holo_red_dark)
                    )
                }

                MEDIUM -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(binding.ivProblemLevel.background),
                        ContextCompat.getColor(context, android.R.color.holo_orange_dark)
                    )
                }

                EASY -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(binding.ivProblemLevel.background),
                        ContextCompat.getColor(context, android.R.color.holo_green_light)
                    )
                }

                else -> {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(binding.ivProblemLevel.background),
                        ContextCompat.getColor(context, android.R.color.darker_gray)
                    )
                }
            }
        }

        private fun getFileUrl(filePath: String, fileName: String, type: String): String {
            val fileUrl = "$SOLUTIONS_FILES_BASE_URL$filePath/$fileName$type"
            return fileUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder =
        ProblemViewHolder(
            RowQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int =
        questionsFilteredList.size

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        holder.onBind(questionsFilteredList[position])
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(input: CharSequence?): FilterResults {
            val filteredList = input?.let {
                if (input.isEmpty()) {
                    questions
                } else {
                    questions.filter { it.properties.problemName.lowercase().contains(input) }
                }
            } ?: questions
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                updateSearchList(results.values as ArrayList<CategoryDto>)
            }
        }
    }

    override fun getFilter(): Filter =
        searchFilter

    fun setData(questions: ArrayList<CategoryDto>) {
        this.questions.apply {
            clear()
            addAll(questions)
            questionsFilteredList = questions
        }
        notifyDataSetChanged()
    }

    private fun updateSearchList(questions: List<CategoryDto>) {
        questionsFilteredList.apply {
            clear()
            addAll(questions)
        }
        notifyDataSetChanged()
    }
}