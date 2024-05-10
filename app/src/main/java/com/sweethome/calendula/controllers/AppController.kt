package com.sweethome.calendula.controllers

import com.sweethome.calendula.models.AppState
import com.sweethome.calendula.models.EventsScope
import java.time.LocalDate

class AppController {

    companion object {

        //TopAppBar events
        val getPrevPeriod: (appState: AppState) -> Unit = {
            it.eventScope.prevPeriod()
            it.setPeriod(it.eventScope.currentPeriod)
        }

        val selectPeriod: (appState: AppState) -> Unit = {
            appState: AppState ->
                {}
        }

        val getNextPeriod: (appState: AppState) -> Unit = {
            it.eventScope.nextPeriod()
            it.setPeriod(it.eventScope.currentPeriod)
        }

        val homePeriod: (appState: AppState) -> Unit = {
            it.eventScope.currentPeriod = LocalDate.now()
            it.setPeriod(it.eventScope.currentPeriod)
        }

        val selectScope: (appState: AppState) -> Unit = {
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

        val showHideScope: (appState: AppState) -> Unit = {
            it.showScope = !it.showScope
        }

        //BottomBar events

    }

}