package com.example.cokesfa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : FragmentActivity() , OnMapReadyCallback {

    private lateinit var map:GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val supportFragment=supportFragmentManager.findFragmentById(R.id.mapF) as SupportMapFragment
        supportFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        map=googleMap!!
        var location=LatLng(23.4714349,91.1414637)
        map.addMarker(MarkerOptions().position(location).title("Coca Cola Factory"))
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
        //map.setMinZoomPreference(10.0f)
        map.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
    }

}
