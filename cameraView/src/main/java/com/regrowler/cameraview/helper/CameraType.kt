package com.regrowler.cameraview.helper

sealed class CameraType(val value: Int) {
    object FRONT : CameraType(0)
    object BACK : CameraType(1)
    object EXTERNAL : CameraType(2)
    companion object {
        val selectedCameraIdTag = "selectedCamera"
        fun parse(int: Int): CameraType? {
            return when (int) {
                0 -> FRONT
                1 -> BACK
                2 -> EXTERNAL
                else -> null
            }
        }
    }
}