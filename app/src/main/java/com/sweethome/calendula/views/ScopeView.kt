package com.sweethome.calendula.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweethome.calendula.models.AppState
import com.sweethome.calendula.models.CalendulaLayout
import com.sweethome.calendula.models.EventsScope
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun ShowPeriodScope(
    appState: AppState,
    showScope: Boolean,
    currentPeriod: String,
    currentLayout: CalendulaLayout,
    refresh: () -> Unit
) {
    Log.d("listState", "start")

    val listState = rememberLazyListState(1)
    val coroutineScope = rememberCoroutineScope()

    val isNextMonth by remember {
        derivedStateOf{
        }
    }

    val isPrevMonth by remember {
        derivedStateOf{
        }
    }

    val prevPeriod = appState.eventScope.clone()
    val nextPeriod = appState.eventScope.clone()

    prevPeriod.currentPeriod = appState.eventScope.getPrevPeriod()
    nextPeriod.currentPeriod = appState.eventScope.getNextPeriod()

    val listOfPeriod: List<EventsScope> = listOf(prevPeriod, appState.eventScope, nextPeriod)

    val config = LocalConfiguration.current

    if(showScope){
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
                )
        ) {
            LazyRow(
                state = listState,
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(3)
                    {listIndex -> Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(config.screenWidthDp.dp),
                        text=listOfPeriod[listIndex].getPeriodValue())}
                }
            }
    }

    DisposableEffect(currentPeriod){
        coroutineScope.launch { scrollToCurrent(listState) }
        onDispose { }
    }

//    DisposableEffect(listState.layoutInfo.visibleItemsInfo[0].offset){
//        if(abs(listState.layoutInfo.visibleItemsInfo[0].offset)>500){
//            appState.eventScope = prevPeriod;
//            refresh()
//        }
//        onDispose { }
//    }

}

suspend fun scrollToCurrent(listState:LazyListState){
    listState.scrollToItem(1)
}
