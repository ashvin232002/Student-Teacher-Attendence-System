package com.example.student_teacher_attendence_system.data.model

import com.google.firebase.firestore.DocumentReference

data class ClassModel(
    val id: String = "",
    val name: String = "",
    val total_strength: Int = 0,
)
