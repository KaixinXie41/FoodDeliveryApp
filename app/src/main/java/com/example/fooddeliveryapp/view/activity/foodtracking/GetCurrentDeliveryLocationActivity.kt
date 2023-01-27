package com.example.fooddeliveryapp.view.activity.foodtracking

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityGetCurrentDeliveryLocationBinding
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar

class GetCurrentDeliveryLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityGetCurrentDeliveryLocationBinding
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }
    private var cancellationToken = CancellationTokenSource()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetCurrentDeliveryLocationBinding.inflate(layoutInflater)
        sharedPreferences = this.getSharedPreferences(
           Account_Information, MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        setContentView(binding.root)
        locationRequestOnClick()
        binding.btnUpdate.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private val fineLocationRationaleSnackbar by lazy {
        Snackbar.make(binding.container, R.string.file_location_permission, Snackbar.LENGTH_LONG)
            .setAction(R.string.ok)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_FINE_LOCATION_PERMISSION_REQ_CODE
                    )
                }
            }
    }

    override fun onStop() {
        super.onStop()
        cancellationToken.cancel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_FINE_LOCATION_PERMISSION_REQ_CODE) {
            when {
                grantResults.isEmpty() ->
                    Log.i("tag", "user interaction is cancelled")

                grantResults[0] == PackageManager.PERMISSION_GRANTED ->
                    Snackbar.make(
                        binding.container, R.string.permisson_apporved,
                        Snackbar.LENGTH_LONG
                    ).show()
            }
        } else {
            Snackbar.make(
                binding.container, R.string.permisson_denied,
                Snackbar.LENGTH_LONG
            ).setAction(R.string.settings) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", "com.example.secondprojectbymvvm.view.foodtracking", null)
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }.show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun locationRequestOnClick() {
        val permissionApproved =
            applicationContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionApproved) {
            requestCurrentLocation()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionWithRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    REQUEST_FINE_LOCATION_PERMISSION_REQ_CODE,
                    fineLocationRationaleSnackbar,
                    fineLocationRationaleSnackbar
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {
        if (applicationContext.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            val currentTask: Task<Location> = fusedLocationClient.getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY, cancellationToken.token
            )

            currentTask.addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    val resultLocation: Location = it.result
                    val latitude = resultLocation.latitude
                    val longitude = resultLocation.longitude
                    editor.putString(LATITUDE, latitude.toString())
                    editor.putString(LONGITUDE, longitude.toString())
                    editor.apply()

                }
            }
        }
    }

    companion object {
        private const val REQUEST_FINE_LOCATION_PERMISSION_REQ_CODE = 34
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
    }
}