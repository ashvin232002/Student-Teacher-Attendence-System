package com.example.student_teacher_attendence_system.data.repository

import com.example.student_teacher_attendence_system.data.firebase.FirebaseService

import com.example.student_teacher_attendence_system.data.model.ClassModel
import kotlinx.coroutines.tasks.await

interface ClassRepository {
    fun listenToClasses(onChange: (List<ClassModel>) -> Unit, onError: (Exception) -> Unit)
    suspend fun addClass(obj: ClassModel): Boolean
}

