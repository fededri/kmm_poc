package com.fededri.kmmpoc.todoList

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class TodoListComponent(
    private val componentContext: ComponentContext,
    private val onItemSelected: (String) -> Unit
) : ComponentContext by componentContext {

    private val handler = instanceKeeper.getOrCreate(::ComponentHandler)
    val model: Value<Model> = handler.model

    fun onItemClicked(item: String) {

        onItemSelected(item)
    }

    fun onTextChanged(text: String) {
        changeState { copy(text = text) }
    }

    fun onAddClicked() {
        changeState { copy(items = items + text, text = "") }
    }

    private inline fun changeState(reducer: Model.() -> Model) {
        handler.changeState(reducer)
    }

    data class Model(
        val items: List<String> = emptyList(),
        val text: String = "",
    )

    private class ComponentHandler: InstanceKeeper.Instance {
        private val _model: MutableValue<Model> = MutableValue(Model())
        val model: Value<Model> = _model

        inline fun changeState(reducer: Model.() -> Model) {
            _model.value = _model.value.reducer()
        }

        override fun onDestroy() {

        }
    }
}