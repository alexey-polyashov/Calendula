package com.sweethome.calendula.controllers

import com.sweethome.calendula.datalayer.AppStateData
import com.sweethome.calendula.models.EventsScope
import java.time.LocalDate

class AppController {

    companion object {

        //TopAppBar events
        val getPrevPeriod: (appState: AppStateData) -> Unit = {
            it.eventScope.prevPeriod()
            it.setPeriod(it.eventScope.currentPeriod)
        }

        val selectPeriod: (appState: AppStateData) -> Unit = {
            appState: AppStateData ->
                {}
        }

        val getNextPeriod: (appState: AppStateData) -> Unit = {
            it.eventScope.nextPeriod()
            it.setPeriod(it.eventScope.currentPeriod)
        }

        val homePeriod: (appState: AppStateData) -> Unit = {
            it.eventScope.currentPeriod = LocalDate.now()
            it.setPeriod(it.eventScope.currentPeriod)
        }

        val selectScope: (appState: AppStateData) -> Unit = {
            it.eventScope = when(it.eventScope){
                is EventsScope.Day -> {
                    EventsScope.Year()
                }
                is EventsScope.Year -> {
                    EventsScope.Month()
                }
                is EventsScope.Month -> {
                    EventsScope.Week()
                }
                is EventsScope.Week -> {
                    EventsScope.Day()
                }
            }
            it.refresh()
        }

        val showHideScope: (appState: AppStateData) -> Unit = {
            it.showScope = !it.showScope
        }

        //BottomBar events

    }

}