package com.regrowler.camera.camera.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.regrowler.camera.camera.BackgroundHandler

class CameraView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    ConstraintLayout(context, attrs, defStyle),
    LifecycleObserver {
    private val backgroundHandler =
        BackgroundHandler()

    init {
        (context as LifecycleOwner?)?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        backgroundHandler.startBackgroundThread()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        backgroundHandler.stopBackgroundThread()
    }

    private fun openCamera(width: Int, height: Int) {

//        setUpCameraOutputs(width, height)
//        configureTransform(width, height)
        val manager =
            context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
//            if (!mCameraOpenCloseLock.tryAcquire(
//                    2500,
//                    TimeUnit.MILLISECONDS
//                )
//            ) {
//                throw RuntimeException("Time out waiting to lock camera opening.")
//            }
//            manager.openCamera(mCameraId, mStateCallback, mBackgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            throw RuntimeException("Interrupted while trying to lock camera opening.", e)
        }
    }

}