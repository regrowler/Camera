package com.regrowler.cameraview.helper

import android.hardware.camera2.CaptureRequest

fun CameraHelper.setAutoFlash(requestBuilder: CaptureRequest.Builder) {
    if (mFlashSupported!!) {
        requestBuilder.set(
            CaptureRequest.CONTROL_AE_MODE,
            CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
        )
    }
}