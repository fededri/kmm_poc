package com.fededri.kmmpoc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform