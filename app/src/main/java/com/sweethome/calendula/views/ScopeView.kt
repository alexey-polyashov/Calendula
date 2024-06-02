package com.sweethome.calendula.views

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.sweethome.calendula.datalayer.AppStateData
import com.sweethome.calendula.models.CalendulaLayout
import com.sweethome.calendula.models.EventsScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    appState: AppStateData,
    showScope: Boolean,
    currentPeriod: String,
    currentLayout: CalendulaLayout,
    refresh: () -> Unit
) {

    val config = LocalConfiguration.current
    val screenWidthDp = config.screenWidthDp
    val screenWidth = with(LocalDensity.current) { screenWidthDp.dp.roundToPx() }

    val listState = rememberScrollState(screenWidth)

    val prevPeriod = appState.eventScope.clone()
    val nextPeriod = appState.eventScope.clone()

    prevPeriod.currentPeriod = appState.eventScope.getPrevPeriod()
    nextPeriod.currentPeriod = appState.eventScope.getNextPeriod()

    val listOfPeriod: List<EventsScope> = listOf(prevPeriod, appState.eventScope, nextPeriod)


    if(showScope){
            Row(
                modifier = Modifier.horizontalScroll(state=listState),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Card(){
                    var it = listOfPeriod[0]
                    DrawScopeGrid(it, it.from, appState, screenWidthDp)
                }
                Card(){
                    var it = listOfPeriod[1]
                    DrawScopeGrid(it, it.from, appState, screenWidthDp)
                }
                Card(){
                    var it = listOfPeriod[2]
                    DrawScopeGrid(it, it.from, appState, screenWidthDp)
                }

        }
    }

    LaunchedEffect(listState.isScrollInProgress){

        if(!listState.isScrollInProgress) {
            val isFirstItemScrolled = isFirstElementSemiShown(listState, screenWidth)
            val isLastItemScrolled = isLastElementSemiShown(listState, screenWidth)
            if (isFirstItemScrolled) {
                AppController.getPrevPeriod(appState)
                refresh()
                scrollToCurrent(listState, screenWidth)
            }
            if (isLastItemScrolled) {
                AppController.getNextPeriod(appState)
                refresh()
                scrollToCurrent(listState, screenWidth)
            }
            scrollToCurrent(listState, screenWidth)
        }

    }

}

suspend fun scrollToCurrent(listState:ScrollState, screenWidth: Int){
    listState.scrollTo(screenWidth)
}


fun isFirstElementSemiShown(ls: ScrollState, screenWidth:Int): Boolean {

    var result:Boolean = false

    val scrollPosition = ls.value

    result = if(abs(scrollPosition) < screenWidth/2) true else false

    return result
}

fun isLastElementSemiShown(ls:ScrollState, screenWidth:Int): Boolean {

    var result:Boolean = false

    val scrollPosition = ls.value

    result = if(abs(scrollPosition) > screenWidth*1.5) true else false

    return result

}

@Composable
fun DrawScopeGrid(scope: EventsScope, currentPeriod: LocalDate, appState: AppStateData, screenWidthDp: Int){
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
    Text(text= currentPeriod.format(formatter), modifier = Modifier.width(screenWidthDp.dp).padding(10.dp, 0.dp, 0.dp, 0.dp))
}

@Composable
fun ShowWeekGrid(scope: EventsScope.Week, currentPeriod: LocalDate, screenWidthDp: Int) {

    var formatter = DateTimeFormatter.ofPattern("MMMM-yyyy", Locale("ru"))

    Column() {

        Text(text = currentPeriod.format(formatter) , modifier = Modifier.width(screenWidthDp.dp).padding(10.dp, 0.dp, 0.dp, 0.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.width(screenWidthDp.dp)
                .background(MaterialTheme.colorScheme.secondary),
        ) {

            formatter = DateTimeFormatter.ofPattern("E", Locale("ru"))
            var daysCounter = scope.from
            val currentDay = LocalDate.now()
            val lastDayOfScope = scope.to
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
                    )
                }
            }

            formatter = DateTimeFormatter.ofPattern("dd", Locale("ru"))
            val daysList: MutableList<CheapOfScope> = mutableListOf()
            while (daysCounter <= lastDayOfScope) {
                val dayOfScope = CheapOfScope(
                    date = daysCounter,
                    dateAsString = daysCounter.format(formatter),
                    isCurrent = daysCounter == currentDay,
                    isBusy = daysCounter == currentDay,
                    isSpecial = if(daysCounter.dayOfWeek==DayOfWeek.SUNDAY || daysCounter.dayOfWeek==DayOfWeek.SATURDAY) true else false
                )
                daysList.add(dayOfScope)
                daysCounter = daysCounter.plusDays(1)
            }
            items(daysList) {

                val dayColor = if (it.isSpecial) MaterialTheme.colorScheme.errorContainer
                                else MaterialTheme.colorScheme.onSecondary

                val cellColor = if(it.isCurrent) MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.secondary

                val mod = if(it.isCurrent) {
                    Modifier.width((screenWidthDp / 7).dp)
                        .height((screenWidthDp / 10).dp)
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(7.dp)
                        )
                        .background(cellColor)
                }
                else {
                    Modifier.width((screenWidthDp / 7).dp)
                        .height((screenWidthDp / 10).dp)
                        .background(cellColor)
                }

                Box(
                    modifier = mod,
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
fun ShowMonthGrid(scope: EventsScope.Month, currentPeriod: LocalDate, screenWidthDp: Int) {

    var formatter = DateTimeFormatter.ofPattern("MMMM", Locale("ru"))
    val scopeStartDate = scope.from
    val scopeEndDate = scope.to
    var currentDate = LocalDate.now()

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)) {

        Text(text = transformNameOfMonth(currentPeriod.format(formatter)) , modifier = Modifier.width(screenWidthDp.dp).padding(10.dp, 0.dp, 0.dp, 0.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.width(screenWidthDp.dp)
                .background(MaterialTheme.colorScheme.secondary),
            //state = rememberLazyGridState()
        ) {
            formatter = DateTimeFormatter.ofPattern("E", Locale("ru"))
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
                    )
                }
            }

            formatter = DateTimeFormatter.ofPattern("dd", Locale("ru"))
            val daysList: MutableList<CheapOfScope> = mutableListOf()
            while (daysCounter <= lastDayOfScope) {
                val dayOfScope = CheapOfScope(
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

                val dayColor = if(it.outOfRange) MaterialTheme.colorScheme.outline
                                else if (it.isSpecial) MaterialTheme.colorScheme.errorContainer
                                else MaterialTheme.colorScheme.onSecondary

                val cellColor = if(it.isCurrent) MaterialTheme.colorScheme.secondaryContainer
                                else MaterialTheme.colorScheme.secondary

                val mod = if(it.isCurrent) {
                        Modifier.width((screenWidthDp / 7).dp)
                            .height((screenWidthDp / 10).dp)
                            .shadow(
                                elevation = 5.dp,
                                shape = RoundedCornerShape(7.dp)
                            )
                            .background(cellColor)
                    }
                    else {
                        Modifier.width((screenWidthDp / 7).dp)
                            .height((screenWidthDp / 10).dp)
                            .background(cellColor)
                    }

                Box(
                    modifier = mod,
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

    var formatter = DateTimeFormatter.ofPattern("yyyy", Locale("ru"))

    Column() {

        Text(text = currentPeriod.format(formatter) , modifier = Modifier.width(screenWidthDp.dp).padding(10.dp, 0.dp, 0.dp, 0.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.width(screenWidthDp.dp)
                .background(MaterialTheme.colorScheme.secondary),
        ) {
            formatter = DateTimeFormatter.ofPattern("MMMM", Locale("ru"))
            var daysCounter = scope.from
            var currentMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())
            val lastDayOfScope = scope.to

            formatter = DateTimeFormatter.ofPattern("MMMM", Locale("ru"))
            var monthsList: MutableList<CheapOfScope> = mutableListOf()
            while (daysCounter <= lastDayOfScope) {
                val dayOfScope = CheapOfScope(
                    date = daysCounter,
                    startDate = daysCounter.with(TemporalAdjusters.firstDayOfMonth()),
                    endDate = daysCounter.with(TemporalAdjusters.lastDayOfMonth()),
                    dateAsString = transformNameOfMonth(daysCounter.format(formatter)),
                    isCurrent = daysCounter.with(TemporalAdjusters.firstDayOfMonth()) == currentMonth,
                    isBusy = daysCounter.with(TemporalAdjusters.firstDayOfMonth()) == currentMonth,
                    isSpecial = false
                )
                monthsList.add(dayOfScope)
                daysCounter = daysCounter.plusMonths(1)
            }
            items(monthsList) {

                val monthColor = MaterialTheme.colorScheme.onSecondary

                val cellColor = if(it.isCurrent) MaterialTheme.colorScheme.secondaryContainer
                else MaterialTheme.colorScheme.secondary

                val mod = if(it.isCurrent) {
                    Modifier.width((screenWidthDp / 7).dp)
                        .height((screenWidthDp / 10).dp)
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(7.dp)
                        )
                        .background(cellColor)
                }
                else {
                    Modifier.width((screenWidthDp / 7).dp)
                        .height((screenWidthDp / 10).dp)
                        .background(cellColor)
                }

                Box(
                    modifier = mod,
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = it.dateAsString,
                        color = monthColor,
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

data class CheapOfScope(
    val date:LocalDate,
    val startDate:LocalDate = date,
    val endDate:LocalDate = date,
    val dateAsString:String,
    val isSpecial:Boolean,
    val isCurrent:Boolean,
    val isBusy:Boolean,
    val outOfRange:Boolean = false)

fun transformNameOfMonth(month:String):String{

    var result:String

    val transformMap: Map<String, String> = mapOf(
        Pair("января",      "Январь"),
        Pair("февраля",     "Февраль"),
        Pair("марта",       "Март"),
        Pair("апреля",      "Апрель"),
        Pair("мая",         "Май"),
        Pair("июня",        "Июнь"),
        Pair("июля",        "Июль"),
        Pair("августа",     "Август"),
        Pair("сентября",    "Сентябрь"),
        Pair("октября",     "Октябрь"),
        Pair("ноября",      "Ноябрь"),
        Pair("декабря",     "Декабрь")
    )

    result = transformMap.get(month.lowercase())?:month

    return result

}
