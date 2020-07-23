package com.regrowler.cameraview.helper

public fun CameraHelper.nextCamera() {
    when (cameraType) {
        CameraType.FRONT -> {
            if (backCameraId != null || externalCameraId != null) {
                backCameraId?.let {
                    changeToBackCamera()
                    return
                }
                externalCameraId?.let {
                    changeToExternalCamera()
                    return
                }

            }
        }
        CameraType.BACK -> {
            if (externalCameraId != null || frontCameraId != null) {
                externalCameraId?.let {
                    changeToExternalCamera()
                    return
                }
                frontCameraId?.let {
                    changeToFrontCamera()
                    return
                }
            }
        }
        CameraType.EXTERNAL -> {
            if (frontCameraId != null || backCameraId != null) {
                frontCameraId?.let {
                    changeToFrontCamera()
                    return
                }
                backCameraId?.let {
                    changeToBackCamera()
                    return
                }
            }
        }
    }
}

private fun CameraHelper.changeToBackCamera() {
    if (currentHeight != 0 && currentWidth != 0) {
        closeCamera()
        cameraType = CameraType.BACK
        openCamera(currentWidth, currentHeight)
    }
}

private fun CameraHelper.changeToFrontCamera() {
    if (currentHeight != 0 && currentWidth != 0) {
        closeCamera()
        cameraType = CameraType.FRONT
        openCamera(currentWidth, currentHeight)
    }
}

private fun CameraHelper.changeToExternalCamera() {
    if (currentHeight != 0 && currentWidth != 0) {
        closeCamera()
        cameraType = CameraType.EXTERNAL
        openCamera(currentWidth, currentHeight)
    }
}