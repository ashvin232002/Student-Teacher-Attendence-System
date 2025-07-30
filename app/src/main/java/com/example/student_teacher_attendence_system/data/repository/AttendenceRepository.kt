package com.example.student_teacher_attendence_system.data.repository

import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import kotlinx.coroutines.tasks.await


class AttendenceRepository {
    private val db = FirebaseService.attendanceCollection
    private val repository = TeacherRepository();


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