package com.regrowler.cameraview.image.saver

import android.media.Image
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageSaver internal constructor(
    private val mImage: Image,
    private val mFile: File
) : Runnable {
    override fun run() {
        val buffer = mImage.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer[bytes]
        var output: FileOutputStream? = null
        try {
            output = FileOutputStream(mFile)
            output.write(bytes)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mImage.close()
            if (null != output) {
                try {
                    output.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

}