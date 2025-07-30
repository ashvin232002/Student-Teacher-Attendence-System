package com.example.student_teacher_attendence_system.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    var email = remember { mutableStateOf("") }
    var name = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text("Enter Your Email")
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter your email") },
            placeholder = { Text("xyz@gmail.com") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        )

//        Text("Enter Your Name")
        Spacer(modifier = Modifier.padding(2.dp))
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Enter your Name") },
            placeholder = { Text("XYZ") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        )

        Button(
            onClick = {
                viewModel.addTeacher(
                    TeacherModel(
                        email = email.value,
                        name = name.value
                    )
                )
                viewModel.setEmail(email.value)
                navController.navigate(route = "display-teacher-class")
            },
            modifier = Modifier
                .padding(30.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            enabled = true,
            contentPadding = PaddingValues(8.dp)
        ) {
            Text(text = "Create Teacher")
        }
    }
}

