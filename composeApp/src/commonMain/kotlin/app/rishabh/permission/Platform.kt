package app.rishabh.permission

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform