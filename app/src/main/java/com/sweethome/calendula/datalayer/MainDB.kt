package com.sweethome.calendula.datalayer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
    (CalendulaEventData::class),
    (AppStateData::class)
                     ], version = 1)
abstract class MainDB: RoomDatabase() {

    abstract fun AppStateDao(): AppStateDAO
    abstract fun CalendulaEventDao(): CalendulaEventDAO

    // реализуем синглтон
    companion object {
        private var INSTANCE: MainDB? = null
        fun getInstance(context: Context): MainDB {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDB::class.java,
                        "CalendulaDB"

                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}