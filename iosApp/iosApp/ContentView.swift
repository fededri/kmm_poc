import SwiftUI
import shared

struct ContentView: View {
    
    @ObservedObject private(set) var viewModel: ViewModel

	var body: some View {
        Text(viewModel.text)
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
