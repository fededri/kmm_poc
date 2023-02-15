package com.fededri.kmmpoc.android.masterDetail

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.fededri.kmmpoc.masterDetail.SinglePaneComponent

@Composable
fun SinglePane(component: SinglePaneComponent) {
    Children(component.stack) {
        when (val child = it.instance) {
            is SinglePaneComponent.Child.Items -> Items(child.component)
            is SinglePaneComponent.Child.Details -> DetailsScreen(child.component)
            else -> Unit
        }.let {}
    }
}