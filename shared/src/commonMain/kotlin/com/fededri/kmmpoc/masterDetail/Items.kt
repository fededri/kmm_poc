package com.fededri.kmmpoc.masterDetail

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

data class Item(
    val id: String,
    val text: String
)

interface ItemsComponent {
    val models: Value<Model>

    fun onItemClicked(id: String)

    data class Model(
        val items: List<Item>
    )

    sealed class Events {
        data class ItemSelected(val id: String) : Events()
    }
}

class ItemsComponentImpl(
    private val output: (ItemsComponent.Events) -> Unit
) : ItemsComponent {

    override val models: Value<ItemsComponent.Model> =
        MutableValue(
            ItemsComponent.Model(
                items = listOf(
                    Item(id = "id1", text = "Item 1"),
                    Item(id = "id2", text = "Item 2"),
                    Item(id = "id3", text = "Item 3")
                )
            )
        )

    override fun onItemClicked(id: String) {
        output(ItemsComponent.Events.ItemSelected(id = id))
    }
}