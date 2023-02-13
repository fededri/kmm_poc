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


extension ContentView {
    class ViewModel: ObservableObject {
        @Published var text = "Loading..."
        init() {
            Greeting().greeting { value, error in
                DispatchQueue.main.async {
                    if let value = value {
                        self.text = value
                    }
                }
            }
        }
    }
}
