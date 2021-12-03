package com.example.mymegot

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.mymegot.databinding.ActivityMapsBinding
import com.google.android.gms.common.internal.FallbackServiceBroker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var locationManager : LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private val LOCATION_PERMISSION_REQUEST = 1






    private fun getLocationAccess(): Location? {
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
        else
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val lm = getSystemService(LOCATION_SERVICE) as LocationManager
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                }
                mMap.isMyLocationEnabled = true
            }
            else {
                Toast.makeText(this, "User has not granted location access permission", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("ServiceCast")


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLocationAccess()
        // Add a marker in Sydney and move the camera
        val btn_click_me = findViewById(R.id.button1) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            // your code to perform when the user clicks on the button
            val loc =getLocationAccess()

            if (loc != null) {
                mMap.addMarker(MarkerOptions().position(LatLng(loc.latitude,loc.longitude)).title("Marker in Sydney"))
            }


        }

    }

}

