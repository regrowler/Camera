package com.regrowler.cameraview.helper

import android.hardware.camera2.CameraCharacteristics

val CameraHelper.mFlashSupported: Boolean?
    get() {
        currentCameraId?.let {
            return manager.getCameraCharacteristics(it)
                .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false

        }
        return null
    }