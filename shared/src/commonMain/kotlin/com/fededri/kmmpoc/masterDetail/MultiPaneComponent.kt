package com.fededri.kmmpoc.masterDetail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface MasterDetailConfig

interface MultiPaneComponent {
    val itemsComponent: ItemsComponent
    val stack: Value<ChildStack<*, Child>>


    sealed class Child {
        object None: Child()
        data class Details(val component: DetailsComponent): Child()
    }
}


class MultiPaneComponentImpl(
    componentContext: ComponentContext
): MultiPaneComponent, ComponentContext by componentContext {

    override val itemsComponent: ItemsComponent
        get() = ItemsComponentImpl(::onItemClicked)
    override val stack: Value<ChildStack<*, MultiPaneComponent.Child>>
        get() = _stack

    private val navigation = StackNavigation<Config>()

    private val _stack = childStack(
        source = navigation,
        initialConfiguration = Config.None,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): MultiPaneComponent.Child {
        return when(config) {
            is Config.None -> MultiPaneComponent.Child.None
            is Config.Items -> MultiPaneComponent.Child.None
            is Config.Details -> MultiPaneComponent.Child.Details(
                DetailsComponentImpl(
                    config.id,
                    this::onDetailFinished)
            )
        }
    }

    private fun onItemClicked(event: ItemsComponent.Events) {
        when(event) {
            is ItemsComponent.Events.ItemSelected -> {
                navigation.replaceCurrent(Config.Details(event.id))
            }
        }
    }

    private fun onDetailFinished(event: DetailsComponent.Events) {
        when(event) {
            is DetailsComponent.Events.Finished -> navigation.pop()
        }
    }

    sealed class Config: Parcelable, MasterDetailConfig {
        @Parcelize
        object None: Config()

        @Parcelize
        object Items : Config()

        @Parcelize
        data class Details(val id: String): Config()
    }
}