package com.regrowler.cameraview.helper

import android.hardware.camera2.*

fun CameraHelper.captureStillPicture() {
    try {
        if (null == mCameraDevice) {
            return
        }
        // This is the CaptureRequest.Builder that we use to take a picture.
        val captureBuilder =
            mCameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)
        captureBuilder.addTarget(mImageReader!!.surface)

        // Use the same AE and AF modes as the preview.
        captureBuilder.set(
            CaptureRequest.CONTROL_AF_MODE,
            CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE
        )
        setAutoFlash(captureBuilder)

        // Orientation
        display.rotation.let {
            getOrientation(it)?.let {
                captureBuilder.set(
                    CaptureRequest.JPEG_ORIENTATION,
                    it
                )
            }
        }
        val CaptureCallback: CameraCaptureSession.CaptureCallback =
            object : CameraCaptureSession.CaptureCallback() {
                override fun onCaptureCompleted(
                    session: CameraCaptureSession,
                    request: CaptureRequest,
                    result: TotalCaptureResult
                ) {
//                    Toast.makeText(context, "Saved: $mFile", Toast.LENGTH_LONG).show()
//                    Log.d(
//                        com.example.android.camera2basic.Camera2BasicFragment.TAG,
//                        mFile.toString()
//                    )
                    unlockFocus()
                }
            }
        mCaptureSession!!.stopRepeating()
        mCaptureSession!!.abortCaptures()
        mCaptureSession!!.capture(captureBuilder.build(), CaptureCallback, null)
    } catch (e: CameraAccessException) {
        e.printStackTrace()
    }
}