package com.regrowler.cameraview.helper

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.camera2.*
import android.media.ImageReader
import android.util.Size
import android.view.Display
import com.regrowler.cameraview.background.handler.BackgroundHandler
import com.regrowler.cameraview.ui.DynamicTextureView
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class CameraHelper(
    val manager: CameraManager,
    val display: Display,
    val context: Context,
    val mTextureView: DynamicTextureView
) {
    companion object {
        const val MAX_PREVIEW_WIDTH = 1920
        const val MAX_PREVIEW_HEIGHT = 1080
    }

    val mBackgroundHandler =
        BackgroundHandler()
    val mCameraOpenCloseLock =
        Semaphore(1)
    var mImageReader: ImageReader? = null
    var cameraType: CameraType? = null
    var mCameraDevice: CameraDevice? = null
    var mPreviewRequestBuilder: CaptureRequest.Builder? = null
    var mCaptureSession: CameraCaptureSession? = null
    var mPreviewRequest: CaptureRequest? = null
    var mState = CameraViewState.STATE_PREVIEW
    var mPreviewSize: Size? = null

    val frontCameraId: String? by lazy {
        manager.cameraIdList.forEach { cameraId ->
            manager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING)?.let {
                if (it == CameraCharacteristics.LENS_FACING_FRONT) return@lazy cameraId
            }
        }
        return@lazy null
    }
    val backCameraId: String? by lazy {
        manager.cameraIdList.forEach { cameraId ->
            manager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING)?.let {
                if (it == CameraCharacteristics.LENS_FACING_BACK) return@lazy cameraId
            }
        }
        return@lazy null
    }
    val externalCameraId: String? by lazy {
        manager.cameraIdList.forEach { cameraId ->
            manager.getCameraCharacteristics(cameraId).get(CameraCharacteristics.LENS_FACING)?.let {
                if (it == CameraCharacteristics.LENS_FACING_EXTERNAL) return@lazy cameraId
            }
        }
        return@lazy null
    }
    val mCaptureCallback: CameraCaptureSession.CaptureCallback =
        object : CameraCaptureSession.CaptureCallback() {
            override fun onCaptureProgressed(
                session: CameraCaptureSession,
                request: CaptureRequest,
                partialResult: CaptureResult
            ) {
                processCaptureCallback(partialResult)
            }

            override fun onCaptureCompleted(
                session: CameraCaptureSession,
                request: CaptureRequest,
                result: TotalCaptureResult
            ) {
                processCaptureCallback(result)
            }
        }
    val mStateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            // This method is called when the camera is opened.  We start camera preview here.
            mCameraOpenCloseLock.release()
            mCameraDevice = camera
            createCameraPreviewSession()
        }

        override fun onDisconnected(camera: CameraDevice) {
            mCameraOpenCloseLock.release()
            camera.close()
            mCameraDevice = null
        }

        override fun onError(camera: CameraDevice, error: Int) {
            mCameraOpenCloseLock.release()
            camera.close()
            mCameraDevice = null

        }

    }

    @SuppressLint("MissingPermission")
    fun openCamera(width: Int, height: Int) {
        setUpCameraOutputs(width, height)
        configureTransform(width, height)
        try {
            if (!mCameraOpenCloseLock.tryAcquire(
                    2500,
                    TimeUnit.MILLISECONDS
                )
            ) {
                throw RuntimeException("Time out waiting to lock camera opening.")
            }
            currentCameraId?.let {
                manager.openCamera(it, mStateCallback, mBackgroundHandler)
            }

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
        }
    }

    fun closeCamera() {
        try {
            mCameraOpenCloseLock.acquire()
            if (null != mCaptureSession) {
                mCaptureSession!!.close()
                mCaptureSession = null
            }
            if (null != mCameraDevice) {
                mCameraDevice!!.close()
                mCameraDevice = null
            }
            if (null != mImageReader) {
                mImageReader!!.close()
                mImageReader = null
            }
        } catch (e: InterruptedException) {
            throw java.lang.RuntimeException("Interrupted while trying to lock camera closing.", e)
        } finally {
            mCameraOpenCloseLock.release()
        }
    }

    fun setCameraType() {

        frontCameraId?.let {
            cameraType = CameraType.FRONT
            return
        }
        backCameraId?.let {
            cameraType = CameraType.BACK
            return
        }
        externalCameraId?.let {
            cameraType = CameraType.EXTERNAL
            return
        }
    }

}