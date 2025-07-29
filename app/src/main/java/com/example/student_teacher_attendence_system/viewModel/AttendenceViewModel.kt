package com.example.student_teacher_attendence_system.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.data.repository.AttendenceRepository
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttendenceViewModel: ViewModel() {
    private val repository = AttendenceRepository()

    private val _attendence = MutableStateFlow<List<AttendanceModel>>(emptyList())
    val attendence = _attendence.asStateFlow()

    fun addAttendence(attendence: AttendanceModel,className: String , teacherEmail: String){
        viewModelScope.launch {
            repository.addAttendance(attendence, className , teacherEmail )
        }
    }


//    fun fetchAttendece(classRef:DocumentReference){
//        viewModelScope.launch {
//            _attendence.value = repository.getAttendenceByClass(classRef);
//        }
//    }
//
//    fun updateAttendece(attendence: AttendanceModel){
//        viewModelScope.launch {
//            repository.updateAttendece(attendence)
//            attendence.class_ref?.let { fetchAttendece(it) }
//        }
//    }
//
//    fun deleteAttendece(attendenceId:String,classRef: DocumentReference){
//        viewModelScope.launch {
//            repository.deleteAttendece(attendenceId)
//            fetchAttendece(classRef)
//        }
//    }
}