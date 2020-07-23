package com.regrowler.cameraview.helper

import android.app.Activity
import android.content.res.Configuration
import android.graphics.ImageFormat
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.media.ImageReader
import android.util.Log
import android.util.Size
import android.view.Surface
import android.widget.Toast
import com.regrowler.cameraview.R
import com.regrowler.cameraview.helper.CameraHelper.Companion.MAX_PREVIEW_HEIGHT
import com.regrowler.cameraview.helper.CameraHelper.Companion.MAX_PREVIEW_WIDTH

fun CameraHelper.setUpCameraOutputs(width: Int, height: Int) {
    try {
        setImageReader()
        setUpPreview(width, height)
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    } catch (e: NullPointerException) {
        // Currently an NPE is thrown when the Camera2API is used but not supported on the
        // device this code runs.
        Toast.makeText(
            context,
            R.string.camera_error,
            Toast.LENGTH_LONG
        ).show()

    }
}

private val CameraHelper.largestSize: Size?
    get() {
        currentCameraId?.let {
            manager
                .getCameraCharacteristics(it)
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)?.let {
                    return it.getOutputSizes(ImageFormat.JPEG).maxBy {
                        (it.height * it.width)
                    }
                }
        }
        return null
    }

private fun CameraHelper.setImageReader() {
    largestSize?.let {
        mImageReader = ImageReader.newInstance(
            it.width,
            it.height,
            ImageFormat.JPEG,
            2
        )
        mImageReader!!.setOnImageAvailableListener(
            mOnImageAvailableListener, mBackgroundHandler
        )
    }
}

private val CameraHelper.swappedDimensions: Boolean
    get() {
        val displayRotation = display.rotation
        when (displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> if (mSensorOrientation == 90 || mSensorOrientation == 270) {
                return true
            }
            Surface.ROTATION_90, Surface.ROTATION_270 -> if (mSensorOrientation == 0 || mSensorOrientation == 180) {
                return true
            }
            else -> Log.e(
                javaClass.simpleName,
                "Display rotation is invalid: $displayRotation"
            )
        }
        return false
    }

private fun CameraHelper.setUpPreview(width: Int, height: Int) {
    // We fit the aspect ratio of TextureView to the size of preview we picked.
    val orientation = context.resources.configuration.orientation
    setPreviewSize(width, height)
    mPreviewSize?.let {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTextureView.setAspectRatio(
                it.width, it.height
            )
        } else {
            mTextureView.setAspectRatio(
                it.height, it.width
            )
        }
    }
}

private fun CameraHelper.setPreviewSize(width: Int, height: Int) {
    currentCameraId?.let { cameraId ->
        val displaySize = Point()
        (context as Activity?)?.windowManager?.defaultDisplay?.getSize(displaySize)
        var rotatedPreviewWidth = width
        var rotatedPreviewHeight = height
        var maxPreviewWidth = displaySize.x
        var maxPreviewHeight = displaySize.y
        if (swappedDimensions) {
            rotatedPreviewWidth = height
            rotatedPreviewHeight = width
            maxPreviewWidth = displaySize.y
            maxPreviewHeight = displaySize.x
        }
        if (maxPreviewWidth > MAX_PREVIEW_WIDTH) {
            maxPreviewWidth =
                MAX_PREVIEW_WIDTH
        }
        if (maxPreviewHeight > MAX_PREVIEW_HEIGHT) {
            maxPreviewHeight =
                MAX_PREVIEW_HEIGHT
        }
        largestSize?.let { largest ->
            manager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)?.let {
                    mPreviewSize = chooseOptimalSize(
                        it.getOutputSizes(
                            SurfaceTexture::class.java
                        ),
                        rotatedPreviewWidth, rotatedPreviewHeight, maxPreviewWidth,
                        maxPreviewHeight, largest
                    )
                    return
                }
        }

    }
    mPreviewSize = null
}

