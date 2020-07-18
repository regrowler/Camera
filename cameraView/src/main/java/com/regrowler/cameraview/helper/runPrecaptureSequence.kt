package com.regrowler.cameraview.helper

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CaptureRequest

fun CameraHelper.runPrecaptureSequence() {
    try {
        // This is how to tell the camera to trigger.
        mPreviewRequestBuilder!!.set(
            CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER,
            CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER_START
        )
        // Tell #mCaptureCallback to wait for the precapture sequence to be set.
        mState = CameraViewState.STATE_WAITING_PRECAPTURE
        mCaptureSession!!.capture(
            mPreviewRequestBuilder!!.build(), mCaptureCallback,
            mBackgroundHandler
        )
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }
}