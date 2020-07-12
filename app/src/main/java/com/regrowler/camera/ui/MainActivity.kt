package com.regrowler.camera.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.regrowler.camera.R
import java.io.File
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private var mFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFile = File(getExternalFilesDir(null), "pic.jpg")
    }

}