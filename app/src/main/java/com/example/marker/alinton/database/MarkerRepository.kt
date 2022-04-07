package com.example.marker.alinton.database


import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class MarkerRepository (private val markerdao: MarkerDao){

         val readAllData: LiveData<List<Marker>> = markerdao.readAllData()

            suspend fun addMarker(marker: Marker){
                markerdao.addMarker(marker)
            }

            suspend fun deleteAllData(){
                markerdao.deleteAllData()
            }

            suspend fun updateMarker(marker:Marker){
              markerdao.Update(marker)
            }

            suspend fun deleteMarker(marker:Marker){
                markerdao.delete(marker)
            }

            fun searchDatabase(searchQuery: String): Flow<List<Marker>>{
                return markerdao.searchDataBase(searchQuery)
            }

}