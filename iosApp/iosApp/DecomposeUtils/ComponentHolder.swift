//
//  ComponentHolder.swift
//  iosApp
//
//  Created by federico.torres on 13/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class ComponentHolder<T> {
    let lifecycle: LifecycleLifecycleRegistry
    let component: T
    
    init(factory: (DecomposeComponentContext) -> T) {
        let lifecycleRegistry = DecomposeUtils.shared.getLifecycleRegistry()
        let defaultComponentContext = DecomposeUtils.shared.getDefaultComponentContext()
        let component = factory(defaultComponentContext)
        self.lifecycle = lifecycleRegistry
        self.component = component
        
        lifecycle.onCreate()
        lifecycle.onStart()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
