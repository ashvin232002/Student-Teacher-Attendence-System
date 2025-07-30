package com.example.student_teacher_attendence_system.data.repository

import android.util.Log
import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.data.model.ClassModel
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val fireBaseService: FirebaseService
): AttendanceRepository {
    override suspend fun getAttendance(date: Int): List<AttendanceModel> {
        return withContext(Dispatchers.IO) {
            try {
                val snapshot = fireBaseService.attendanceCollection
                    .whereEqualTo("day", date)
                    .get()
                    .await()

                Log.d("AttendanceRepository", "Documents found: ${snapshot.documents.size}")
                snapshot.documents.mapNotNull { it.toObject(AttendanceModel::class.java) }
            } catch (e: Exception) {
                Log.e("AttendanceRepository", "Error getting attendance", e)
                emptyList()
            }
        }
    }

    override fun listenToAttendance(
        day: Long,
        onChange: (List<AttendanceModel>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        fireBaseService.attendanceCollection
            .whereEqualTo("day", day)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("AttendanceRepository", "Error listening to attendance", error)
                    onError(error)
                    return@addSnapshotListener
                }

                val attendanceList = snapshot?.toObjects(AttendanceModel::class.java) ?: emptyList()
                onChange(attendanceList)
            }
    }

    override suspend fun getAttendanceWithClassInfo(
        classList: List<ClassModel>,
        day: Long
    ): List<AttendanceModel> {
        return withContext(Dispatchers.IO) {
            try {
                val attendanceRecords = mutableListOf<AttendanceModel>()

                classList.forEach { classModel ->
                    val querySnapshot = fireBaseService.attendanceCollection
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

                attendanceRecords
            } catch (e: Exception) {
                Log.e("AttendanceRepository", "Error getting attendance with class info", e)
                emptyList()
            }
        }
    }
}