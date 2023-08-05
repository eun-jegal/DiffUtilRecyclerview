package com.example.diffutilrecyclerview.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.diffutilrecyclerview.databinding.ToDoItemBinding
import com.example.diffutilrecyclerview.model.ToDo

class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.ToDoItemViewHolder>() {

    private var onClickDeleteListener: ((ToDo) -> Unit)? = null

    fun setOnClickDeleteListener(listener: (ToDo) -> Unit) {
        onClickDeleteListener = listener
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoItemViewHolder {
        val binding = ToDoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoItemViewHolder, position: Int) {
        holder.bindItem(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ToDoItemViewHolder(
        private val binding: ToDoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(todo: ToDo) {
            binding.apply {
                toDoContent.text = todo.content
                btnDelete.setOnClickListener {
                    onClickDeleteListener?.let {
                        it(todo)
                    }
                }
            }
        }
    }
}