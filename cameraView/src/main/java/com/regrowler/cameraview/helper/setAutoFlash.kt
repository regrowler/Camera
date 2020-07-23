package com.regrowler.cameraview.helper

import android.hardware.camera2.CaptureRequest

fun CameraHelper.setFlash(requestBuilder: CaptureRequest.Builder) {
    prepareFlashType()
    flashType?.let {
        var mode = when (it) {
            FlashType.OFF -> CaptureRequest.FLASH_MODE_OFF
            FlashType.AUTO -> {
                requestBuilder.set(
                    CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH
                )
                CaptureRequest.FLASH_MODE_SINGLE
            }
            FlashType.TORCH -> CaptureRequest.FLASH_MODE_TORCH
        }
        mode.let {
            requestBuilder.set(CaptureRequest.FLASH_MODE, it)
        }
    }
}

fun CameraHelper.prepareFlashType() {
    if (flashType == null) flashType = null
}