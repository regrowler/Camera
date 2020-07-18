package com.regrowler.cameraview.helper

import android.hardware.camera2.CaptureRequest
import android.hardware.camera2.CaptureResult

fun CameraHelper.processCaptureCallback(result: CaptureResult) {
    when (mState) {
        CameraViewState.STATE_PREVIEW -> {
        }
        CameraViewState.STATE_WAITING_LOCK -> {
            val afState = result.get(CaptureResult.CONTROL_AF_STATE)
            if (afState == null) {
                captureStillPicture()
            } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState
            ) {
                // CONTROL_AE_STATE can be null on some devices
                val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
                if (aeState == null ||
                    aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED
                ) {
                    mState =
                        CameraViewState.STATE_PICTURE_TAKEN
                    captureStillPicture()
                } else {
                    runPrecaptureSequence()
                }
            }
        }
        CameraViewState.STATE_WAITING_PRECAPTURE -> {

            // CONTROL_AE_STATE can be null on some devices
            val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
            if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE || aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED
            ) {
                mState =
                    CameraViewState.STATE_WAITING_NON_PRECAPTURE
            }
        }
        CameraViewState.STATE_WAITING_NON_PRECAPTURE -> {

            // CONTROL_AE_STATE can be null on some devices
            val aeState = result.get(CaptureResult.CONTROL_AE_STATE)
            if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                mState =
                    CameraViewState.STATE_PICTURE_TAKEN
                captureStillPicture()
            }
        }
    }
}