package com.example.contactssample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
