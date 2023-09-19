package com.example.roomtodolist.ui.components

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomtodolist.R
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalTime


@Composable
fun DateCard(
    date: () -> LocalDate?,
    time: () -> LocalTime?,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit
) {
    val isCalendarVisible = remember { mutableStateOf(false) }
    val isClockVisible = remember { mutableStateOf(false) }
    MyCalendarDialog(setDate = onDateChange, time = time(), isVisible = isCalendarVisible)
    MyClockDialog(setTime = onTimeChange, date = date(), isVisible = isClockVisible)

    val dateUi = remember { mutableStateOf("--/--/----") }

    dateUi.value = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        date()?.toString() ?: "yyyy/mm/dd"
    else
        if (date() == null)
            "--/--/----"
        else
            "${date()!!.dayOfMonth}/" +
                    "${date()!!.monthValue}/" +
                    "${date()!!.year}"

    val timeUi = remember { mutableStateOf("--:--") }

    timeUi.value =

        if (time() == null)
            "--:--"
        else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                time.toString()

            else if (time()!!.hour/10 > 0 && time()!!.minute /10 >0)
                "${time()!!.hour}:${time()!!.minute}"

            else if (time()!!.hour /10 > 0)
                "${time()!!.hour}:0${time()!!.minute}"

            else if (time()!!.minute /10 > 0)
                "0${time()!!.hour}:${time()!!.minute}"

            else
                "0${time()!!.hour}:0${time()!!.minute}"
        }

    ExpandableCard(icon = R.drawable.outlined_calendar_add_icon, title = "Set Date") {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "due to: ",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(0.5f)
                )
                Text(
                    text = dateUi.value,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { isCalendarVisible.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(20f),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp)
                ) {
                    Text(
                        text = "set date",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "At: ", color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(0.5f))
                Text(
                    text = timeUi.value,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { isClockVisible.value = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(20f),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 20.dp)
                ) {
                    Text(text = "set time", fontSize = 10.sp, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCalendarDialog(
    setDate: (LocalDate) -> Unit,
    time: LocalTime?,
    isVisible: MutableState<Boolean>
) {
    val context = LocalContext.current
    CalendarDialog(
        state = UseCaseState(
            visible = isVisible.value,
            onCloseRequest = {
                isVisible.value = false
            }
        ),
        selection = CalendarSelection.Date { date ->
            isVisible.value = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (time == null) {
                    if (date.isBefore(LocalDate.now()))
                        Toast.makeText(context, "Invalid date", Toast.LENGTH_LONG).show()
                    else
                        setDate(date)
                }
                else {
                    if ((date.isEqual(LocalDate.now()) &&
                                (time.hour < LocalTime.now().hour ||
                                        (time.hour == LocalTime.now().hour &&
                                                time.minute < LocalTime.now().minute))) ||
                        date.isBefore(LocalDate.now())
                    ) {
                        Toast.makeText(context, "Invalid date", Toast.LENGTH_LONG).show()
                    } else {
                        setDate(date)
                    }
                }
            }
            else {
                setDate(date)
            }
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyClockDialog(
    setTime: (LocalTime) -> Unit,
    date: LocalDate?,
    isVisible: MutableState<Boolean>
) {
    val context = LocalContext.current
    ClockDialog(
        state = UseCaseState(visible = isVisible.value, onCloseRequest = {isVisible.value = false }),
        selection = ClockSelection.HoursMinutes { h, m ->

            isVisible.value = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val localTime = LocalTime.of(h, m)
                if (date == null) {
                    setTime(localTime)
                }
                else{
                    if (date.isEqual(LocalDate.now())){
                        if (h > LocalTime.now().hour){
                            setTime(localTime)
                        } else {
                            if (h != LocalTime.now().hour || m < LocalTime.now().minute)
                                Toast.makeText(context, "Invalid Time", Toast.LENGTH_LONG).show()
                            else{
                                setTime(localTime)
                            }
                        }
                    }
                    else if(date.isBefore(LocalDate.now())){
                        Toast.makeText(context, "Invalid date", Toast.LENGTH_LONG).show()
                    }
                    else {
                        setTime(localTime)
                    }
                }
            }
        }
    )
}