package com.fededri.kmmpoc.android.todoList

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.fededri.kmmpoc.todoList.TodoRootComponent

@Composable
fun TodoRootContent(component: TodoRootComponent) {
    Children(
        stack = component.stack,
        animation = stackAnimation(fade() + slide()),
    ) {
        when (val child = it.instance) {
            is TodoRootComponent.Child.List -> TodoListContent(child.component)
            is TodoRootComponent.Child.Details -> TodoDetailsContent(child.component)
        }
    }
}