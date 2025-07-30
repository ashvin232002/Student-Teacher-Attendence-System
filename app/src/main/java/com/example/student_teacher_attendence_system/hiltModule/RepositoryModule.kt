package com.example.student_teacher_attendence_system.hiltModule

import com.example.student_teacher_attendence_system.data.repository.AttendanceRepository
import com.example.student_teacher_attendence_system.data.repository.AttendanceRepositoryImpl
import com.example.student_teacher_attendence_system.data.repository.ClassRepository
import com.example.student_teacher_attendence_system.data.repository.ClassRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAttendanceRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ): AttendanceRepository


    @Binds
    @Singleton
    abstract fun bindClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository
}