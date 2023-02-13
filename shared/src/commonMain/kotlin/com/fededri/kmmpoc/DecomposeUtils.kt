package com.fededri.kmmpoc

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry

/**
 * Workaround to be able to reference the LifecycleRegistry which is a global function from Swift code
 */
object DecomposeUtils {
    fun getLifecycleRegistry(): LifecycleRegistry {
        return LifecycleRegistry()
    }

    fun getDefaultComponentContext(): DefaultComponentContext {
        return DefaultComponentContext(getLifecycleRegistry())
    }
}