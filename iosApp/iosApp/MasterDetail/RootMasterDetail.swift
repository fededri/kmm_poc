//
//  RootMasterDetail.swift
//  iosApp
//
//  Created by federico.torres on 15/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct RootMasterDetail: View {
    
    let orientationProvider = OrientationProvider()
    
    @State
    private var rootComponentHolder = ComponentHolder { context in
        MultiPaneComponentImpl(componentContext: context)
    }
    
    @State
    private var rootSingleComponentHolder = ComponentHolder { context in
        SinglePaneComponentImpl(context: context)
    }
    
    var body: some View {
        let supportsMultiPane = orientationProvider.supportsMasterDetail()
        
        if supportsMultiPane {
            MultiPaneView(component: rootComponentHolder.component)
                .onAppear { rootComponentHolder.lifecycle.onResume() }
                .onDisappear {
                    rootComponentHolder.lifecycle.onPause()
                    rootComponentHolder.lifecycle.onStop()
                }
        } else {
            SinglePaneView(component: rootSingleComponentHolder.component)
        }
        
    }
}
