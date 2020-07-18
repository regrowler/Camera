package com.regrowler.cameraview

import android.content.Context
import android.graphics.Color
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import android.util.AttributeSet
import android.view.TextureView
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.regrowler.cameraview.helper.CameraHelper
import com.regrowler.cameraview.helper.configureTransform
import com.regrowler.cameraview.ui.DynamicTextureView

public class CameraView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    ConstraintLayout(context, attrs, defStyle),
    LifecycleObserver {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private var cameraHelper: CameraHelper? = null

    private val manager: CameraManager by lazy {
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private val mTextureView: DynamicTextureView by lazy {
        DynamicTextureView(
            context,
            attrs,
            defStyle
        ).apply {
            id = View.generateViewId()
            layoutParams =
                LayoutParams(LayoutParams.MATCH_CONSTRAINT, LayoutParams.WRAP_CONTENT)
        }
    }
    private val mSurfaceTextureListener: TextureView.SurfaceTextureListener = object :
        TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(
            texture: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            cameraHelper?.openCamera(width, height)
        }

        override fun onSurfaceTextureSizeChanged(
            texture: SurfaceTexture,
            width: Int,
            height: Int
        ) {
            cameraHelper?.configureTransform(width, height)
        }

        override fun onSurfaceTextureDestroyed(texture: SurfaceTexture): Boolean {
            return true
        }

        override fun onSurfaceTextureUpdated(texture: SurfaceTexture) {}
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        cameraHelper = CameraHelper(
            manager,
            display,
            context,
            mTextureView
        ).apply {
            mBackgroundHandler.startBackgroundThread()
            setCameraType()
        }
        if (mTextureView.isAvailable) {
            cameraHelper?.openCamera(mTextureView.width, mTextureView.height)
        } else {
            mTextureView.surfaceTextureListener = mSurfaceTextureListener
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cameraHelper?.closeCamera()
        cameraHelper?.mBackgroundHandler?.stopBackgroundThread()
        cameraHelper = null
    }

    init {
        addView(mTextureView)
        var set = ConstraintSet()
        set.clone(this)
        set.connect(mTextureView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        set.connect(
            mTextureView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        set.connect(
            mTextureView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        set.connect(mTextureView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
        set.applyTo(this)
        setBackgroundColor(Color.CYAN)
    }
}