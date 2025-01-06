package app.rishabh.permission


expect interface PermissionsBridgeListener {
    fun requestContactPermission(callback: PermissionResultCallback)
    fun isContactPermissionGranted(): Boolean
}

class PermissionBridge {

    private var listener: PermissionsBridgeListener? = null

    fun setListener(listener: PermissionsBridgeListener) {
        this.listener = listener
    }

    fun requestContactPermission(callback: PermissionResultCallback) {
        listener?.requestContactPermission(callback) ?: error("Callback handler not set")
    }

    fun isContactPermissionGranted(): Boolean {
        return listener?.isContactPermissionGranted() ?: false
    }

}

interface PermissionResultCallback {
    fun onPermissionGranted()
    fun onPermissionDenied(isPermanentDenied: Boolean)
}
