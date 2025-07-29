package com.example.student_teacher_attendence_system.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_teacher_attendence_system.data.repository.AttendanceRepository
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AttendanceViewModel: ViewModel() {
    private val attendanceRepository = AttendanceRepository()

    private val _attendance = MutableStateFlow<List<AttendanceModel>>(emptyList())

    val attendance=_attendance.asStateFlow()

    fun getAttendance(date:Int){
        viewModelScope.launch {
            _attendance.value = attendanceRepository.getAttendance(date)
        }
    }


}