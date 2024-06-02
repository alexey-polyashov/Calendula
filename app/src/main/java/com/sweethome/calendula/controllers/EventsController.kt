package com.sweethome.calendula.controllers

import com.sweethome.calendula.datalayer.CalendulaEventData
import com.sweethome.calendula.models.EventsScope
import java.time.LocalDate

class EventsController {
    companion object {
        fun getScopeEvents(scope: EventsScope):List<CalendulaEventData> {
            val events:MutableList<CalendulaEventData> = mutableListOf()

            return events
        }
        fun getEvents(startDate:LocalDate, endDate:LocalDate):List<CalendulaEventData> {
            val events:MutableList<CalendulaEventData> = mutableListOf()

            return events
        }
    }
}