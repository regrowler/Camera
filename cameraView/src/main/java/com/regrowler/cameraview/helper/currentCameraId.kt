package com.regrowler.cameraview.helper

val CameraHelper.currentCameraId: String?
    get() {
        return when (cameraType) {
            CameraType.FRONT -> {
                frontCameraId
            }
            CameraType.BACK -> {
                backCameraId
            }
            CameraType.EXTERNAL -> {
                externalCameraId
            }
            null -> null
        }
    }