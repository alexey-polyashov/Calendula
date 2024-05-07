package com.sweethome.calendula.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

sealed class EventsScope(var currentPeriod:LocalDate = LocalDate.now()) {

    var from:LocalDate
    var to:LocalDate

    init{
        from = currentPeriod.with(TemporalAdjusters.firstDayOfMonth())
        to = currentPeriod.with(TemporalAdjusters.lastDayOfMonth())
    }

    fun nextPeriod(){

    }

    fun prevPeriod(){

    }

    fun getPeriodValue(){

    }

    class Month(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(){
    }

    class Year(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(){
        init {
            from = currentPeriod.with(TemporalAdjusters.firstDayOfYear())
            to = currentPeriod.with(TemporalAdjusters.lastDayOfYear())
        }
    }

    class Week(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(){
        init {
            from = currentPeriod.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            to = currentPeriod.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        }
    }


}