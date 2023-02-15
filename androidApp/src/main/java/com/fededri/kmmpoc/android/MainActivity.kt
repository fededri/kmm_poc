package com.fededri.kmmpoc.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.fededri.kmmpoc.OrientationProvider
import com.fededri.kmmpoc.android.masterDetail.MultiPane
import com.fededri.kmmpoc.android.masterDetail.SinglePane
import com.fededri.kmmpoc.android.todoList.TodoRootContent
import com.fededri.kmmpoc.masterDetail.MultiPaneComponentImpl
import com.fededri.kmmpoc.masterDetail.SinglePaneComponentImpl
import com.fededri.kmmpoc.todoList.TodoRootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val root = TodoRootComponent(componentContext = defaultComponentContext())
        val orientationProvider = OrientationProvider()


        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if(orientationProvider.supportsMasterDetail()){
                        val multiPaneComponent = remember {
                            MultiPaneComponentImpl(defaultComponentContext())
                        }
                        MultiPane(component = multiPaneComponent)
                    } else {
                        val singlePaneComponent = remember {
                            SinglePaneComponentImpl(defaultComponentContext())
                        }
                        SinglePane(component = singlePaneComponent)
                    }

                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
