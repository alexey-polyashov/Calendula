package com.sweethome.calendula.datalayer

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.sweethome.calendula.models.CalendulaLayout
import com.sweethome.calendula.models.EventsScope
import java.time.LocalDate


//data class AppStateData(
//    var showCalendar:Boolean = true,
//    var eventScope: EventsScope = EventsScope.Month(),
//    var currentLayout: CalendulaLayout = CalendulaLayout.Calendar(),
//    var curentPeriodPresentation:String = "",
//    var showScope:Boolean = false
//){
//
//    init{
//        curentPeriodPresentation = eventScope.getPeriodValue()
//    }
//
//    fun setPeriod(newPeriod: LocalDate){
//        eventScope.currentPeriod = newPeriod
//        curentPeriodPresentation = eventScope.getPeriodValue()
//    }
//
//    fun refresh(){
//        curentPeriodPresentation = eventScope.getPeriodValue()
//    }
//
//}

@Entity(tableName = "appState")
class AppStateData {
    @ColumnInfo(name = "showCalendar")
    var showCalendar: Boolean? = null
    @ColumnInfo(name = "eventScope")
    var eventScope: EventsScope? = null
    @ColumnInfo(name = "currentLayout")
    var currentLayout: Int? = null
    @ColumnInfo(name = "curentPeriodPresentation")
    var currentPeriod: LocalDate? = null
    @ColumnInfo(name = "eventScope")
    var showScope: Boolean? = null

    constructor() {}

    constructor(showCalendar: Boolean, eventScope: EventsScope, currentLayout: CalendulaLayout, showScope: Boolean, currentPeriod: LocalDate) {
        this.showCalendar = showCalendar
        this.eventScope = eventScope
        this.currentLayout = currentLayout.getIdentifier()
        this.showScope = showScope
        this.currentPeriod = currentPeriod
    }

}
