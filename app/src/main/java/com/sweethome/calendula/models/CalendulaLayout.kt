package com.sweethome.calendula.models

sealed class CalendulaLayout {

    open fun getIdentifier():Int{
        return 0
    }

    fun getByIdentifier(id:Int):CalendulaLayout{
        when(id) {
            1 -> return Calendar()
            2 -> return NewEvent()
            3 -> return EventDetail()
            4 -> return NewCategory()
            5 -> return Settings()
            else -> return Calendar()
        }
    }

    class Calendar:CalendulaLayout(){
        override fun getIdentifier():Int{
            return 1
        }
    }
    class NewEvent:CalendulaLayout(){
        override fun getIdentifier():Int{
            return 2
        }
    }
    class EventDetail:CalendulaLayout(){
        override fun getIdentifier():Int{
            return 3
        }
    }
    class NewCategory:CalendulaLayout(){
        override fun getIdentifier():Int{
            return 4
        }
    }
    class Settings:CalendulaLayout(){
        override fun getIdentifier():Int{
            return 5
        }
    }
}
