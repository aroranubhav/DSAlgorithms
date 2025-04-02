package com.almax.dsalgorithms.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.almax.dsalgorithms.databinding.RowItemBinding
import com.almax.dsalgorithms.domain.model.Category

class CategoryAdapter(
    private val items: ArrayList<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(), Filterable {

    private var filteredList = arrayListOf<Category>()

    inner class CategoryViewHolder(
        private val binding: RowItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Category) {
            binding.apply {
                btnItem.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun getItemCount(): Int =
        filteredList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(filteredList[position])
    }

    private val searchFilter: Filter = object : Filter() {
        override fun performFiltering(input: CharSequence?): FilterResults {
            val filteredList = input?.let {
                if (input.isEmpty()) {
                    items
                } else {
                    items.filter { it.name.lowercase().contains(input) }
                }
            } ?: items
            return FilterResults().apply { values = filteredList }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let {
                updateSearchedList(it.values as ArrayList<Category>)
            }
        }
    }

    override fun getFilter(): Filter =
        searchFilter

    fun setData(items: ArrayList<Category>) {
        this.items.apply {
            clear()
            addAll(items)
            filteredList = items
        }
        notifyDataSetChanged()
    }

    private fun updateSearchedList(categories: ArrayList<Category>) {
        filteredList.apply {
            clear()
            addAll(categories)
        }
        notifyDataSetChanged()
    }
}