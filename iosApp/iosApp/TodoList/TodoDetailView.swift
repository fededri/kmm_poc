//
//  TodoDetailView.swift
//  iosApp
//
//  Created by federico.torres on 13/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TodoDetailsView: View {
    var todo: String
    let goBack: () -> Void
    
    var body: some View {
        NavigationView {
                    VStack {
                        Text(todo)
                        Button("Go Back") {
                            self.goBack()
                        }
                    }
                    .navigationBarTitle("Details")
                }
    }
}
