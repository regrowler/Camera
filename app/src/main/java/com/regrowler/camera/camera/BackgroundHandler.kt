package com.regrowler.camera.camera

import android.os.Handler
import android.os.HandlerThread
import android.os.Message

class BackgroundHandler : Handler() {
    private var mBackgroundThread: HandlerThread? = null
    private var mBackgroundHandler: android.os.Handler? = null

    fun startBackgroundThread() {
        mBackgroundThread = HandlerThread("CameraBackground")
        mBackgroundThread?.start()
        mBackgroundHandler = Handler(mBackgroundThread?.looper!!)
    }
    fun stopBackgroundThread() {
        mBackgroundThread?.quitSafely()
        try {
            mBackgroundThread?.join()
            mBackgroundThread = null
            mBackgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
    override fun sendMessageAtTime(msg: Message, uptimeMillis: Long): Boolean {
        return mBackgroundHandler?.sendMessageAtTime(msg, uptimeMillis)!!
    }

    override fun dispatchMessage(msg: Message) {
        mBackgroundHandler?.dispatchMessage(msg)!!
    }

    override fun handleMessage(msg: Message) {
        mBackgroundHandler?.handleMessage(msg)!!
    }

    override fun getMessageName(message: Message): String {
        return mBackgroundHandler?.getMessageName(message)!!
    }

    override fun toString(): String {
        return mBackgroundHandler?.toString()!!
    }



}