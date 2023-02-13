package com.fededri.kmmpoc.todoList

import com.arkivanov.decompose.ComponentContext

class TodoDetailsComponent(
    componentContext: ComponentContext,
    val text: String,
    private val onFinished: () -> Unit
) : ComponentContext by componentContext {

    fun onBackClicked() {
        onFinished()
    }
}