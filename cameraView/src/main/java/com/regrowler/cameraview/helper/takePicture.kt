package com.regrowler.cameraview.helper

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureRequest

fun CameraHelper.takePicture() {
    lockFocus()
}

private fun CameraHelper.lockFocus() {
    try {
        // This is how to tell the camera to lock focus.
        mPreviewRequestBuilder?.set(
            CaptureRequest.CONTROL_AF_TRIGGER,
            CameraMetadata.CONTROL_AF_TRIGGER_START
        )
        mPreviewRequestBuilder?.set(
            CaptureRequest.CONTROL_AF_MODE,
            CameraMetadata.CONTROL_AF_MODE_AUTO
        )
        // Tell #mCaptureCallback to wait for the lock.
        mState = CameraViewState.STATE_WAITING_LOCK
        mCaptureSession?.capture(
            mPreviewRequestBuilder!!.build(), mCaptureCallback,
            mBackgroundHandler
        )
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }
}