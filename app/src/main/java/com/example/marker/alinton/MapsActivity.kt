package com.example.marker.alinton

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toolbar

import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.marker.alinton.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val userLocation= Location("")

    private lateinit var mySaveButton:FloatingActionButton

    var latitudMarcador:Double=0.0
    var longitudMarcador:Double=0.0


    lateinit var imageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mySaveButton=findViewById(R.id.save_btn)

        mySaveButton.visibility=View.INVISIBLE



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        imageButton=findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener{
            val intent = Intent(this,ListActivity::class.java)
            startActivity(intent)
        }



    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        var inicioPos=LatLng(12.12774,-86.26573)

        if(userLocation.latitude !=0.0){
            inicioPos= LatLng(userLocation.latitude, userLocation.longitude,)
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicioPos))

        mMap.setOnMapClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled=true

        setUpMap()
    }


    @SuppressLint("MissingPermission")

    private fun setUpMap(){

        requestLocationPermission()

        mMap.isMyLocationEnabled=true

        this.getUserLocation()

        mySaveButton.setOnClickListener {
           println("clic en boton banano")
            val intent = Intent(this,SaveActivity::class.java)
            intent.putExtra("latitude",latitudMarcador)
            intent.putExtra("longitude",longitudMarcador)
            startActivity(intent)
        }

    }



    private fun requestLocationPermission(){

        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.M ){

            if((checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                &&(checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)){


             }else{
                 val permissionArray= arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                 requestPermissions(permissionArray,1000)
             }

        }else{

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==1000){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }else if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)){
                showLocationPermissionRationaleDialog()
            }


            if(grantResults.isNotEmpty() && grantResults[1]==PackageManager.PERMISSION_GRANTED){

            }else if(shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_COARSE_LOCATION)){
                showLocationPermissionRationaleDialog()
            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLocationPermissionRationaleDialog() {

        val dialog= AlertDialog.Builder(this)
            .setTitle("se necesita ubicacion para guardar metodo")
            .setMessage("aceptar el permiso para ver tu ubicacion en mapa")
            .setPositiveButton(android.R.string.ok){_,_,->
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),1000)
            }
            .setNegativeButton(android.R.string.cancel){_,_,->
                finish()
            }
            .show()
    }



   @SuppressLint("MissingPermission")
   private fun getUserLocation(){
        val fuseLocationClient= LocationServices.getFusedLocationProviderClient(this)
                fuseLocationClient.lastLocation.addOnSuccessListener {
                    location: Location?->
                    if(location!= null){
                        userLocation.latitude=location.latitude
                        userLocation.longitude=location.longitude
                    }
                }
    }

    override fun onMapClick(p0: LatLng) {


        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0).title("Marker in Gps"))
        latitudMarcador=p0.latitude
        longitudMarcador=p0.longitude

        mySaveButton.visibility=View.VISIBLE

    }


}