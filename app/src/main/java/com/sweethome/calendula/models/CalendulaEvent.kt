package com.sweethome.calendula.models

import java.time.LocalDateTime
import kotlin.time.Duration

data class CalendulaEvent(
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var periodic: Boolean = false,
    var period: Duration = Duration.ZERO,
    var remind: Boolean = true,
    var shortName:String = "",
    var description:String = "",
    var category:String = "",
    var done:Boolean = false) {
}