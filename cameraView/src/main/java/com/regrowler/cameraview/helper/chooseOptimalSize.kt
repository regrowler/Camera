package com.regrowler.cameraview.helper

import android.util.Log
import android.util.Size
import com.regrowler.cameraview.CameraView
import java.util.*

fun chooseOptimalSize(
    choices: Array<Size>, textureViewWidth: Int,
    textureViewHeight: Int, maxWidth: Int, maxHeight: Int, aspectRatio: Size
): Size? {

    // Collect the supported resolutions that are at least as big as the preview Surface
    val bigEnough: MutableList<Size> =
        ArrayList()
    // Collect the supported resolutions that are smaller than the preview Surface
    val notBigEnough: MutableList<Size> =
        ArrayList()
    val w = aspectRatio.width
    val h = aspectRatio.height
    for (option in choices) {
        if (option.width <= maxWidth && option.height <= maxHeight && option.height == option.width * h / w
        ) {
            if (option.width >= textureViewWidth &&
                option.height >= textureViewHeight
            ) {
                bigEnough.add(option)
            } else {
                notBigEnough.add(option)
            }
        }
    }

    // Pick the smallest of those big enough. If there is no one big enough, pick the
    // largest of those not big enough.
    return when {
        bigEnough.size > 0 -> {
            bigEnough.minBy { it.width * it.height }
        }
        notBigEnough.size > 0 -> {
            notBigEnough.maxBy {
                it.height * it.width
            }

        }
        else -> {
            Log.e(
                CameraView::class.java.simpleName,
                "Couldn't find any suitable preview size"
            )
            choices[0]
        }
    }
}