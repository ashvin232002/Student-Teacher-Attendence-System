package com.example.student_teacher_attendence_system.di

import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
import com.example.student_teacher_attendence_system.data.repository.AttendenceRepository
import com.example.student_teacher_attendence_system.data.repository.TeacherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFirebaseService(): FirebaseService = FirebaseService

    @Provides
    @Singleton
    fun provideTeacherRepository(): TeacherRepository = TeacherRepository()

    @Provides
    @Singleton
    fun provideAttendanceRepository(): AttendenceRepository = AttendenceRepository()
}