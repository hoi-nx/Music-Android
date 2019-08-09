package com.sun.music_66.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun.music_66.R
import com.sun.music_66.constant.RequestCode
import com.sun.music_66.util.AlertDialogUtil.showMessageDialog

object PermissionUtil {
    val storagePermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    fun isGrantResultsGranted(grantResults: IntArray): Boolean {
        if (grantResults.isNotEmpty()) {
            grantResults.forEach {
                if (it != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        } else {
            return false
        }
    }

    fun isPermissionsGranted(context: Context, permissions: Array<String>): Boolean {
        permissions.forEach {
            if (!isPermissionGranted(context, it)) {
                return false
            }
        }
        return true
    }

    fun requestStoragePermission(fragment: Fragment) {
        if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showMessageDialog(fragment.context!!,fragment.context!!
                    .getString(R.string.alert_permissions_rationale),false) {
                fragment.requestPermissions(storagePermissions, RequestCode.PERMISSION_STORAGE)
            }
        } else {
            fragment.requestPermissions(storagePermissions, RequestCode.PERMISSION_STORAGE)
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

}