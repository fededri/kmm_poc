package com.fededri.kmmpoc.masterDetail

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

interface DetailsComponent {
    val model: Value<Model>

    fun onCloseClicked()

    data class Model(
        val title: String,
        val text: String
    )

    sealed class Events {
        object Finished: Events()
    }
}


class DetailsComponentImpl(
    private val itemId: String,
    private val eventListener: (DetailsComponent.Events) -> Unit
): DetailsComponent {
    override val model: Value<DetailsComponent.Model>
        get() = MutableValue(
            DetailsComponent.Model(
                title = "Item $itemId",
                text = "Item text"
            )
        )

    override fun onCloseClicked() {
        eventListener(DetailsComponent.Events.Finished)
    }
}