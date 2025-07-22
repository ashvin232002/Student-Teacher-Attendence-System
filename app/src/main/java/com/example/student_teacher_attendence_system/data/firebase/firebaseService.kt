package com.example.student_teacher_attendence_system.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

object FirebaseService {
    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val teachersCollection get() = firestore.collection("teachers")
    val classesCollection get() = firestore.collection("classes")
    val attendanceCollection get() = firestore.collection("attendance")
}
