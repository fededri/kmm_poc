//
//  ListView.swift
//  iosApp
//
//  Created by federico.torres on 13/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared


struct TodoListView: View {
    private let component: TodoListComponent
    @State private var newTodo = ""
    
    @ObservedObject
    private var state: ObservableValue<TodoListComponent.Model>
    
    init(component: TodoListComponent) {
        self.component = component
        self.state = ObservableValue.init(component.model)
    }
    
    var body: some View {
        NavigationView {
            VStack {
                List {
                    ForEach(state.value.items, id: \.self) { todo in
                        VStack {
                            Text(todo)
                                .frame(maxWidth: .infinity, alignment: .leading)
                                .background(Color.white)
                        }
                        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
                        .onTapGesture { withAnimation { component.onItemClicked(item: todo) } }
                    }
                }
                HStack {
                    TextField("Enter new todo item", text: $newTodo)
                        .onChange(of: newTodo) { newValue in
                            component.onTextChanged(text: newValue)
                        }
                    Button(action: addTodo) {
                        Text("+")
                    }
                }
                .padding()
            }
            .navigationBarTitle("Todo List")
        }
    }
    
    private func addTodo() {
        newTodo = ""
        component.onAddClicked()
    }
}
