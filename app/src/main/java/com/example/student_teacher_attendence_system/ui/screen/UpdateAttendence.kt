package com.example.student_teacher_attendence_system.ui.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.viewModel.AttendenceViewModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAttendence(
    modifier: Modifier = Modifier,
    attendenceViewModel: AttendenceViewModel,
    teacherViewModel: TeacherViewModel,
    navController: NavController,
    className: String,
    teacherEmail: String
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        TeacherTopBar(
            title = "Add Attendence",
            showBack = true,
            onBackClick = { navController.popBackStack() }
        )

        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var presentCount by rememberSaveable { mutableStateOf("") }

            var day by rememberSaveable { mutableStateOf("") }
            val context = LocalContext.current

            val months = listOf(
                "January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"
            )
            val days = (1..31).map { it.toString() }
            val years = (1990..2025).map { it.toString() }

            var selectedMonth by remember { mutableStateOf(months[4]) }
            var selectedDay by remember { mutableStateOf(days[0]) }
            var selectedYear by remember { mutableStateOf(years[0]) }

            val classList by teacherViewModel.classes.collectAsState()
            val currentClass = classList.find { it.name == className }
            val classStrength = currentClass?.total_strength ?: 0


            Text("class Name : ${className}")

            OutlinedTextField(
                value = presentCount,
                onValueChange = { input ->
                    if (input.all { it.isDigit() }) {
                        presentCount = input
                    }
                },
                label = { Text(text = "Enter Presence Count") },
                placeholder = { Text("0") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                CustomDropdownMenu(
                    label = "Month",
                    options = months,
                    selectedOption = selectedMonth,
                    onOptionSelected = { selectedMonth = it },
                    modifier = Modifier.weight(1f)
                )

                CustomDropdownMenu(
                    label = "Day",
                    options = days,
                    selectedOption = selectedDay,
                    onOptionSelected = { selectedDay = it },
                    modifier = Modifier.weight(1f)
                )

                CustomDropdownMenu(
                    label = "Year",
                    options = years,
                    selectedOption = selectedYear,
                    onOptionSelected = { selectedYear = it },
                    modifier = Modifier.weight(1f)
                )
            }


            Button(
                onClick = {
                    if(presentCount.isBlank()){
                        Toast.makeText(context, "Please enter present count", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val presentCountInt = presentCount.toIntOrNull()
                    if (presentCountInt == null || presentCountInt < 0) {
                        Toast.makeText(context, "Please enter a valid present count", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (presentCountInt > classStrength) {
                        Toast.makeText(context, "Present count cannot exceed class strength ($classStrength)", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val dateString = formatDateString(selectedDay, selectedMonth, selectedYear)

                    Log.d("AttendanceDate", "Selected Date: $dateString")

                    val epochMillis = convertDateToEpoch(dateString)
                    Log.d("EpochTiming", epochMillis.toString())

                    attendenceViewModel.addAttendence(
                        AttendanceModel(
                            presence_count = presentCount.toInt(),
                            day = epochMillis
                        ),
                        className = className,
                        teacherEmail = teacherEmail
                    )
                    Toast.makeText(context, "Attendence SuccessFully Added", Toast.LENGTH_SHORT)
                        .show()
                    presentCount = ""
                    day = ""
                },
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = true,
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(text = "Add Attendece For a class")
            }
        }
    }
}

fun formatDateString(day: String, month: String, year: String): String {
    return "$day $month,$year"
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertDateToEpoch(dateStr: String): Long {
    val formatter = DateTimeFormatter.ofPattern("d MMMM,yyyy", java.util.Locale.ENGLISH)
    val localDate = LocalDate.parse(dateStr, formatter)
    return localDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
}


//convert Date
//val monthIndex = months.indexOf(selectedMonth) + 1
//
//// Create LocalDate
//val localDate = LocalDate.of(
//    selectedYear.toInt(),
//    monthIndex,
//    selectedDay.toInt()
//)
//
//// Convert to epoch milliseconds with correct timezone
//val epochMillis = localDate
//    .atStartOfDay(ZoneOffset.UTC) // Fixed timezone name
//    .toInstant()
//    .toEpochMilli()

//                Log.d("Timing", "Date: $selectedDay/$monthIndex/${selectedYear}")
//                Log.d("Timing", "Epoch: $epochMillis")