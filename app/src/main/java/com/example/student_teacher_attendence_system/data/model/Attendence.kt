package com.example.student_teacher_attendence_system.data.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date


data class AttendanceModel(
    val id: String = "",
    val class_ref: DocumentReference? = null,
    val teacher_ref: DocumentReference? = null,
    val day: Date = Date(),
    val presence_count: Int = 0
)