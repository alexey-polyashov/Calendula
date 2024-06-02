package com.sweethome.calendula.views

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.sweethome.calendula.R
import com.sweethome.calendula.controllers.AppController
import com.sweethome.calendula.datalayer.AppStateData
import com.sweethome.calendula.models.CalendulaLayout
import com.sweethome.calendula.models.EventsScope



@Composable
fun ShowTopAppBar(
    appState: AppStateData,
    currentPeriod: String,
    currentLayout: CalendulaLayout,
    showScope: Boolean,
    refresh: () -> Unit
){

    val periodType = when(appState.eventScope){
        is EventsScope.Day-> stringResource(R.string.period_day)
        is EventsScope.Month-> stringResource(R.string.period_month)
        is EventsScope.Week-> stringResource(R.string.period_week)
        is EventsScope.Year-> stringResource(R.string.period_year)
    }

    val colorScopeButton = if(showScope) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary

    ClickableText(
        onClick = {
            AppController.selectScope(appState)
            refresh()
        },
        text = AnnotatedString(periodType),
        style = MaterialTheme.typography.titleLarge.merge(TextStyle(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary)),
    )
    IconButton(
        onClick = {
            AppController.getPrevPeriod(appState)
            refresh()
        },
    ) {
        Icon(
            Icons.Filled.KeyboardArrowLeft,
            contentDescription = stringResource(R.string.prev_period),)
    }
    ClickableText(
        onClick = {
            AppController.selectPeriod(appState)
            refresh()
                  },
        text = AnnotatedString(currentPeriod),
        style = MaterialTheme.typography.titleLarge.merge(TextStyle(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary)),
    )
    IconButton(
        onClick = {
            AppController.getNextPeriod(appState)
            refresh()
                  },
    ) {
        Icon(Icons.Filled.KeyboardArrowRight, contentDescription = stringResource(R.string.next_period))
    }
    IconButton(
            onClick = {
                AppController.homePeriod(appState)
                refresh()
            },
    ) {
        Icon(
            Icons.Filled.Home,
            contentDescription = stringResource(R.string.period_home),)
    }
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(containerColor = colorScopeButton),
        onClick = {
            AppController.showHideScope(appState)
            refresh()
        },
    ) {
        Icon(Icons.Filled.MoreVert, contentDescription = stringResource(R.string.next_period))
    }


}
