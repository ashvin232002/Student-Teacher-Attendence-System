package com.example.student_teacher_attendence_system.data.model


data class AttendanceModel(
    val id: String = "",
    val class_id: String = "",
    val teacher_id: String = "",
    val day: String = "",
    val presence_count: Int = 0
)