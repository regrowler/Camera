package com.regrowler.cameraview.helper

import android.util.SparseIntArray
import android.view.Surface

val ORIENTATIONS = SparseIntArray().apply {
    append(
        Surface.ROTATION_0,
        90
    )
    append(
        Surface.ROTATION_90,
        0
    )
    append(
        Surface.ROTATION_180,
        270
    )
    append(
        Surface.ROTATION_270,
        180
    )
}

fun CameraHelper.getOrientation(rotation: Int): Int? {
    // Sensor orientation is 90 for most devices, or 270 for some devices (eg. Nexus 5X)
    // We have to take that into account and rotate JPEG properly.
    // For devices with orientation of 90, we simply return our mapping from ORIENTATIONS.
    // For devices with orientation of 270, we need to rotate the JPEG 180 degrees.
    mSensorOrientation?.let {
        return (ORIENTATIONS.get(rotation) + it + 270) % 360
    }
    return null
}