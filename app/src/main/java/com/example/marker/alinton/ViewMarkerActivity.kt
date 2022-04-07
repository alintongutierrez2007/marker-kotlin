package com.example.marker.alinton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ViewMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private lateinit var nombre:String
    private lateinit var descripcion: String

    private var latitude: Double =0.0
    private var longitude: Double=0.0

    private lateinit var fechahora: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_marker)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)


        nombre= intent.getStringExtra("nombre").toString()
        descripcion=intent.getStringExtra("descripcion").toString()

        fechahora=intent.getStringExtra("fecha_hora").toString()
        latitude=intent.getDoubleExtra("latitude",0.0)
        longitude=intent.getDoubleExtra("longitude",0.0)




    }

    override fun onMapReady(googleMap: GoogleMap) {
        map=googleMap

        var inicioPos= LatLng(latitude,longitude)
        map.addMarker(MarkerOptions().position(inicioPos).title(nombre))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(inicioPos,15f))
        map.uiSettings.isZoomControlsEnabled=true
    }


}