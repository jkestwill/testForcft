package com.jkestwill.test

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.jkestwill.test.ui.card.PermissionDialog
import com.jkestwill.test.ui.utils.PermissionUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PermissionUtil.DIAL->{
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                }else {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                        val showRationale = shouldShowRequestPermissionRationale(permissions[0])
                        if (!showRationale) {
                            setupCallPermissionDialog().show(
                                supportFragmentManager,
                                PermissionDialog.TAG
                            )
                        }
                    }else{
                        setupCallPermissionDialog().show(
                            supportFragmentManager,
                            PermissionDialog.TAG
                        )
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupCallPermissionDialog(): PermissionDialog {
        return PermissionDialog().also {
            it.setHeader("Allow this app to access your phone")
            it.setDescription("This allows you to call the selected number")
            it.setPositiveButtonText("Open settings")
            it.setOnPositiveListener {
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package",packageName, null)
                    )
                )
                it.dismiss()
            }
        }
    }
}