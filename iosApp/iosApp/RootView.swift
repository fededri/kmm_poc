//
//  RootView.swift
//  iosApp
//
//  Created by federico.torres on 13/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RootView: View {
    @ObservedObject
    private var childStack: ObservableValue<DecomposeChildStack<AnyObject, TodoRootComponent.Child>>
    
    init(_ component: TodoRootComponent) {
        self.childStack = ObservableValue(component.stack)
    }
    
    var body: some View {
        let child = self.childStack.value.active.instance
        
        switch child {
        case let list as TodoRootComponent.ChildList:
            TodoListView(component: list.component)

        case let details as TodoRootComponent.ChildDetails:
            TodoDetailsView(todo: details.component.text, goBack: details.component.onBackClicked)
                .transition(
                    .asymmetric(
                        insertion: AnyTransition.move(edge: .trailing),
                        removal: AnyTransition.move(edge: .trailing)
                    )
                )
                .animation(.easeInOut)
            
        default: EmptyView()
        }
    }
}
