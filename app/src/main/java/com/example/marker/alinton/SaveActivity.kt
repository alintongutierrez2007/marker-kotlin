package com.example.marker.alinton


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.marker.alinton.database.Marker
import com.example.marker.alinton.database.MarkerDatabase
import com.example.marker.alinton.database.MarkerViewModel
import java.text.SimpleDateFormat
import java.util.*



class SaveActivity : AppCompatActivity() {

    private var latitude: Double =0.0
    private var longitude: Double=0.0

   private lateinit var nombreTexto: EditText
    private lateinit var descripcionTexto: EditText

    lateinit var button: Button

    private lateinit var mMarkerViewModel : MarkerViewModel


        companion object{
            lateinit var database:MarkerDatabase
        }


        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        SaveActivity.database= Room.databaseBuilder(this,MarkerDatabase::class.java, "db_marker").build()


            latitude=intent.getDoubleExtra("latitude",0.0)
            longitude=intent.getDoubleExtra("longitude",0.0)

            findViewById<TextView>(R.id.textView_latitud).apply { text=latitude.toString() }
            findViewById<TextView>(R.id.textView_longitud).apply { text=longitude.toString() }

            findViewById<TextView>(R.id.textView_fecha).apply { text=getCurrentDate() }

            nombreTexto= findViewById<EditText>(R.id.editTextNombre)
            descripcionTexto= findViewById<EditText>(R.id.editTextDescripcion)

            button=findViewById<Button>(R.id.button)

            mMarkerViewModel=ViewModelProvider(this).get(MarkerViewModel::class.java)


            button.setOnClickListener {


                if (TextUtils.isEmpty(nombreTexto.toString())) {
                    Toast.makeText(applicationContext, "faltan datos", Toast.LENGTH_LONG).show()

                } else{

                val marker = Marker(
                    id = 0,
                    fecha_hora = getCurrentDate(),
                    latitude = latitude,
                    longitude = longitude,
                    nombre = nombreTexto.text.toString(),
                    descripcion = descripcionTexto.text.toString()
                )

                println("clic en guardar marcador ahora....")

                mMarkerViewModel.addMarker(marker)

                Toast.makeText(applicationContext, "Datos Guardados", Toast.LENGTH_LONG)
                    .show()

                    nombreTexto.text.clear()
                    descripcionTexto.text.clear()

            }

            }



    }



    private fun getCurrentDate():String{
        val sdf= SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }




}