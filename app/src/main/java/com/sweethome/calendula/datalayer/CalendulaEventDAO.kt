package com.sweethome.calendula.datalayer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CalendulaEventDAO {
    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<CalendulaEventData>>

    @Query("SELECT * FROM events e WHERE e.Id = :id")
    fun getEventById(id:Int):LiveData<CalendulaEventData>

    @Insert
    fun addEvent(event: CalendulaEventData)

    @Delete
    fun deleteEvent(event: CalendulaEventData):Int

    @Update
    fun updateEvent(event: CalendulaEventData)
}