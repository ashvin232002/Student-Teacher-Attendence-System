package com.example.student_teacher_attendence_system.data.model

data class AttendanceModel(
    val id: String = "",
    val class_ref: String= "",
    val teacher_ref: String = "",
    val day: Long = 0,
    val presence_count: Int = 0
)
