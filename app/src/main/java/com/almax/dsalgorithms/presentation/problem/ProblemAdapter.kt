package com.almax.dsalgorithms.presentation.problem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.almax.dsalgorithms.databinding.RowQuestionBinding
import com.almax.dsalgorithms.domain.model.CategoryDto
import com.almax.dsalgorithms.util.AppConstants.EASY
import com.almax.dsalgorithms.util.AppConstants.HARD
import com.almax.dsalgorithms.util.AppConstants.MEDIUM

class ProblemAdapter(
    private val questions: ArrayList<CategoryDto>
) : RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder>() {

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
                }
                txtQuestion.apply {
                    text = question.category.name
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder =
        ProblemViewHolder(
            RowQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int =
        questions.size

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        holder.onBind(questions[position])
    }

    fun setData(questions: List<CategoryDto>) {
        this.questions.apply {
            clear()
            addAll(questions)
        }
        notifyDataSetChanged()
    }
}