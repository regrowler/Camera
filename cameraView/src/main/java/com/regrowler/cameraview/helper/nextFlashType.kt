package com.regrowler.cameraview.helper

fun CameraHelper.nextFlashType(): FlashType? {
    prepareFlashType()
    return when (flashType) {
        FlashType.TORCH -> {
            changeToOFFMode()
            FlashType.OFF
        }
        FlashType.AUTO -> {
            if (mFlashSupported!!) {
                changeToTorchMode()
                FlashType.TORCH
            } else FlashType.OFF

        }
        FlashType.OFF -> {
            if (mFlashSupported!!) {
                changeToAutoMode()
                FlashType.AUTO
            } else FlashType.OFF
        }
        null -> null
    }
}

fun CameraHelper.turnOffFlash() {
    currentCameraId?.let {
        manager.setTorchMode(it, false)
    }
}

fun CameraHelper.isFlashSupported(): Boolean {
    return mFlashSupported ?: false
}

private fun CameraHelper.changeToOFFMode() {
    closeCamera()
    flashType = FlashType.OFF
    openCamera(currentWidth, currentHeight)
}

private fun CameraHelper.changeToAutoMode() {
    closeCamera()
    flashType = FlashType.AUTO
    openCamera(currentWidth, currentHeight)
}

private fun CameraHelper.changeToTorchMode() {
    closeCamera()
    flashType = FlashType.TORCH
    openCamera(currentWidth, currentHeight)
}
