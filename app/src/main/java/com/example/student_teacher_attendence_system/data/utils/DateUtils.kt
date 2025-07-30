package com.example.student_teacher_attendence_system.data.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateUtils @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToEpoch(dateStr: String): Long {
        return try {
            val formatter = DateTimeFormatter.ofPattern("d MMMM,yyyy", Locale.ENGLISH)
            val localDate = LocalDate.parse(dateStr, formatter)
            localDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
        } catch (e: Exception) {
            Log.e("DateUtils", "Error converting date: $dateStr", e)
            0L
        }
    }
}