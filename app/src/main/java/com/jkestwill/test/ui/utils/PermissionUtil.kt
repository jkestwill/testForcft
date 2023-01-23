package com.jkestwill.test.ui.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionUtil(private val context:Context) {
    companion object{
        const val DIAL=1
    }
    fun permissionPhone(onAccept: () -> Unit, onDenied: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onDenied()
        } else {
            onAccept()
        }
    }
}