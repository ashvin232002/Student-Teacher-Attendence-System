package com.example.student_teacher_attendence_system

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_teacher_attendence_system.ui.screen.AddClassPage
import com.example.student_teacher_attendence_system.ui.screen.AdminDashboard
import com.example.student_teacher_attendence_system.ui.screen.AdminPage
import com.example.student_teacher_attendence_system.viewModel.AdminViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationAdminPage(){
    val navController = rememberNavController()

    NavHost(navController, startDestination = "adminHomePage") {
        composable("adminHomePage"){
            AdminPage(navController)
        }
        composable("adminHomePage/adminDashboard"){
            val viewModel: AdminViewModel = hiltViewModel()
            AdminDashboard(viewModel)
        }
        composable(route="adminHomePage/addClass"){
            AddClassPage()
        }
    }
}