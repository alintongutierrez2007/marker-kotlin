package com.example.marker.alinton

import android.content.Context
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.marker.alinton.database.ListAdapter
import com.example.marker.alinton.database.Marker
import com.example.marker.alinton.database.MarkerViewModel
import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.client.KotlinCsvExperimental
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

import androidx.appcompat.widget.SearchView

class ListActivity : AppCompatActivity() {

    private lateinit var mMarkerViewModel : MarkerViewModel

    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var imageButtonDownload: ImageButton
    lateinit var imageButtonDelete: ImageButton

    lateinit var listado :List<Marker>

    private var myAdapter:ListAdapter ?= null




    @OptIn(KotlinCsvExperimental::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        myAdapter= ListAdapter()
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter=myAdapter
        linearLayoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager= linearLayoutManager

        mMarkerViewModel= ViewModelProvider(this).get(MarkerViewModel::class.java)
        mMarkerViewModel.readAllData.observe( this, Observer { marker->
            listado=marker
            myAdapter!!.setData(marker)
        })

        imageButtonDelete= findViewById<ImageButton>(R.id.imageButtonDelete)
        imageButtonDownload= findViewById<ImageButton>(R.id.imageButtonDownload)


        imageButtonDelete.setOnClickListener{

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Deseas borrar todos los datos??")
                .setCancelable(false)
                .setPositiveButton("Yes"){ dialog, id ->
                    mMarkerViewModel.deleteAllData()
                }
                .setNegativeButton("No"){ dialog, id->
                    dialog.dismiss()
                }

            val alert= builder.create()
            alert.show()

            //Toast.makeText(this,"clic en eliminar", Toast.LENGTH_LONG).show()

        }


        imageButtonDownload.setOnClickListener{
            Toast.makeText(this,"clic en descargar", Toast.LENGTH_LONG).show()

            val csvFile = generateFile(this,"marker_csv_files.csv")
            if(csvFile!= null){
                exportMarkersToCsv(csvFile)
            }

            val intent =gotoFileIntent(this, csvFile!!)
            startActivity(intent)
        }


        val searchView: SearchView= findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0!=null){
                    searchDatabase(p0)
                }
                    return false
            }
        })




    }

    private fun searchDatabase(query :String){
        val searchQuery="%$query%"

        mMarkerViewModel.searchDatabase(searchQuery).observe(this) { list ->
            list.let {

                myAdapter!!.setData(it)
            }
        }
    }





    private fun gotoFileIntent(context: Context, file: File): Intent {

        val intent=Intent(Intent.ACTION_VIEW)
        val contentUris= FileProvider.getUriForFile(context,"com.example.marker.alinton.fileprovider", file)
        val mimeType = context.contentResolver.getType(contentUris)
        intent.setDataAndType(contentUris,mimeType)
        intent.flags=Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        return intent
    }

    private fun generateFile(context: Context, fileName :String): File? {
        val csvFile= File(context.filesDir,fileName)
        csvFile.createNewFile()
        return if(csvFile.exists()){
            csvFile
        }else{
            null
        }
    }

    fun exportMarkersToCsv(csvFile: File){
        csvWriter().open(csvFile,append = false){
            writeRow(listOf("[id]","[fecha_hora]","[latitude]", "[longitude]","[nombre]","[descripcion]"))
           listado.forEachIndexed{ index, marker->
               writeRow(listOf(index,marker.fecha_hora,marker.latitude, marker.longitude,marker.nombre, marker.descripcion))
           }
        }
    }



}