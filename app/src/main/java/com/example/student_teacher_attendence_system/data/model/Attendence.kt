package com.example.student_teacher_attendence_system.data.model

import java.util.Date


data class AttendanceModel(
    val id: String = "",
    val class_id: String = "",
    val teacher_id: String = "",
    val day: Date = Date(),
    val presence_count: Int = 0
)