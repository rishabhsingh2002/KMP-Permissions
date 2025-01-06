package app.rishabh.permission

actual interface PermissionsBridgeListener {
    actual fun requestContactPermission(callback: PermissionResultCallback)
    actual fun isContactPermissionGranted(): Boolean
}