package com.sweethome.calendula.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

sealed class EventsScope(var currentPeriod:LocalDate, val type:String) {

    var from:LocalDate
    var to:LocalDate

    init{
        from = currentPeriod.with(TemporalAdjusters.firstDayOfMonth())
        to = currentPeriod.with(TemporalAdjusters.lastDayOfMonth())
    }

    open fun nextPeriod(){
        currentPeriod = LocalDate.from(currentPeriod).plusMonths(1)
    }

    open fun prevPeriod(){
        currentPeriod = LocalDate.from(currentPeriod).plusMonths(-1)
    }

    open fun getPeriodValue():String{
        val formatter = DateTimeFormatter.ofPattern("yyyy, MMM", Locale("ru"))
        return currentPeriod.format(formatter)
    }

    fun setPeriod(){

    }

    class Month(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(LocalDate.now(), "month"){
    }

    class Year(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(LocalDate.now(), "year"){
        init {
            from = currentPeriod.with(TemporalAdjusters.firstDayOfYear())
            to = currentPeriod.with(TemporalAdjusters.lastDayOfYear())
        }
        override fun getPeriodValue():String {
            val formatter = DateTimeFormatter.ofPattern("Год" + ", yyyy")
            return currentPeriod.format(formatter)
        }

        override fun nextPeriod(){
            currentPeriod = LocalDate.from(currentPeriod).plusYears(1)
        }
        override fun prevPeriod(){
            currentPeriod = LocalDate.from(currentPeriod).plusYears(-1)
        }
    }

    class Week(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(LocalDate.now(), "week"){
        init {
            from = currentPeriod.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            to = currentPeriod.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        }
        override fun getPeriodValue():String {
            val formatter = DateTimeFormatter.ofPattern(
                "Неделя" + ", ww")
            return currentPeriod.format(formatter)
        }
        override fun nextPeriod(){
            currentPeriod = LocalDate.from(currentPeriod).plusWeeks(1)
        }
        override fun prevPeriod(){
            currentPeriod = LocalDate.from(currentPeriod).plusWeeks(-1)
        }
    }

    class Day(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(LocalDate.now(), "day"){
        init {
            from = currentPeriod
            to = currentPeriod
        }
        override fun getPeriodValue():String {
            val formatter = DateTimeFormatter.ofPattern(
                 "dd-MMM-yyyy")
            return currentPeriod.format(formatter)
        }
        override fun nextPeriod(){
            currentPeriod = LocalDate.from(currentPeriod).plusDays(1)
        }
        override fun prevPeriod(){
            currentPeriod = LocalDate.from(currentPeriod).plusDays(-1)
        }
    }

}