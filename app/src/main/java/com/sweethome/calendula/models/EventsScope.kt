package com.sweethome.calendula.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

sealed class EventsScope(var currentPeriod:LocalDate, val type:String): Cloneable {

    public override fun clone() = super.clone() as EventsScope
    var from:LocalDate = LocalDate.MIN
        get() {return getStartDate(currentPeriod)}
    var to:LocalDate = LocalDate.MAX
        get() {return getFinishDate(currentPeriod)}

    open fun getStartDate(period:LocalDate):LocalDate{
        return currentPeriod.with(TemporalAdjusters.firstDayOfMonth())
    }
    open fun getFinishDate(period:LocalDate):LocalDate{
        return currentPeriod.with(TemporalAdjusters.lastDayOfMonth())
    }

    open fun nextPeriod(){
        currentPeriod = getNextPeriod()
    }
    open fun getNextPeriod():LocalDate{
        return LocalDate.from(currentPeriod).plusMonths(1)
    }

    open fun prevPeriod(){
        currentPeriod = getPrevPeriod()
    }
    open fun getPrevPeriod():LocalDate{
        return LocalDate.from(currentPeriod).plusMonths(-1)
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

        override fun getPeriodValue():String {
            val formatter = DateTimeFormatter.ofPattern("Год" + ", yyyy")
            return currentPeriod.format(formatter)
        }
        override fun getStartDate(period:LocalDate):LocalDate{
            return period.with(TemporalAdjusters.firstDayOfYear())
        }
        override fun getFinishDate(period:LocalDate):LocalDate{
            return period.with(TemporalAdjusters.lastDayOfYear())
        }

        override fun getNextPeriod():LocalDate{
            return LocalDate.from(currentPeriod).plusYears(1)
        }
        override fun getPrevPeriod():LocalDate{
            return LocalDate.from(currentPeriod).plusYears(-1)
        }
    }

    class Week(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(LocalDate.now(), "week"){
        override fun getStartDate(period:LocalDate):LocalDate{
            return period.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        }
        override fun getFinishDate(period:LocalDate):LocalDate{
            return period.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        }
        override fun getPeriodValue():String {
            val formatter = DateTimeFormatter.ofPattern(
                "Неделя" + ", ww")
            return currentPeriod.format(formatter)
        }
        override fun getNextPeriod():LocalDate{
            return LocalDate.from(currentPeriod).plusWeeks(1)
        }
        override fun getPrevPeriod():LocalDate{
            return LocalDate.from(currentPeriod).plusWeeks(-1)
        }
    }

    class Day(currentPeriod:LocalDate = LocalDate.now()) :EventsScope(LocalDate.now(), "day"){
        override fun getStartDate(period:LocalDate):LocalDate{
            return period
        }
        override fun getFinishDate(period:LocalDate):LocalDate{
            return period
        }
        override fun getPeriodValue():String {
            val formatter = DateTimeFormatter.ofPattern(
                 "dd-MMM-yyyy")
            return currentPeriod.format(formatter)
        }
        override fun getNextPeriod():LocalDate{
            return LocalDate.from(currentPeriod).plusDays(1)
        }
        override fun getPrevPeriod():LocalDate{
            return LocalDate.from(currentPeriod).plusDays(-1)
        }
    }

}