package com.fededri.kmmpoc.todoList

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class TodoRootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val _stack =
        childStack(
            source = navigation,
            initialConfiguration = Config.List(),
            handleBackButton = true,
            childFactory = ::createChild
        )

    val stack: Value<ChildStack<*, Child>> = _stack

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.List -> Child.List(todoList(componentContext))
            is Config.Details -> Child.Details(todoDetails(componentContext, config))
        }

    private fun todoList(componentContext: ComponentContext): TodoListComponent =
        TodoListComponent(
            componentContext = componentContext,
            onItemSelected = ::onItemSelected,
        )

    private fun todoDetails(componentContext: ComponentContext, config: Config.Details): TodoDetailsComponent =
        TodoDetailsComponent(
            componentContext = componentContext,
            text = config.text,
            onFinished = navigation::pop,
        )

    private fun onItemSelected(text: String) {
        navigation.push(Config.Details(text = text))
    }

    sealed class Child {
        class List(val component: TodoListComponent) : Child()
        class Details(val component: TodoDetailsComponent) : Child()
    }

    private sealed class Config : Parcelable {
        @Parcelize
        data class List(val name: String = "todo") : Config()

        @Parcelize
        data class Details(val text: String) : Config()
    }
}