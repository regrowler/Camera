package com.regrowler.cameraview.helper

import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CaptureRequest
import android.view.Surface
import android.widget.Toast
import java.util.*

fun CameraHelper.createCameraPreviewSession() {
    try {
        val texture = mTextureView.surfaceTexture!!

        // We configure the size of default buffer to be the size of camera preview we want.
        texture.setDefaultBufferSize(mPreviewSize!!.width, mPreviewSize!!.height)

        // This is the output Surface we need to start preview.
        val surface = Surface(texture)

        // We set up a CaptureRequest.Builder with the output Surface.
        mPreviewRequestBuilder =
            mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        mPreviewRequestBuilder?.addTarget(surface)

        // Here, we create a CameraCaptureSession for camera preview.
        mCameraDevice!!.createCaptureSession(
            Arrays.asList(surface, mImageReader!!.surface),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(cameraCaptureSession: CameraCaptureSession) {
                    // The camera is already closed
                    if (null == mCameraDevice) {
                        return
                    }

                    // When the session is ready, we start displaying the preview.
                    mCaptureSession = cameraCaptureSession
                    try {
                        // Auto focus should be continuous for camera preview.
                        mPreviewRequestBuilder?.set(
                            CaptureRequest.CONTROL_AF_MODE,
                            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
                        )
                        // Flash is automatically enabled when necessary.
                        setAutoFlash(mPreviewRequestBuilder!!)

                        // Finally, we start displaying the camera preview.
                        mPreviewRequest = mPreviewRequestBuilder?.build()
                        mCaptureSession?.setRepeatingRequest(
                            mPreviewRequest!!,
                            mCaptureCallback, mBackgroundHandler
                        )
                    } catch (e: CameraAccessException) {
                        e.printStackTrace()
                    }
                }

                override fun onConfigureFailed(
                    cameraCaptureSession: CameraCaptureSession
                ) {
                    Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
                }
            }, null
        )
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }
}