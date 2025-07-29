package com.example.student_teacher_attendence_system.data.model

data class DataOrException<T,E : Exception?>(
    var data: T? =  null,
    var e: E? =  null
)