package com.regrowler.cameraview.helper

import android.hardware.camera2.CameraCharacteristics

val CameraHelper.mSensorOrientation: Int?
    get() {
        currentCameraId?.let {
            var t=manager
                .getCameraCharacteristics(it)
                .get(CameraCharacteristics.SENSOR_ORIENTATION)
            return t
        }
        return null
    }