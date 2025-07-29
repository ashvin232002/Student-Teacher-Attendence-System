package com.example.student_teacher_attendence_system.data.repository

import android.util.Log
import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
import com.example.student_teacher_attendence_system.data.model.ClassModel
import com.example.student_teacher_attendence_system.data.model.TeacherModel
import com.example.student_teacher_attendence_system.viewModel.TeacherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class TeacherRepository{
     private val  db =  FirebaseService.teachersCollection
     private val  dbClassCollection =  FirebaseService.classesCollection

     suspend fun addTeacher(teacherModel: TeacherModel){
          var id:String=""
          FirebaseService.teachersCollection.add(teacherModel).addOnSuccessListener {
               docRef -> id=docRef.id
          }.await()

          FirebaseService.teachersCollection.document(id).update("id",id)
     }

     suspend fun getTeacher():List<TeacherModel>{
          return withContext(Dispatchers.IO){
               val snapshot = db.get().await()
               snapshot.documents.mapNotNull { it.toObject(TeacherModel::class.java) }
          }
     }

     suspend fun getAllClasses(): List<ClassModel>{
          return withContext(Dispatchers.IO){
               val snapshot = dbClassCollection.get().await()
               Log.d("ClassRepository", "Documents found: ${snapshot.documents.size}")
               snapshot.documents.mapNotNull { it.toObject(ClassModel::class.java) }
          }
     }
     fun getAllClassesRealTime(): Flow<List<ClassModel>> = callbackFlow {
          val listener = FirebaseService.classesCollection.addSnapshotListener { snapshot, error ->
               if (error != null) {
                    close(error)
                    return@addSnapshotListener
               }

               val classList = snapshot?.documents?.mapNotNull {
                    it.toObject(ClassModel::class.java)
               } ?: emptyList()

               trySend(classList).isSuccess
          }

          awaitClose { listener.remove() }
     }


//     fun getAllClassesRealTime(): Flow<List<ClassModel>> = callbackFlow {
//          val listener = dbClassCollection.addSnapshotListener { snapshot, error ->
//               if (error != null) {
//                    close(error)
//                    return@addSnapshotListener
//               }
//
//               val classList = snapshot?.documents?.mapNotNull {
//                    it.toObject(ClassModel::class.java)
//               } ?: emptyList()
//
//               trySend(classList).isSuccess
//          }
//
//          awaitClose { listener.remove() }
//     }

//     fun getAllClasses(): Flow<List<ClassModel>> = callbackFlow {
//          val listenerRegistration = dbClassCollection.addSnapshotListener { snapshot, error ->
//               if (error != null) {
//                    close(error)
//                    return@addSnapshotListener
//               }
//
//               if (snapshot != null) {
//                    val classList = snapshot.documents.mapNotNull { it.toObject(ClassModel::class.java) }
//                    trySend(classList).isSuccess
//               }
//          }
//
//          awaitClose {
//               listenerRegistration.remove()
//          }
//     }


//     suspend fun getClasses():List<ClassModel>{
//          return withContext(Dispatchers.IO){
//               val snapshot = db.get().await()
//               snapshot.documents.mapNotNull { it.toObject(ClassModel::class.java) }
//          }
//     }
//
//     suspend fun updateClasses(classModel: ClassModel){
//          db.document(classModel.id).set(classModel).await()
//     }
//

}