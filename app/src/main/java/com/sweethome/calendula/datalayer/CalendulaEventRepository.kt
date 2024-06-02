package com.sweethome.calendula.datalayer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendulaEventRepository(private val eventsDao: CalendulaEventDAO) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addEvent(event:CalendulaEventData){
        coroutineScope.launch(Dispatchers.IO ) {
            eventsDao.addEvent(event)
        }
    }

    fun deleteEvent(id:Int){
        coroutineScope.launch(Dispatchers.IO ) {
            val event = eventsDao.getEventById(id)
            event.value?.let { eventsDao.deleteEvent(it) }
        }
    }

    fun updateEvent(event: CalendulaEventData){
        coroutineScope.launch(Dispatchers.IO ) {
            eventsDao.updateEvent(event)
        }
    }
}