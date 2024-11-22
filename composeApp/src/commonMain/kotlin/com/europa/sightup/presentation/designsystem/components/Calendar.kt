package com.europa.sightup.presentation.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.europa.sightup.presentation.ui.theme.SightUPTheme
import com.europa.sightup.presentation.ui.theme.typography.textStyles
import com.europa.sightup.utils.clickableWithRipple
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.painterResource
import sightupkmpapp.composeapp.generated.resources.Res
import sightupkmpapp.composeapp.generated.resources.arrow_left
import sightupkmpapp.composeapp.generated.resources.arrow_right

@Composable
fun SDSCalendarDay(
    onDateSelected: (day: Int, month: String, year: Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Calendar(
            onClickCalendar = { day, month, year ->
                println(day)
                println(month)
                println(year)
                onDateSelected(day.toInt(), month, year.toInt())
            }
        )
    }
}


@Composable
fun Calendar(
    onClickCalendar: (String, String, String) -> Unit,
) {
    var currentMonth by remember { mutableStateOf(Clock.System.todayIn(TimeZone.currentSystemDefault())) }
    val daysList = remember { mutableListOf<String>() }
    var activeToday: Boolean by remember { mutableStateOf(true) }
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var dayCounter = 1
    var _currentMonth = currentMonth.monthNumber
    var firstDayOfMonth = currentMonth.minus(DatePeriod(days = currentMonth.dayOfMonth.toInt() - dayCounter))
    var emptyDays = 1;

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                currentMonth.month.toString().lowercase().replaceFirstChar { it.uppercase() } +
                        " " +
                        currentMonth.year.toString(),
                style = SightUPTheme.textStyles.subtitle
            )

            Row {
                Image(
                    painter = painterResource(Res.drawable.arrow_left),
                    contentDescription = "Voltar mês",
                    modifier = Modifier
                        .clickable {
                            currentMonth = currentMonth.minus(DatePeriod(months = 1))
                            daysList.clear()
                        }
                        .padding(12.dp)
                        .size(24.dp)
                )

                Spacer(Modifier.width(20.dp))

                Image(
                    painter = painterResource(Res.drawable.arrow_right),
                    contentDescription = "Avançar mês",
                    modifier = Modifier
                        .clickable {
                            currentMonth = currentMonth.plus(DatePeriod(months = 1))
                            daysList.clear()
                        }
                        .padding(12.dp)
                        .size(24.dp)
                )
            }
        }

        Spacer(Modifier.height(21.dp))

        when (today.month) {
            Month.JANUARY -> check(today.month.number == 1)
            Month.FEBRUARY -> check(today.month.number == 2)
            Month.MARCH -> check(today.month.number == 3)
            Month.APRIL -> check(today.month.number == 4)
            Month.MAY -> check(today.month.number == 5)
            Month.JUNE -> check(today.month.number == 6)
            Month.JULY -> check(today.month.number == 7)
            Month.AUGUST -> check(today.month.number == 8)
            Month.SEPTEMBER -> check(today.month.number == 9)
            Month.OCTOBER -> check(today.month.number == 10)
            Month.NOVEMBER -> check(today.month.number == 11)
            Month.DECEMBER -> check(today.month.number == 12)
            else -> ""
        }

        Column {


            when (firstDayOfMonth.dayOfWeek.toString()) {
                "SUNDAY" -> {
                    emptyDays = 1
                }

                "MONDAY" -> {
                    emptyDays = 2
                }

                "TUESDAY" -> {
                    emptyDays = 3
                }

                "WEDNESDAY" -> {
                    emptyDays = 4
                }

                "THURSDAY" -> {
                    emptyDays = 5
                }

                "FRIDAY" -> {
                    emptyDays = 6
                }

                "SATURDAY" -> {
                    emptyDays = 7
                }

                else -> {
                    Text("Unknown Day")
                }
            }

            for (i in 1..emptyDays - 1) {
                daysList.add("")
            }

            while (dayCounter < currentMonth.dayOfMonth.toInt()) {
                var weekDay = currentMonth.minus(DatePeriod(days = currentMonth.dayOfMonth.toInt() - dayCounter))
                daysList.add(weekDay.dayOfMonth.toString())
                dayCounter++
            }

            dayCounter = 0
            do {
                var weekDay = currentMonth.plus(DatePeriod(days = dayCounter))
                _currentMonth = weekDay.monthNumber
                if (_currentMonth == currentMonth.monthNumber) {
                    daysList.add(weekDay.dayOfMonth.toString())
                }
                ++dayCounter
            } while (currentMonth.monthNumber == _currentMonth)

            val weekName = listOf(
                "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"
            )

            Column {

                Row {
                    weekName.forEach { weekDays ->
                        Text(
                            weekDays,
                            modifier = Modifier.weight((1f)),
                            textAlign = TextAlign.Center,
                            color = SightUPTheme.sightUPColors.text_tertiary,
                            style = SightUPTheme.textStyles.body2
                        )
                    }
                }

                val clickedStates = remember { mutableStateMapOf<String, Boolean>() }

                daysList.chunked(7).forEach { weekDays ->
                    Row {
                        weekDays.forEach { day ->
                            // Aqui, usamos o estado do mapa global diretamente
                            val isClicked = clickedStates[day] ?: false // Define o estado do dia diretamente

                            Text(
                                text = day,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(vertical = 0.dp)
                                    .padding(horizontal = 0.dp)
                                    .let { modifier ->
                                        if (activeToday) {
                                            if (currentMonth.dayOfMonth.toString() == day) {
                                                modifier.background(SightUPTheme.sightUPColors.primary_200, CircleShape)
                                            } else {
                                                modifier
                                            }
                                        } else {
                                            modifier
                                        }
                                    }
                                    .background(
                                        if (isClicked) SightUPTheme.sightUPColors.primary_200 else Color.Transparent, // Muda o fundo ao clicar
                                        shape = CircleShape
                                    )
                                    .clip(CircleShape)
                                    .clickableWithRipple {
                                        clickedStates.keys.forEach { day ->
                                            clickedStates[day] = false
                                        }
                                        activeToday = false
                                        clickedStates[day] = !isClicked
                                        daysList.clear()
                                        println(day.toString() + "-" + currentMonth.month.toString() + "-" + currentMonth.year.toString())
                                        onClickCalendar(day, currentMonth.month.toString(), currentMonth.year.toString())
                                    }
                                    .padding(vertical = 10.dp)
                                    .padding(horizontal = 10.dp),
                                style = SightUPTheme.textStyles.subtitle.copy(
                                    color = if (currentMonth.dayOfMonth.toString() == day) Color(0xFF235E86) else Color.Unspecified
                                )
                            )
                        }
                        // Preenche os espaços vazios da semana
                        repeat(7 - weekDays.size) {
                            Text("", modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
            }

        }

    }
}
