package com.regrowler.camera.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.regrowler.camera.R
import com.regrowler.cameraview.helper.FlashType
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            setFlashModeIcon(cameraView?.getFlashMode())
        }, 500)
        takePictureButton?.setOnClickListener {
            cameraView?.takePhoto()
        }
        changeCameraButton?.setOnClickListener {
            cameraView?.nextCamera()
        }
        changeFlashButton?.setOnClickListener {
            setFlashModeIcon(cameraView?.nextFlashType())
        }
    }

    fun setFlashModeIcon(type: FlashType?) {
        type?.let {
            when (it) {
                FlashType.OFF -> {
                    changeFlashButton.setImageResource(R.drawable.ic_baseline_flash_off_24)
                }
                FlashType.TORCH -> {
                    changeFlashButton.setImageResource(R.drawable.ic_baseline_flash_on_24)
                }
                FlashType.AUTO -> {
                    changeFlashButton.setImageResource(R.drawable.ic_baseline_flash_auto_24)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        cameraView?.turnOffFlash()
    }
}