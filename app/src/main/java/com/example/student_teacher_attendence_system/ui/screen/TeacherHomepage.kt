package com.example.student_teacher_attendence_system.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.student_teacher_attendence_system.data.model.TeacherModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel

@Composable
fun TeacherHomepage(
    viewModel: TeacherViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TeacherTopBar(
                title = "Teacher Panel",
                showBack = false
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Text("Enter Your Email:")
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("xyz@gmail.com") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text("Enter Your Name:")
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                placeholder = { Text("John Doe") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    if (email.isBlank() || name.isBlank()) {
                        showError = true
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        viewModel.checkAndAddTeacher(
                            TeacherModel(email = email, name = name),
                            onExists = {
                                Toast.makeText(context, "Email already exists so we logging in", Toast.LENGTH_SHORT).show()
                                viewModel.setEmail(email)
                                navController.navigate("display-teacher-class")
                            },
                            onSuccess = {
                                Toast.makeText(context, "Teacher created", Toast.LENGTH_SHORT).show()
                                navController.navigate("display-teacher-class")
                            })
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Create Teacher")
            }
        }
    }
}

