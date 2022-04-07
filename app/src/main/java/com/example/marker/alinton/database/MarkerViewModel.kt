package com.example.marker.alinton.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MarkerViewModel(application: Application): AndroidViewModel(application) {
     val readAllData: LiveData<List<Marker>>
    private val repository: MarkerRepository

    init{
        val markerdao= MarkerDatabase.getDataBase(application).markerDao()
        repository= MarkerRepository(markerdao)
        readAllData=repository.readAllData
    }

    fun addMarker(marker:Marker){
        viewModelScope.launch(Dispatchers.IO){
            repository.addMarker(marker)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO){
           repository.deleteAllData()        }
    }

    fun updateMarker(marker:Marker){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateMarker(marker)
        }
    }

    fun deleteMarker(marker: Marker){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteMarker(marker)
        }
    }

    fun searchDatabase(searchQuery:String): LiveData<List<Marker>>{
        return repository.searchDatabase(searchQuery).asLiveData()
    }




}