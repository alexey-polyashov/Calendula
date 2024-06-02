package com.sweethome.calendula.datalayer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sweethome.calendula.models.AppState

@Dao
interface AppStateDAO {
    @Query("SELECT * FROM appState a Limit 1")
    fun getAppState(): LiveData<AppStateData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAppState(appState: AppStateData)

}