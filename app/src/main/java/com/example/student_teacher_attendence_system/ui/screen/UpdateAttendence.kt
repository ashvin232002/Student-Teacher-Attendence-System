package com.example.student_teacher_attendence_system.ui.screen

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.collection.mutableIntSetOf
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.data.model.TeacherModel
import com.example.student_teacher_attendence_system.viewModel.AttendenceViewModel
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZoneOffset


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAttendence(
    modifier: Modifier = Modifier,
    attendenceViewModel: AttendenceViewModel,
    navController: NavController,
    className: String,
    teacherEmail: String
) {

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
                val monthIndex = months.indexOf(selectedMonth) + 1

                // Create LocalDate
                val localDate = LocalDate.of(
                    selectedYear.toInt(),
                    monthIndex,
                    selectedDay.toInt()
                )

                // Convert to epoch milliseconds with correct timezone
                val epochMillis = localDate
                    .atStartOfDay(ZoneOffset.UTC) // Fixed timezone name
                    .toInstant()
                    .toEpochMilli()

//                Log.d("Timing", "Date: $selectedDay/$monthIndex/${selectedYear}")
//                Log.d("Timing", "Epoch: $epochMillis")

                attendenceViewModel.addAttendence(
                    AttendanceModel(
                        presence_count = presentCount.toInt(),
                        day = epochMillis
                    ),
                    className = className,
                    teacherEmail = teacherEmail
                )
                Toast.makeText(context, "Attendence SuccessFully Added", Toast.LENGTH_SHORT).show()
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