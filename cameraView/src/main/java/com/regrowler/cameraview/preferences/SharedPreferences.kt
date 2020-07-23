package com.regrowler.cameraview.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.regrowler.cameraview.R
import com.regrowler.cameraview.helper.CameraType
import com.regrowler.cameraview.helper.FlashType


public fun Context.getEncryptedSharedPreferences(): SharedPreferences {
    var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
    var sharedPreferences = EncryptedSharedPreferences.create(
        resources.getString(R.string.shared_preferences_encrypted_file_name),
        masterKeyAlias,
        this.applicationContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    return sharedPreferences
}

fun SharedPreferences.saveCameraType(int: Int) {
    this.edit()
        .putInt(CameraType.selectedCameraIdTag, int)
        .apply()
}
fun SharedPreferences.saveFlashType(int: Int) {
    this.edit()
        .putInt(FlashType.selectedFlashTypeTag, int)
        .apply()
}