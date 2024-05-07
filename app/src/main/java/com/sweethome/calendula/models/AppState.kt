package com.sweethome.calendula.models

data class AppState(
    var scopeSize:EventsScope = EventsScope.Month(),
    var currentLayout:CalendulaLayout = CalendulaLayout.Calendar()
)