package com.example.student_teacher_attendence_system.data.model

data class Attendance(
    val id: String = "",
    val date: String = "",
    val subject: String = "",
    val teacherRef: String = "",
    val classRef: String = "",
    val present: Map<String, Boolean> = emptyMap()
)
