package com.sweethome.calendula.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sweethome.calendula.datalayer.AppStateDAO
import com.sweethome.calendula.datalayer.AppStateData
import com.sweethome.calendula.datalayer.AppStateRepository
import com.sweethome.calendula.datalayer.MainDB
import java.time.LocalDate

class AppState(application: Application): ViewModel() {

    val appState: LiveData<AppStateData>
    private val repository: AppStateRepository

    var showCalendar:Boolean = true
    var eventScope: EventsScope = EventsScope.Month()
    var currentLayout: CalendulaLayout = CalendulaLayout.Calendar()
    var curentPeriodPresentation:String = ""
    var showScope:Boolean = false

    init{
        val mainDb = MainDB.getInstance(application)
        val appStateDao = mainDb.AppStateDao()
        repository = AppStateRepository(appStateDao)
        appState = repository.appState

    }

    companion object {
        fun getState(): AppStateData {
            return AppStateData()
        }
        init{
            curentPeriodPresentation = eventScope.getPeriodValue()
        }

        fun setPeriod(newPeriod: LocalDate){
            eventScope.currentPeriod = newPeriod
            curentPeriodPresentation = eventScope.getPeriodValue()
        }

        fun refresh(){
            curentPeriodPresentation = eventScope.getPeriodValue()
        }
    }

}