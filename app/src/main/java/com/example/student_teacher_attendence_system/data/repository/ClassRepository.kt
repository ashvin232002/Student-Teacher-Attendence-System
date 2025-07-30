package com.example.student_teacher_attendence_system.data.repository

import com.example.student_teacher_attendence_system.data.firebase.FirebaseService

import com.example.student_teacher_attendence_system.data.model.ClassModel
import kotlinx.coroutines.tasks.await

class ClassRepository {

    fun listenToClasses(onChange: (List<ClassModel>) -> Unit, onError: (Exception) -> Unit) {
        FirebaseService.classesCollection
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                val classes = snapshot?.toObjects(ClassModel::class.java) ?: emptyList()
                onChange(classes)
            }
    }

    suspend fun addClass(obj: ClassModel) {
        var id = ""
        FirebaseService.classesCollection.add(obj).addOnSuccessListener { docRef ->
            id = docRef.id
        }.await()

        FirebaseService.classesCollection.document(id).update("id", id)
    }
}
