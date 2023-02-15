package com.fededri.kmmpoc.android.masterDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.fededri.kmmpoc.masterDetail.DetailsComponent
import com.fededri.kmmpoc.masterDetail.ItemsComponent
import com.fededri.kmmpoc.masterDetail.MultiPaneComponent

@Composable
fun MultiPane(component: MultiPaneComponent) {
    Row {
        Items(component.itemsComponent,
            modifier = Modifier.fillMaxWidth(0.3f)
        )

        Children(component.stack, modifier = Modifier.fillMaxWidth(0.7f)) {
            when(val child = it.instance) {
                is MultiPaneComponent.Child.None -> Unit
                is MultiPaneComponent.Child.Details -> DetailsScreen(detailsComponent = child.component)
                else -> {}
            }
        }
    }
}

@Composable
fun Items(component: ItemsComponent, modifier: Modifier = Modifier) {
    val state = component.models.subscribeAsState()
    val itemsList = state.value.items
    LazyColumn(modifier = modifier) {
        items(itemsList) { item ->
            Row(modifier = Modifier.clickable(onClick = {
                component.onItemClicked(item.id)
            })) {
                Text(
                    text = item.text,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .weight(1F)
                        .padding(16.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun DetailsScreen(detailsComponent: DetailsComponent) {
    Text(text = detailsComponent.model.value.title)
}