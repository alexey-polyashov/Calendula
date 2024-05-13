package com.sweethome.calendula.views

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sweethome.calendula.models.AppState
import com.sweethome.calendula.models.CalendulaLayout
import com.sweethome.calendula.models.EventsScope
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.sweethome.calendula.controllers.AppController
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

    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp
    val screenWidth = with(LocalDensity.current) { screenWidthDp.dp.roundToPx() }

    val listState = rememberLazyListState(1)
    val coroutineScope = rememberCoroutineScope()
    var isFirstItemScrolled = listState.isFirstElementSemiShown(screenWidth = screenWidth)
    var isLastItemScrolled = listState.isLastElementSemiShown(screenWidth = screenWidth)


    val prevPeriod = appState.eventScope.clone()
    val nextPeriod = appState.eventScope.clone()

    prevPeriod.currentPeriod = appState.eventScope.getPrevPeriod()
    nextPeriod.currentPeriod = appState.eventScope.getNextPeriod()

    val listOfPeriod: List<EventsScope> = listOf(prevPeriod, appState.eventScope, nextPeriod)


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
                modifier = Modifier
                        .clickable {coroutineScope.launch { listState.scrollToItem(1)} }
            ) {
                items(3)
                    {listIndex -> Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(screenWidthDp.dp)
                        ,
                        text=listOfPeriod[listIndex].getPeriodValue())}
                }
            }
    }

    DisposableEffect(currentPeriod){
        coroutineScope.launch { scrollToCurrent(listState) }
        onDispose { }
    }

    LaunchedEffect(isFirstItemScrolled, isLastItemScrolled){
        if (isFirstItemScrolled) {
            Log.d("listState", "first visible")
            AppController.getPrevPeriod(appState)
            refresh()
            //listState.scrollToItem(0)
        }
        if (isLastItemScrolled) {
            Log.d("listState", "last visible")
            AppController.getNextPeriod(appState)
            refresh()
            //listState.scrollToItem(2)
        }

    }

}

suspend fun scrollToCurrent(listState:LazyListState){
    listState.scrollToItem(1)
}

@Composable
private fun LazyListState.isFirstElementSemiShown(screenWidth:Int): Boolean {

    return remember(this) {
        derivedStateOf {
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val firstEl = visibleItemsInfo[0]
                val isFirstVisible = firstEl.index==0
                if(isFirstVisible)
                    screenWidth - abs(firstEl.offset)>screenWidth/2
                else
                    false
            }
        }
    }.value
}

@Composable
private fun LazyListState.isLastElementSemiShown(screenWidth:Int): Boolean {

    return remember(this) {
        derivedStateOf {
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val LastEl = visibleItemsInfo.last()
                val isFirstVisible = LastEl.index==(layoutInfo.totalItemsCount-1)
                if(isFirstVisible)
                    screenWidth - abs(LastEl.offset)>screenWidth/2
                else
                    false
            }
        }
    }.value
}
