package com.example.student_teacher_attendence_system.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.student_teacher_attendence_system.viewModel.AdminViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminDashboard(viewModel: AdminViewModel) {
    val attendance by viewModel.attendance.collectAsState()
    val classes by viewModel.classes.collectAsState()
    val attendanceRecords by viewModel.attendanceRecords.collectAsState()

    val today = remember { LocalDate.now() }
    val defaultDate = today.dayOfMonth.toString()
    val defaultMonth = today.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    val defaultYear = today.year.toString()
    val defaultFormattedDate = "$defaultDate $defaultMonth,$defaultYear"

    var date by rememberSaveable { mutableStateOf(defaultDate) }
    var month by rememberSaveable { mutableStateOf(defaultMonth) }
    var year by rememberSaveable { mutableStateOf(defaultYear) }
    var currentDate by rememberSaveable { mutableStateOf(defaultFormattedDate) }

    var showDateMenu by rememberSaveable { mutableStateOf(false) }
    var showMonthMenu by rememberSaveable { mutableStateOf(false) }
    var showYearMenu by rememberSaveable { mutableStateOf(false) }

    val dateList = listOf("27", "28", "29", "30")
    val monthList = listOf("June", "July", "August")
    val yearList = listOf("2024", "2025")

    LaunchedEffect(currentDate) {
        viewModel.listenToClassesAndUpdateAttendance(currentDate)
        viewModel.listenToAttendance(currentDate)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📊 Admin Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Total Classes: ${classes.size}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        dropdownSelector("Date", date, dateList, showDateMenu) {
                            date = it
                            showDateMenu = false
                        }.also { showDateMenu = it }

                        dropdownSelector("Month", month, monthList, showMonthMenu) {
                            month = it
                            showMonthMenu = false
                        }.also { showMonthMenu = it }

                        dropdownSelector("Year", year, yearList, showYearMenu) {
                            year = it
                            showYearMenu = false
                        }.also { showYearMenu = it }
                    }

                    Button(
                        onClick = {
                            currentDate = "$date $month,$year"
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("📅 Submit", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            if (attendanceRecords.isEmpty()) {
                Text(
                    text = "😐 No attendance records found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 32.dp)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    items(attendanceRecords) { attendance ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            elevation = CardDefaults.cardElevation(4.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("📅 Day: ${attendance.day}", style = MaterialTheme.typography.titleMedium)
                                Spacer(Modifier.height(4.dp))
                                Text("✅ Present: ${attendance.presence_count}", style = MaterialTheme.typography.bodyMedium)
                                Text("👥 Total Strength: ${attendance.class_strength}", style = MaterialTheme.typography.bodyMedium)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "🆔 Class ID: ${attendance.class_ref}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun dropdownSelector(
    label: String,
    selected: String,
    options: List<String>,
    expanded: Boolean,
    onItemSelected: (String) -> Unit
): Boolean {
    var isExpanded by remember { mutableStateOf(expanded) }

    Box {
        OutlinedButton(onClick = { isExpanded = true }) {
            Text("$label: $selected")
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onItemSelected(it)
                        isExpanded = false
                    }
                )
            }
        }
    }

    return isExpanded
}