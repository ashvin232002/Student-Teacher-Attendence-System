package com.example.student_teacher_attendence_system.data.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date


data class AttendanceModel(
    val id: String = "",
    val class_ref: String = "",
    val teacher_ref: String = "",
    val day:Int=0,
    val presence_count: Int = 0,
    val class_strength:Int=0
)