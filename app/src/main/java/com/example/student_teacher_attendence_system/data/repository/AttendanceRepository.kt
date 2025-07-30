package com.example.student_teacher_attendence_system.data.repository

import android.util.Log
import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.data.model.ClassModel
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AttendanceRepository {

    private val db = FirebaseService.attendanceCollection
    private val repository = TeacherRepository();


    suspend fun getAttendance(date: Int): List<AttendanceModel> {

        return withContext(Dispatchers.IO) {
            val snapshot =
                FirebaseService.attendanceCollection.whereEqualTo("day", date).get().await()

            Log.d("ClassRepository", "Documents found: ${snapshot.documents.size}")
            snapshot.documents.mapNotNull { it.toObject(AttendanceModel::class.java) }
        }
    }

    fun listenToAttendance(day: Long, onChange: (List<AttendanceModel>) -> Unit, onError: (Exception) -> Unit) {
        FirebaseService.attendanceCollection
            .whereEqualTo("day", day)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onError(error)
                    return@addSnapshotListener
                }

                val attendanceList = snapshot?.toObjects(AttendanceModel::class.java) ?: emptyList()
                onChange(attendanceList)
            }
    }

    suspend fun utilityAttendanceToClass(
        classList: List<ClassModel>,
        day: Long
    ): MutableList<AttendanceModel> {
        val attendanceRecords = mutableListOf<AttendanceModel>()

        classList.forEach { classModel ->
            val querySnapshot = FirebaseService.attendanceCollection
                .whereEqualTo("class_ref", classModel.id)
                .whereEqualTo("day", day)
                .get()
                .await()

            val records = querySnapshot.toObjects<AttendanceModel>()
                .map { attendance ->
                    attendance.copy(class_strength = classModel.total_strength)
                }

            attendanceRecords.addAll(records)
        }

        return attendanceRecords
    }

    suspend fun addAttendance(
        attendance: AttendanceModel,
        className: String,
        teacherEmail:String
    ) {
        val classes = repository.getAllClasses()
        val teacher =  repository.getTeacher()

        val matchedClass = classes.find { it.name == className }
        val matchedTeacher =  teacher.find { it.email == teacherEmail}

        if (matchedClass != null) {
            val attendanceWithClassRef =
                matchedTeacher?.let { attendance.copy(class_ref = matchedClass.id, teacher_ref = it.id) }
            var id:String = ""
            if (attendanceWithClassRef != null) {
                FirebaseService.attendanceCollection.add(attendanceWithClassRef).addOnSuccessListener { docRef -> id = docRef.id
                }.await()
            }

            FirebaseService.attendanceCollection.document(id).update("id",id)
        } else {
            throw IllegalArgumentException("No class found with name: $className")
        }
    }


}
