package com.shoykatsaha.myapplication
import android.content.pm.PackageManager
import android.os.Bundle
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)
        if (!hasRequiredPermissions()) {
            // Request the required permissions
            requestPermissions()
        } else {
            // Permissions are already granted, perform necessary actions
            // For example, initialize Firebase service or perform other operations
        }

    }

    private fun hasRequiredPermissions(): Boolean {
        // Check if the app has INTERNET and notification permissions
        val internetPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
        val notificationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)

        return internetPermission == PackageManager.PERMISSION_GRANTED &&
                notificationPermission == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermissions() {
        // Request INTERNET and notification permissions
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.INTERNET, Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_PERMISSIONS_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Check if the requested permissions are granted
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions are granted, perform necessary actions
                // For example, initialize Firebase service or perform other operations
            } else {
                // Permissions are denied, inform the user or handle accordingly
                // For example, display a message or prompt the user to grant permissions again
            }
        }
    }

}