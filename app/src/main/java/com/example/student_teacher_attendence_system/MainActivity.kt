package com.example.student_teacher_attendence_system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.student_teacher_attendence_system.ui.theme.StudentTeacherAttendenceSystemTheme
import com.example.student_teacher_attendence_system.viewModel.AttendenceViewModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel


class MainActivity : ComponentActivity() {
    private val teacherViewModel by viewModels<TeacherViewModel>()
    private val attendenceViewModel by viewModels<AttendenceViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentTeacherAttendenceSystemTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyAppNavigation(
                        modifier = Modifier,
                        teacherViewModel = teacherViewModel,
                        navController = navController,
                        attendenceViewModel = attendenceViewModel
                    )
                }
            }
        }
    }
}

