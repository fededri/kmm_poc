//
//  ObservableValue.swift
//  iosApp
//
//  Created by federico.torres on 12/02/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

public class ObservableValue<T : AnyObject> : ObservableObject {
    private let observableValue: DecomposeValue<T>

    @Published
    var value: T
    
    private var observer: ((T) -> Void)?
    
    init(_ value: DecomposeValue<T>) {
        self.observableValue = value
        self.value = observableValue.value
        self.observer = { [weak self] value in self?.value = value }

        observableValue.subscribe(observer: observer!)
    }
    
    deinit {
        self.observableValue.unsubscribe(observer: self.observer!)
    }
}
