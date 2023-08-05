package com.example.diffutilrecyclerview.model

import java.util.*

data class ToDo(
    val id: String = UUID.randomUUID().toString(),
    val content: String
)
