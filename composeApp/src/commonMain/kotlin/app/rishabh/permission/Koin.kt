package app.rishabh.permission

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


fun initKoin(
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(permissionModule)
}

val permissionModule = module {
    single { PermissionBridge() }
}