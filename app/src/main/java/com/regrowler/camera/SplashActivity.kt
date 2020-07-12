package com.regrowler.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.regrowler.camera.camera.REQUEST_CAMERA_PERMISSION
import com.regrowler.camera.camera.requestCameraPermission
import com.regrowler.camera.ui.BaseActivity
import com.regrowler.camera.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (checkSelfPermission(Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestCameraPermission()
            return
        }
        launchMainActivity()

    }

    private fun launchMainActivity() {
        launch {
            delay(1000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 700)
            } else {
                launchMainActivity()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}