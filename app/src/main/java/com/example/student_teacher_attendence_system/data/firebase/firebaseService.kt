package com.example.student_teacher_attendence_system.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor() {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val teachersCollection get() = firestore.collection("teacher")
    val classesCollection get() = firestore.collection("class")
    val attendanceCollection get() = firestore.collection("attendance")
}
