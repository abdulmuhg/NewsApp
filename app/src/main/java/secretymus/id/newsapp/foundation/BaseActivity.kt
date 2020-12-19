package secretymus.id.newsapp.foundation

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private var progressBar: ProgressBar? = null

    private val requiredPermissions: Array<String> by lazy {
        try {
            this.packageManager.getPackageInfo(
                    this.packageName,
                    PackageManager.GET_PERMISSIONS
            ).requestedPermissions ?: arrayOf()
        } catch (e: PackageManager.NameNotFoundException) {
            arrayOf()
        }
    }

    private fun allPermissionsGranted() = requiredPermissions.none { !isPermissionGranted(it) }

    private fun requestRuntimePermissions() {
        val allNeededPermissions = requiredPermissions.filter { !isPermissionGranted(it) }

        if (allNeededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    allNeededPermissions.toTypedArray(),
                    PERMISSION_REQUESTS
            )
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return when (ContextCompat.checkSelfPermission(this, permission)) {
            PackageManager.PERMISSION_GRANTED -> {
                Log.i(TAG, "Permission granted: $permission")
                true
            }
            else -> {
                Log.i(TAG, "Permission NOT granted: $permission")
                false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!allPermissionsGranted()) {
            requestRuntimePermissions()
        }
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    public override fun onStop() {
        super.onStop()
        hideProgressBar()
    }

    companion object {

        private const val TAG = "BaseActivity"

        private const val PERMISSION_REQUESTS = 1

    }
}