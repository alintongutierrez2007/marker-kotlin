package com.example.marker.alinton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.marker.alinton.database.Marker
import com.example.marker.alinton.database.MarkerViewModel

class UpdateActivity : AppCompatActivity() {

    private lateinit var nombre:String
    private lateinit var descripcion: String
    private var id: Int=0

    private var latitude: Double =0.0
    private var longitude: Double=0.0

    private lateinit var fecha_hora: String

    private lateinit var nombreTexto: EditText
    private lateinit var descripcionTexto: EditText

    private lateinit var button: Button
    private lateinit var buttonEliminar :ImageButton

    private lateinit var mMarkerViewModel : MarkerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        id= intent.getIntExtra("id",0)
        nombre= intent.getStringExtra("nombre").toString()
        descripcion=intent.getStringExtra("descripcion").toString()

        fecha_hora=intent.getStringExtra("fecha_hora").toString()
        latitude=intent.getDoubleExtra("latitude",0.0)
        longitude=intent.getDoubleExtra("longitude",0.0)

        nombreTexto= findViewById<EditText>(R.id.editTextNombre_update)
        descripcionTexto= findViewById<EditText>(R.id.editTextDescripcion_update)

        nombreTexto.setText(nombre)
        descripcionTexto.setText(descripcion)

        findViewById<TextView>(R.id.textView_latitud_update).apply { text=latitude.toString() }
        findViewById<TextView>(R.id.textView_longitud_update).apply { text=longitude.toString() }

        findViewById<TextView>(R.id.textView_fecha_update).apply { text=fecha_hora}


        mMarkerViewModel= ViewModelProvider(this).get(MarkerViewModel::class.java)

        buttonEliminar= findViewById<ImageButton>(R.id.button_eliminar)

        button=findViewById<Button>(R.id.button_update)

        button.setOnClickListener{
            if (TextUtils.isEmpty(nombreTexto.toString())) {
                Toast.makeText(applicationContext, "faltan datos", Toast.LENGTH_LONG).show()

            } else {

                val marker = Marker(
                    id = id,
                    fecha_hora = fecha_hora,
                    latitude = latitude,
                    longitude = longitude,
                    nombre = nombreTexto.text.toString(),
                    descripcion = descripcionTexto.text.toString()
                )

                mMarkerViewModel.updateMarker(marker)

                println("clic en guardar marcador ahora....")

                Toast.makeText(applicationContext, "Datos Actualizados", Toast.LENGTH_LONG).show()


            }
        }


        buttonEliminar.setOnClickListener{

            val marker = Marker(
                id = id,
                fecha_hora = fecha_hora,
                latitude = latitude,
                longitude = longitude,
                nombre = nombre,
                descripcion = descripcion
            )


            val builder = AlertDialog.Builder(this)
            builder.setMessage("Deseas borrar este marcador?")
                .setCancelable(false)
                .setPositiveButton("Yes"){ dialog, id ->
                    mMarkerViewModel.deleteMarker(marker)
                    finish()
                }
                .setNegativeButton("No"){ dialog, id->
                    dialog.dismiss()
                }

            val alert= builder.create()
            alert.show()

        }

    }
}