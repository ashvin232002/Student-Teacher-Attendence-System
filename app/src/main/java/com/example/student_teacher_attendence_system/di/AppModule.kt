package com.example.student_teacher_attendence_system.di

import com.example.student_teacher_attendence_system.data.firebase.FirebaseService
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
    fun ProvideQueryClasses() = FirebaseService.classesCollection
}
