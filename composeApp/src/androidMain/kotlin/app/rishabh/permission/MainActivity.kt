package app.rishabh.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import org.koin.core.context.GlobalContext


class MainActivity : ComponentActivity(), PermissionsBridgeListener {

    private var contactPermissionResultCallback: PermissionResultCallback? = null

    private val requestContactPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                contactPermissionResultCallback?.onPermissionGranted()
            } else {
                val permanentlyDenied =
                    !shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)
                contactPermissionResultCallback?.onPermissionDenied(permanentlyDenied)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalContext.get().get<PermissionBridge>().setListener(this)

        setContent {
            App()
        }
    }

    override fun requestContactPermission(callback: PermissionResultCallback) {
        val permission = Manifest.permission.READ_CONTACTS
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                callback.onPermissionGranted()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                callback.onPermissionDenied(false)
            }

            else -> {
                contactPermissionResultCallback = callback
                requestContactPermissionLauncher.launch(permission)
            }
        }
    }

    override fun isContactPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }
}
