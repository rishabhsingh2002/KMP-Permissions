package app.rishabh.permission

import org.koin.core.KoinApplication

lateinit var koinInstance: KoinApplication
@Suppress("unused", "FunctionNaming")
fun initKoinIos(
): KoinApplication = initKoin().also { koinInstance = it }
