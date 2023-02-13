package com.fededri.kmmpoc.android.todoList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.fededri.kmmpoc.todoList.TodoListComponent

@Composable
fun TodoListContent(component: TodoListComponent) {
    val state by component.model.subscribeAsState()

    Column {
        TopAppBar(title = { Text(text = "Todo List") })

        LazyColumn(modifier = Modifier.weight(1F)) {
            items(state.items) { item ->
                TodoListItem(
                    text = item,
                    onClick = { component.onItemClicked(item) },
                )
            }
        }

        Input(
            text = state.text,
            onTextChanged = component::onTextChanged,
            onAddClicked = component::onAddClicked,
        )
    }
}