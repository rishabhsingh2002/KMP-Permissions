package app.rishabh.permission

import kotlin.experimental.ExperimentalObjCName


@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "PermissionRequestProtocol")
actual interface PermissionsBridgeListener {
    actual fun requestContactPermission(callback: PermissionResultCallback)
    actual fun isContactPermissionGranted(): Boolean
}

@Suppress("unused")
fun registerPermissionHandler(listener: PermissionsBridgeListener){
    koinInstance.koin.get<PermissionBridge>().setListener(listener)
}