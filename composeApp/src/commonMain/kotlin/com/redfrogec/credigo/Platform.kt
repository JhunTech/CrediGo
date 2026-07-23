package com.redfrogec.credigo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun sendToBackground()