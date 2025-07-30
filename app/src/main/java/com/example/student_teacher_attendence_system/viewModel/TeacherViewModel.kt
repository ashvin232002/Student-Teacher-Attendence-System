package com.example.student_teacher_attendence_system.viewModel

import android.util.Log
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.student_teacher_attendence_system.data.model.ClassModel
import com.example.student_teacher_attendence_system.data.model.TeacherModel
import com.example.student_teacher_attendence_system.data.repository.TeacherRepository
import com.example.student_teacher_attendence_system.ui.screen.TeacherHomepage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TeacherViewModel @Inject constructor(
    private val repository: TeacherRepository
): ViewModel(){
    private val _teachers = MutableStateFlow<List<TeacherModel>>(emptyList())
    val teachers = _teachers.asStateFlow()

    private val _classes = MutableStateFlow<List<ClassModel>>(emptyList())
    val classes = _classes.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    fun setEmail(value: String) {
        _email.value = value
    }

    fun fetchClasses(){
        viewModelScope.launch {
            _classes.value = repository.getAllClasses()
        }
    }

    fun fetchClassesRealtime() {
        viewModelScope.launch {
            repository.getAllClassesRealTime().collect { updatedClasses ->
                _classes.value = updatedClasses
            }
        }
    }


//    fun fetchClasses() {
//        viewModelScope.launch {
//            repository.getAllClasses().collect { updatedClasses ->
//                _classes.value = updatedClasses
//            }
//        }
//    }

    fun fetchTeachers(){
        viewModelScope.launch {
            _teachers.value =  repository.getTeacher()
        }
    }

    fun addTeacher(teacherModel: TeacherModel){
        viewModelScope.launch {
            repository.addTeacher(teacherModel)
            fetchTeachers()
        }
    }

    fun checkAndAddTeacher(
        teacherModel: TeacherModel,
        onExists: ()-> Unit,
        onSuccess: ()->Unit
    ){
        viewModelScope.launch {
            val teachers =  repository.getTeacher()
            val emailExists = teachers.any { it.email.equals(teacherModel.email, ignoreCase = true) }
            if (emailExists) {
                onExists()
            } else {
                repository.addTeacher(teacherModel)
                fetchTeachers()
                setEmail(teacherModel.email)
                onSuccess()
            }
        }
    }

//    fun fetchClasses(){
//        viewModelScope.launch {
//            _classes.value = repository.getClasses()
//            Log.d("TeacherViewModel", "Fetched classes: ${_classes.value}")
//        }
//    }
//    fun updateClass(classModel: ClassModel){
//        viewModelScope.launch {
//            repository.updateClasses(classModel)
//            fetchClasses()
//        }
//    }
}


//class TeacherViewModel : ViewModel(){
//    private val repository = TeacherRepository()
//
//    private val _teachers = MutableStateFlow<List<TeacherModel>>(emptyList())
//    val teachers = _teachers.asStateFlow()
//
//    private val _classes = MutableStateFlow<List<ClassModel>>(emptyList())
//    val classes = _classes.asStateFlow()
//
//    private val _email = MutableStateFlow("")
//    val email = _email.asStateFlow()
//
//    fun setEmail(value: String) {
//        _email.value = value
//    }
//
//    fun fetchClasses(){
//        viewModelScope.launch {
//            _classes.value = repository.getAllClasses()
//        }
//    }
//
//    fun fetchClassesRealtime() {
//        viewModelScope.launch {
//            repository.getAllClassesRealTime().collect { updatedClasses ->
//                _classes.value = updatedClasses
//            }
//        }
//    }
//
//
////    fun fetchClasses() {
////        viewModelScope.launch {
////            repository.getAllClasses().collect { updatedClasses ->
////                _classes.value = updatedClasses
////            }
////        }
////    }
//
//    fun fetchTeachers(){
//        viewModelScope.launch {
//            _teachers.value =  repository.getTeacher()
//        }
//    }
//
//    fun addTeacher(teacherModel: TeacherModel){
//        viewModelScope.launch {
//            repository.addTeacher(teacherModel)
//            fetchTeachers()
//        }
//    }
//
//    fun checkAndAddTeacher(
//        teacherModel: TeacherModel,
//        onExists: ()-> Unit,
//        onSuccess: ()->Unit
//    ){
//        viewModelScope.launch {
//            val teachers =  repository.getTeacher()
//            val emailExists = teachers.any { it.email.equals(teacherModel.email, ignoreCase = true) }
//            if (emailExists) {
//                onExists()
//            } else {
//                repository.addTeacher(teacherModel)
//                fetchTeachers()
//                setEmail(teacherModel.email)
//                onSuccess()
//            }
//        }
//    }
//
////    fun fetchClasses(){
////        viewModelScope.launch {
////            _classes.value = repository.getClasses()
////            Log.d("TeacherViewModel", "Fetched classes: ${_classes.value}")
////        }
////    }
////    fun updateClass(classModel: ClassModel){
////        viewModelScope.launch {
////            repository.updateClasses(classModel)
////            fetchClasses()
////        }
////    }
//}