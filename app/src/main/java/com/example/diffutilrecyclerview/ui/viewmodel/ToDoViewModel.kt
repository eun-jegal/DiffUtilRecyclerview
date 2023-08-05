package com.example.diffutilrecyclerview.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diffutilrecyclerview.model.ToDo

class ToDoViewModel: ViewModel() {

    private val _toDoList = MutableLiveData<List<ToDo>>(emptyList())
    val toDoList: LiveData<List<ToDo>> = _toDoList

    private val _inputText = MutableLiveData("")
    val inputText: LiveData<String> = _inputText

    fun updateCurrentInput(input: String) {
        _inputText.value = input
    }

    fun addToDo(toDo: ToDo) {
        val list = ArrayList(_toDoList.value)
        list.add(toDo)
        _toDoList.value = list
    }

    fun deleteToDo(toDo: ToDo) {
        val list = ArrayList(_toDoList.value)
        list.remove(toDo)
        _toDoList.value = list
    }
}