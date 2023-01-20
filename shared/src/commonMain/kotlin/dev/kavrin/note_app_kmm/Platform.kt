package dev.kavrin.note_app_kmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform