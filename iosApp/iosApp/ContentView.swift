import SwiftUI
import shared

struct ContentView: View {
    @State
    private var rootComponentHolder = ComponentHolder { context in
        TodoRootComponent(componentContext: context)
    }
    
	var body: some View {
        RootView(rootComponentHolder.component)
            .onAppear { rootComponentHolder.lifecycle.onResume() }
            .onDisappear {
                rootComponentHolder.lifecycle.onPause()
                rootComponentHolder.lifecycle.onStop()
            }
	}
}
