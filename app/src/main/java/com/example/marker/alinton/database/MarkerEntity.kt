package com.example.marker.alinton.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="marker")
data class Marker (
    @PrimaryKey(autoGenerate = true) var id:Int=0,
    @ColumnInfo(name="fecha_hora") var fecha_hora: String,
    @ColumnInfo(name="latitude")var latitude: Double,
    @ColumnInfo(name="longitude")var longitude:Double,
    @ColumnInfo(name="nombre")var nombre: String,
    @ColumnInfo(name="descripcion")var descripcion: String){

}