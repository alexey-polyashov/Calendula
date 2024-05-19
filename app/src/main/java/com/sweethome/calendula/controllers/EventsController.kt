package com.sweethome.calendula.controllers

import com.sweethome.calendula.models.AppState
import com.sweethome.calendula.models.CalendulaEvent
import com.sweethome.calendula.models.EventsScope
import java.time.LocalDate

class EventsController {
    companion object {
        fun getScopeEvents(scope: EventsScope):List<CalendulaEvent> {
            val events:MutableList<CalendulaEvent> = mutableListOf()

            return events
        }
        fun getEvents(startDate:LocalDate, endDate:LocalDate):List<CalendulaEvent> {
            val events:MutableList<CalendulaEvent> = mutableListOf()

            return events
        }
    }
}