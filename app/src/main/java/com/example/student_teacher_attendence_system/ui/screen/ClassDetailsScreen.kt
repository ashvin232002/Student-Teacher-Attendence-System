package com.example.student_teacher_attendence_system.ui.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.student_teacher_attendence_system.data.model.ClassModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel
import kotlin.reflect.KProperty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDetailsScreen(
    modifier: Modifier,
    viewModel: TeacherViewModel,
    navController: NavController
) {

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    val classes by viewModel.classes.collectAsState();
    val teacherEmail by viewModel.email.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchClassesRealtime()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                readOnly = true, value = selectedText, onValueChange = {
                selectedText = it
            },
                label = { Text("Classes") }, trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }, modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                classes.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = item.name
                            expanded = false
                            navController.navigate("update-attendence/${selectedText}/${Uri.encode(teacherEmail)}")

                        },
                        text = { Text(item.name) }
                    )
                }
            }
        }
    }
}


