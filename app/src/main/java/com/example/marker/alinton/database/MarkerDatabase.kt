package com.example.marker.alinton.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Marker::class], version=1, exportSchema = false)
abstract  class MarkerDatabase: RoomDatabase(){
    abstract fun markerDao(): MarkerDao


    companion object{
        private const val DATABASE_NAME="db_marker"

        @Volatile
        private var INSTANCE: MarkerDatabase?=null

        fun getDataBase(context: Context): MarkerDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }

            synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext, MarkerDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE=instance
                return instance
            }
        }

    }


}