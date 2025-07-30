package com.example.student_teacher_attendence_system

import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi

import com.example.student_teacher_attendence_system.ui.theme.StudentTeacherAttendenceSystemTheme
import com.example.student_teacher_attendence_system.viewModel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentTeacherAttendenceSystemTheme {
                NavigationAdminPage( )
            }
        }
    }
}



