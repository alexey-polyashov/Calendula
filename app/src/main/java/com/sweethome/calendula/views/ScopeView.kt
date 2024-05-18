package com.sweethome.calendula.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.sweethome.calendula.models.AppState
import com.sweethome.calendula.models.CalendulaLayout
import com.sweethome.calendula.models.EventsScope
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import com.sweethome.calendula.controllers.AppController
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale
import kotlin.math.abs

@Composable
fun ShowPeriodScope(
    appState: AppState,
    showScope: Boolean,
    currentPeriod: String,
    currentLayout: CalendulaLayout,
    refresh: () -> Unit
) {

    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp
    val screenWidth = with(LocalDensity.current) { screenWidthDp.dp.roundToPx() }

    val listState = rememberLazyListState(1)
    val coroutineScope = rememberCoroutineScope()

    val prevPeriod = appState.eventScope.clone()
    val nextPeriod = appState.eventScope.clone()

    prevPeriod.currentPeriod = appState.eventScope.getPrevPeriod()
    nextPeriod.currentPeriod = appState.eventScope.getNextPeriod()
    Log.d("debugmes", "1 ShowPeriodScope, currentPeriod - ${currentPeriod.toString()}")
    Log.d("debugmes", "1 ShowPeriodScope, nextPeriod - ${nextPeriod.currentPeriod.toString()}")
    Log.d("debugmes", "1 ShowPeriodScope, prevPeriod - ${prevPeriod.currentPeriod.toString()}")

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
            ) {
                items(listOfPeriod){
                    Log.d("debugmes", "2 ShowPeriodScope, it.from - ${it.from.toString()}")
                    DrawScopeGrid(it.from, appState, screenWidthDp)
                    }
                }
            }
    }

    LaunchedEffect(listState.isScrollInProgress){
        val isFirstItemScrolled = isFirstElementSemiShown(listState, screenWidth)
        val isLastItemScrolled = isLastElementSemiShown(listState, screenWidth)
        if (isFirstItemScrolled) {
            AppController.getPrevPeriod(appState)
            refresh()
        }
        if (isLastItemScrolled) {
            AppController.getNextPeriod(appState)
            refresh()
        }
        if(!listState.isScrollInProgress) {
            coroutineScope.launch { scrollToCurrent(listState) }
        }

    }

}

suspend fun scrollToCurrent(listState:LazyListState){
    listState.scrollToItem(1)
}


fun isFirstElementSemiShown(ls:LazyListState, screenWidth:Int): Boolean {

    var result:Boolean = false

    val visibleItemsInfo = ls.layoutInfo.visibleItemsInfo

    if (ls.layoutInfo.totalItemsCount == 0) {
        result = false
    } else {
        val firstEl = visibleItemsInfo[0]
        val isFirstVisible = firstEl.index==0
        if(isFirstVisible)
            result = (screenWidth - abs(firstEl.offset)>screenWidth/2)
        else
            result = false
    }

    return result
}

fun isLastElementSemiShown(ls:LazyListState, screenWidth:Int): Boolean {

    var result:Boolean = false

    val visibleItemsInfo = ls.layoutInfo.visibleItemsInfo

    if (ls.layoutInfo.totalItemsCount == 0) {
        result = false
    } else {
        val LastEl = visibleItemsInfo.last()
        val isFirstVisible = LastEl.index==(ls.layoutInfo.totalItemsCount-1)
        if(isFirstVisible)
            result = (screenWidth - abs(LastEl.offset)>screenWidth/2)
        else
            result = false
    }

    return result
}

@Composable
fun DrawScopeGrid(currentPeriod: LocalDate, appState: AppState, screenWidthDp: Int){
    val scope = appState.eventScope
    Log.d("debugmes", "3 DrawScopeGrid, currentPeriod - ${currentPeriod.toString()}")
    when(scope){
        is EventsScope.Year -> ShowYearGrid(scope, currentPeriod, screenWidthDp)
        is EventsScope.Month -> ShowMonthGrid(scope, currentPeriod, screenWidthDp)
        is EventsScope.Week -> ShowWeekGrid(scope, currentPeriod, screenWidthDp)
        is EventsScope.Day -> ShowDayGrid(scope, currentPeriod, screenWidthDp)
    }
}

@Composable
fun ShowDayGrid(scope: EventsScope.Day, currentPeriod: LocalDate, screenWidthDp: Int) {
    val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale("ru"))
    val from = scope.from.format(formatter)
    val to = scope.to.format(formatter)
    Text(text= "It's a day scope for $from date")
}

@Composable
fun ShowWeekGrid(scope: EventsScope.Week, currentPeriod: LocalDate, screenWidthDp: Int) {
    val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale("ru"))
    val from = scope.from.format(formatter)
    val to = scope.to.format(formatter)
    Text(text= "It's a week scope for $from - $to dates")
}

@Composable
fun ShowMonthGrid(scope: EventsScope.Month, currentPeriod: LocalDate, screenWidthDp: Int) {

    val formatter = DateTimeFormatter.ofPattern("yyyy, MMMM", Locale("ru"))
    val scopeStartDate = scope.from
    val scopeEndDate = scope.to
    var currentDate = LocalDate.now()
    //val state = rememberLazyGridState(1)

    Log.d("debugmes", "4 ShowMonthGrid, currentPeriod - ${currentPeriod.toString()}")

    Column() {

        Text(text = "MONTH ${scopeStartDate.format(formatter)} ")

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.width(screenWidthDp.dp),
            //state = rememberLazyGridState()
        ) {
            var formatter = DateTimeFormatter.ofPattern("E", Locale("ru"))
            var daysCounter = scope.from.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val lastDayOfScope = scope.to.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
            val weekDaysList = listOf(
                daysCounter.format(formatter),
                daysCounter.plusDays(1).format(formatter),
                daysCounter.plusDays(2).format(formatter),
                daysCounter.plusDays(3).format(formatter),
                daysCounter.plusDays(4).format(formatter),
                daysCounter.plusDays(5).format(formatter),
                daysCounter.plusDays(6).format(formatter),
            )
            items(weekDaysList) {
                Box(
                    modifier = Modifier.width((screenWidthDp / 7).dp)
                        .height((screenWidthDp / 14).dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = AnnotatedString(it),
                        color = if (weekDaysList.indexOf(it) > 4) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.onSecondary,
                        //modifier = Modifier.fillMaxSize()
                    )
                }
            }

            formatter = DateTimeFormatter.ofPattern("dd", Locale("ru"))
            var daysList: MutableList<DayOfScope> = mutableListOf()
            while (daysCounter <= lastDayOfScope) {
                val dayOfScope = DayOfScope(
                    date = daysCounter,
                    dateAsString = daysCounter.format(formatter),
                    isCurrent = currentDate == daysCounter,
                    isBusy = currentDate == daysCounter,
                    outOfRange = if(daysCounter<scopeStartDate || daysCounter>scopeEndDate) true else false,
                    isSpecial = if(daysCounter.dayOfWeek==DayOfWeek.SUNDAY || daysCounter.dayOfWeek==DayOfWeek.SATURDAY) true else false
                    )
                daysList.add(dayOfScope)
                daysCounter = daysCounter.plusDays(1)
            }
            items(daysList) {

                val dayColor = if(it.outOfRange) MaterialTheme.colorScheme.background
                                else if (it.isSpecial) MaterialTheme.colorScheme.errorContainer
                                else MaterialTheme.colorScheme.onSecondary

                val cellColor = if(it.isCurrent) MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.secondary

                Box(
                    modifier = Modifier.width((screenWidthDp / 7).dp)
                        .height((screenWidthDp / 10).dp)
                        .background(cellColor)
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = it.dateAsString,
                        color = dayColor,
                        textAlign = TextAlign.Center
                    )
                    if(it.isBusy) {
                        Box(
                            modifier = Modifier.fillMaxSize(0.7f)
                                //.background(MaterialTheme.colorScheme.primaryContainer)
                            ,
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Text(
                                text = ".",
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.errorContainer,
                                modifier = Modifier.fillMaxSize(),
                                fontSize = 5.em,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ShowYearGrid(scope: EventsScope.Year, currentPeriod: LocalDate, screenWidthDp: Int) {
    val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", Locale("ru"))
    val from = scope.from.format(formatter)
    val to = scope.to.format(formatter)
    Text(text= "It's a year scope for $from - $to dates")
}

data class DayOfScope(
    val date:LocalDate,
    val dateAsString:String,
    val isSpecial:Boolean,
    val isCurrent:Boolean,
    val isBusy:Boolean,
    val outOfRange:Boolean)
