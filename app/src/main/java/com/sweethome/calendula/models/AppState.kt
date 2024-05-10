package com.sweethome.calendula.models

import java.time.LocalDate


data class AppState(
    var showCalendar:Boolean = true,
    var eventScope:EventsScope = EventsScope.Month(),
    var currentLayout:CalendulaLayout = CalendulaLayout.Calendar(),
    var curentPeriodPresentation:String = "",
    var showScope:Boolean = false
){

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