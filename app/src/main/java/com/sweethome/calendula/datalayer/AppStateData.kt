package com.sweethome.calendula.datalayer

import com.sweethome.calendula.models.AppState

class AppStateData {
    companion object {
        fun getState(): AppState {
            return AppState()
        }
    }
}