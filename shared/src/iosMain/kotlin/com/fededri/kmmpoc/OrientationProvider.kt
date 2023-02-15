package com.fededri.kmmpoc

import platform.UIKit.UIDevice

actual class OrientationProvider {
    actual fun supportsMasterDetail(): Boolean {
        val deviceType = UIDevice.currentDevice.model
        return deviceType == "iPad"
    }
}