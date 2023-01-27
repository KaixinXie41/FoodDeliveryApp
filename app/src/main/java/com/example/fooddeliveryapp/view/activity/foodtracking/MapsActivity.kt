package com.example.fooddeliveryapp.view.activity.foodtracking

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityMapsBinding
import com.example.fooddeliveryapp.view.auth.registration.RegistrationActivity.Companion.Account_Information
import com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption.PickUpAdapter.Companion.LATITUDE
import com.example.fooddeliveryapp.view.fragment.checkout.deliveryoption.PickUpAdapter.Companion.LONGITUDE
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        sharedPreferences = this.getSharedPreferences(Account_Information, AppCompatActivity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latitude = sharedPreferences.getString(LATITUDE,"NONE")
        val longitude = sharedPreferences.getString(LONGITUDE,"NONE")

        val home = LatLng(33.216743564251914, -96.73570575575988)
        val restaurant = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
        val midpoint = LatLng(33.218381717748215, -96.68222313092001)
        mMap.addPolyline(
            PolylineOptions().add(restaurant,midpoint,home)
                .geodesic(true)
                .color(Color.BLUE)
        )
        mMap.addMarker(
            MarkerOptions().position(home).title("Marker in Home")
                .icon(bitmapVector(R.drawable.ic_baseline_home_24))
        )
        mMap.addMarker(
            MarkerOptions().position(midpoint).title("Marker in Driver")
                .icon(bitmapVector(R.drawable.ic_baseline_directions_car_24)
                )
        )
        mMap.addMarker(
            MarkerOptions().position(restaurant).title("Marker in Restaurant")
                .icon(bitmapVector(R.drawable.ic_baseline_liquor_24)
                )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(midpoint, 12.5f))
    }
    private fun bitmapVector(vectorId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this, vectorId)
        vectorDrawable?.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}