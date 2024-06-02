package com.sweethome.calendula.datalayer

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.Duration

@Entity(tableName = "events")
class CalendulaEventData{
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    var id:Int = 0
    @ColumnInfo(name = "date")
    var date: LocalDate = LocalDate.now()
    @ColumnInfo(name = "time")
    var time: LocalTime = LocalTime.now()
    @ColumnInfo(name = "periodiv")
    var periodic: Boolean = false
    @ColumnInfo(name = "period")
    var period: Duration = Duration.ZERO
    @ColumnInfo(name = "remind")
    var remind: Boolean = true
    @ColumnInfo(name = "shortName")
    var shortName:String = ""
    @ColumnInfo(name = "description")
    var description:String = ""
    @ColumnInfo(name = "category")
    var category:String = ""
    @ColumnInfo(name = "done")
    var done:Boolean = false

    constructor() {}


}