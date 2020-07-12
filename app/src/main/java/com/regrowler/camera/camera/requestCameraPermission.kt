package com.regrowler.camera.camera

import android.Manifest
import android.app.Activity
import android.content.ContextWrapper
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.FragmentActivity
import com.regrowler.camera.ui.BaseActivity
import com.regrowler.camera.ui.ConfirmationDialog
import com.regrowler.camera.ui.ConfirmationDialog.Companion.FRAGMENT_DIALOG

public var REQUEST_CAMERA_PERMISSION = 1
fun BaseActivity.requestCameraPermission() {
    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
        ConfirmationDialog().show(supportFragmentManager, FRAGMENT_DIALOG)
    } else {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }
}