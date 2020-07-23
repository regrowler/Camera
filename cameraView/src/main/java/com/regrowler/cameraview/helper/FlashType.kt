package com.regrowler.cameraview.helper

public sealed class FlashType(val value: Int) {
    object OFF : FlashType(0)
    object TORCH : FlashType(1)
    object AUTO : FlashType(2)
    companion object {
        val selectedFlashTypeTag = "selectedFlashMode"
        fun parse(int: Int): FlashType? {
            return when (int) {
                0 -> OFF
                1 -> TORCH
                2 -> AUTO
                else -> null
            }
        }
    }
}