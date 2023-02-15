//
//  MasterDetailView.swift
//  iosApp
//
//  Created by federico.torres on 15/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct MultiPaneView: View {
    
    private let component: MultiPaneComponent
    
    @ObservedObject
    private var state: ObservableValue<DecomposeChildStack<AnyObject, MultiPaneComponentChild>>

    
    init(component: MultiPaneComponent) {
        self.component = component
        self.state = ObservableValue.init(component.stack)
    }
    
    var body: some View {
        HStack {
            ListView(itemsComponent: component.itemsComponent) { item in
                component.itemsComponent.onItemClicked(id: item.id)
            }
            .frame(maxWidth: .infinity)
            .frame(width: UIScreen.main.bounds.width * 0.3)
            
            let child = state.value.active.instance
            switch child {
            case let details as MultiPaneComponentChild.Details:
                DetailsView(detailsComponent: details.component)
                    .frame(maxWidth: .infinity)
                    .frame(width: UIScreen.main.bounds.width * 0.7)
            default:
                Text("")
                    .frame(maxWidth: .infinity)
                    .frame(width: UIScreen.main.bounds.width * 0.7)
            }
        }
    }
}

struct SinglePaneView: View {
    let component: SinglePaneComponent
    
    @ObservedObject
    private var childStack: ObservableValue<DecomposeChildStack<AnyObject, SinglePaneComponentChild>>
    
    init(component: SinglePaneComponent) {
        self.component = component
        self.childStack = ObservableValue(component.stack)
    }
    
    var body: some View {
        let child = self.childStack.value.active.instance
        
        switch child {
        case let list as SinglePaneComponentChild.Items:
            ListView(itemsComponent: list.component) { item in
                list.component.onItemClicked(id: item.id)
            }
            
        case let details as SinglePaneComponentChild.Details:
            DetailsView(detailsComponent: details.component)
        default: EmptyView()
        }
        Text("")
    }
}

struct DetailsView: View {
    let detailsComponent: DetailsComponent
    
    @ObservedObject
    private var state: ObservableValue<DetailsComponentModel>
    
    
    init(detailsComponent: DetailsComponent) {
        self.detailsComponent = detailsComponent
        self.state = ObservableValue(detailsComponent.model)
    }
    
    var body: some View {
        VStack {
            Text(state.value.title)
            Button("Go Back") {
                detailsComponent.onCloseClicked()
            }
        }
        
    }
}

struct ListView: View {
    let itemsComponent: ItemsComponent
    let onItemSelected: (Item) -> Void
    
    @ObservedObject
    private var state: ObservableValue<ItemsComponentModel>
    
    init(itemsComponent: ItemsComponent, onItemSelected: @escaping (Item) -> Void) {
        self.itemsComponent = itemsComponent
        self.onItemSelected = onItemSelected
        self.state = ObservableValue(itemsComponent.models)
    }
    
    var body: some View {
        VStack {
            List(state.value.items, id: \.self) { item in
                Button(action: {
                    onItemSelected(item)
                }) {
                    Text(item.text)
                }
            }
        }
    }
}

