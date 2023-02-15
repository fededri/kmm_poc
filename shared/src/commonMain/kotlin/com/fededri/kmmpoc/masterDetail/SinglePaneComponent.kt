package com.fededri.kmmpoc.masterDetail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

interface SinglePaneComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Items(val component: ItemsComponent) : Child()
        data class Details(val component: DetailsComponent) : Child()
        object None: Child()
    }
}

class SinglePaneComponentImpl(
    context: ComponentContext
) : SinglePaneComponent, ComponentContext by context {

    private val navigation = StackNavigation<MultiPaneComponentImpl.Config>()

    private val _stack =
        childStack(
            source = navigation,
            initialConfiguration = MultiPaneComponentImpl.Config.Items,
            handleBackButton = true,
            childFactory = ::child
        )

    override val stack: Value<ChildStack<*, SinglePaneComponent.Child>> = _stack

    private fun child(configuration: MultiPaneComponentImpl.Config, context: ComponentContext): SinglePaneComponent.Child =
        when (configuration) {
            is MultiPaneComponentImpl.Config.Items ->
                SinglePaneComponent.Child.Items(
                    ItemsComponentImpl(
                        output = ::onItemsOutput
                    )
                )

            is MultiPaneComponentImpl.Config.Details ->
                SinglePaneComponent.Child.Details(
                    DetailsComponentImpl(
                        itemId = configuration.id,
                        eventListener = ::onItemDetailsOutput
                    )
                )

            is MultiPaneComponentImpl.Config.None -> SinglePaneComponent.Child.None
        }

    private fun onItemsOutput(output: ItemsComponent.Events): Unit =
        when (output) {
            is ItemsComponent.Events.ItemSelected -> navigation.push(MultiPaneComponentImpl.Config.Details(id = output.id))
        }

    private fun onItemDetailsOutput(output: DetailsComponent.Events): Unit =
        when (output) {
            DetailsComponent.Events.Finished -> navigation.pop()
        }
}