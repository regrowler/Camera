package com.regrowler.cameraview.helper

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraMetadata
import android.hardware.camera2.CaptureRequest

fun CameraHelper.unlockFocus() {
    try {
        // Reset the auto-focus trigger
        mPreviewRequestBuilder!!.set(
            CaptureRequest.CONTROL_AF_TRIGGER,
            CameraMetadata.CONTROL_AF_TRIGGER_CANCEL
        )
        setFlash(mPreviewRequestBuilder!!)
        mCaptureSession!!.capture(
            mPreviewRequestBuilder!!.build(), mCaptureCallback,
            mBackgroundHandler
        )
        // After this, the camera will go back to the normal state of preview.
        mState = CameraViewState.STATE_PREVIEW
        mCaptureSession!!.setRepeatingRequest(
            mPreviewRequest!!, mCaptureCallback,
            mBackgroundHandler
        )
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }
}