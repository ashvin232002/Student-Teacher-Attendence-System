package com.example.student_teacher_attendence_system

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_teacher_attendence_system.ui.screen.AddClassPage
import com.example.student_teacher_attendence_system.ui.screen.AdminDashboard
import com.example.student_teacher_attendence_system.ui.screen.AdminPage
import com.example.student_teacher_attendence_system.ui.screen.ClassDetailsScreen
import com.example.student_teacher_attendence_system.ui.screen.ModeSelectionScreen
import com.example.student_teacher_attendence_system.ui.screen.TeacherHomepage
import com.example.student_teacher_attendence_system.ui.screen.UpdateAttendence
import com.example.student_teacher_attendence_system.viewModel.AdminViewModel
import com.example.student_teacher_attendence_system.viewModel.AttendenceViewModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    modifier: Modifier,
    navController: NavHostController,
    teacherViewModel: TeacherViewModel,
    attendenceViewModel: AttendenceViewModel,
    adminViewModel: AdminViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "mode-selection"
    ) {

        composable("mode-selection") {
            ModeSelectionScreen(
                modifier = modifier,
                navController = navController
            )
        }

        // Teacher Mode Routes
        composable("create-teacher") {
            TeacherHomepage(teacherViewModel, modifier, navController)
        }
        composable("display-teacher-class") {
            ClassDetailsScreen(modifier, teacherViewModel, navController)
        }
        composable("update-attendence/{className}/{email}") { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: ""
            val email = Uri.decode(backStackEntry.arguments?.getString("email") ?: "")

            UpdateAttendence(
                modifier = modifier,
                attendenceViewModel = attendenceViewModel,
                navController = navController,
                className = className,
                teacherEmail = email
            )
        }

        // Admin Mode Routes
        composable("adminHomePage") {
            AdminPage(navController)
        }
        composable("adminHomePage/adminDashboard") {
            AdminDashboard(adminViewModel)
        }
        composable(route = "adminHomePage/addClass") {
            AddClassPage()
        }
    }
}