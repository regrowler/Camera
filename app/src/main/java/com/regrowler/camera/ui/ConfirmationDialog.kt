package com.regrowler.camera.ui

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.regrowler.camera.R
import com.regrowler.camera.camera.REQUEST_CAMERA_PERMISSION

class ConfirmationDialog : DialogFragment() {
    companion object {
        const val FRAGMENT_DIALOG = "dialog"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val parent: Fragment? = parentFragment
        return AlertDialog.Builder(activity)
            .setMessage(R.string.request_permission)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog, which ->
                parent?.requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { dialog, which ->
                parent?.activity?.finish()
            }
            .create()

    }
}