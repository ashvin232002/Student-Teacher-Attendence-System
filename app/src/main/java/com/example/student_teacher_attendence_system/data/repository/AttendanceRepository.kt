package com.example.student_teacher_attendence_system.data.repository


import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.data.model.ClassModel


interface AttendanceRepository {
    suspend fun getAttendance(date: Int): List<AttendanceModel>
    fun listenToAttendance(day: Long, onChange: (List<AttendanceModel>) -> Unit, onError: (Exception) -> Unit)
    suspend fun getAttendanceWithClassInfo(classList: List<ClassModel>, day: Long): List<AttendanceModel>
}

