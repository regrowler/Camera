package com.regrowler.camera.preferences

import android.content.ContextWrapper
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.regrowler.camera.R

fun ContextWrapper.getEncryptedSharedPreferences(): SharedPreferences {
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