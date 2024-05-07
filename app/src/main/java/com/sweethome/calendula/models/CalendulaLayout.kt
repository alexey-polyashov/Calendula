package com.sweethome.calendula.models

sealed class CalendulaLayout {
    class Calendar:CalendulaLayout()
    class NewEvent:CalendulaLayout()
    class EventDetail:CalendulaLayout()
    class NewCategory:CalendulaLayout()
    class Settings:CalendulaLayout()
}
