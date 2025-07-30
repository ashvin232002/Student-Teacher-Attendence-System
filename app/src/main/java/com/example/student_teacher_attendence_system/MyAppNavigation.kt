package com.example.student_teacher_attendence_system

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_teacher_attendence_system.ui.screen.ClassDetailsScreen
import com.example.student_teacher_attendence_system.ui.screen.TeacherHomepage
import com.example.student_teacher_attendence_system.ui.screen.UpdateAttendence
import com.example.student_teacher_attendence_system.viewModel.AttendenceViewModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(), // default here
) {
    val teacherViewModel: TeacherViewModel = hiltViewModel()
    val attendenceViewModel: AttendenceViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "create-teacher") {
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
                teacherViewModel = teacherViewModel,
                navController = navController,
                className = className,
                teacherEmail = email
            )
        }
    }
}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MyAppNavigation(
//    modifier: Modifier,
//    navController: NavHostController,
//    teacherViewModel: TeacherViewModel,
//    attendenceViewModel: AttendenceViewModel
//) {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "create-teacher") {
//        composable("create-teacher") {
//            TeacherHomepage(teacherViewModel,modifier,navController)
//        }
//        composable("display-teacher-class") {
//            ClassDetailsScreen(modifier,teacherViewModel,navController)
//        }
//        composable("update-attendence/{className}/{email}") { backStackEntry ->
//            val className = backStackEntry.arguments?.getString("className") ?: ""
//            val email = Uri.decode(backStackEntry.arguments?.getString("email") ?: "")
//
//            UpdateAttendence(
//                modifier = modifier,
//                attendenceViewModel = attendenceViewModel,
//                teacherViewModel = teacherViewModel,
//                navController = navController,
//                className = className,
//                teacherEmail = email
//            )
//        }
//
//
//    }
//}
