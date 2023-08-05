package com.example.diffutilrecyclerview.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diffutilrecyclerview.databinding.ActivityMainBinding
import com.example.diffutilrecyclerview.model.ToDo
import com.example.diffutilrecyclerview.ui.viewmodel.ToDoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ToDoViewModel
    private lateinit var todoAdapter: ToDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ToDoViewModel::class.java]

        setContentView(binding.root)

        initLayout()
        observeViewModelLiveData()
    }

    private fun initLayout() {
        binding.editTextTodo.apply {
            viewModel.inputText.value?.let {
                if (it.isNotEmpty()) {
                    setText(it)
                }
            }
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Do nothing
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Do nothing
                }

                override fun afterTextChanged(p0: Editable?) {
                    viewModel.updateCurrentInput(p0.toString())
                }

            })
        }
        binding.btnAdd.setOnClickListener {
            viewModel.addToDo(ToDo(content = binding.editTextTodo.text.toString()))
        }

        todoAdapter = ToDoListAdapter()
        todoAdapter.setOnClickDeleteListener {
            viewModel.deleteToDo(it)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    private fun observeViewModelLiveData() {
        viewModel.toDoList.observe(this) { toDoList ->
            todoAdapter.differ.submitList(toDoList)
        }
    }
}