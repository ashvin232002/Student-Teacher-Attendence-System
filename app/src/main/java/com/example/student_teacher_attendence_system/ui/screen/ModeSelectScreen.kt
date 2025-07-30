package com.example.student_teacher_attendence_system.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun ModeSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedMode by remember { mutableStateOf("admin") } // Default to admin mode

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Select Mode",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Admin Mode Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedMode = "admin" }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedMode == "admin",
                onClick = { selectedMode = "admin" }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Admin Mode",
                fontSize = 18.sp
            )
        }

        // Teacher Mode Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedMode = "teacher" }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedMode == "teacher",
                onClick = { selectedMode = "teacher" }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Teacher Mode",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Continue Button
        Button(
            onClick = {
                when (selectedMode) {
                    "admin" -> navController.navigate("adminHomePage")
                    "teacher" -> navController.navigate("create-teacher")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Continue")
        }
    }
}