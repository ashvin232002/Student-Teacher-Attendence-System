package com.example.student_teacher_attendence_system.data.repository

import android.util.Log
import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
import com.example.student_teacher_attendence_system.data.model.ClassModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClassRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : ClassRepository {

    override fun listenToClasses(onChange: (List<ClassModel>) -> Unit, onError: (Exception) -> Unit) {
        firebaseService.classesCollection
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("ClassRepository", "Error listening to classes", error)
                    onError(error)
                    return@addSnapshotListener
                }

                val classes = snapshot?.toObjects(ClassModel::class.java) ?: emptyList()
                onChange(classes)
            }
    }

    override suspend fun addClass(obj: ClassModel): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val docRef = firebaseService.classesCollection.add(obj).await()
                val documentId = docRef.id

                firebaseService.classesCollection
                    .document(documentId)
                    .update("id", documentId)
                    .await()

                Log.d("ClassRepository", "Class added successfully with ID: $documentId")
                true
            } catch (e: Exception) {
                Log.e("ClassRepository", "Error adding class", e)
                false
            }
        }
    }
}
