package com.sweethome.calendula.datalayer

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppStateRepository(private val appStateDao: AppStateDAO) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun saveAppState(appState: AppStateData){
        coroutineScope.launch(Dispatchers.IO ) {
            appStateDao.saveAppState(appState)
        }
    }

    val appState: LiveData<AppStateData> = appStateDao.getAppState()

}