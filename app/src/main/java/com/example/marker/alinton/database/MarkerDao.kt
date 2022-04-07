package com.example.marker.alinton.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMarker(marker:Marker):Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun Update(marker: Marker)

    @Delete
    fun delete(marker: Marker)

    @Query("DELETE FROM marker ")
    fun deleteAllData()

    @Query("SELECT * FROM marker ORDER BY id DESC")
    fun readAllData(): LiveData<List<Marker>>

    @Query("SELECT * FROM marker WHERE nombre LIKE :searchQuery or descripcion LIKE :searchQuery")
    fun searchDataBase(searchQuery:String): Flow<List <Marker>>


}