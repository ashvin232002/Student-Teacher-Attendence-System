package com.example.student_teacher_attendence_system.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_teacher_attendence_system.data.repository.AttendanceRepository
import com.example.student_teacher_attendence_system.data.repository.ClassRepository
import com.example.student_teacher_attendence_system.data.model.AttendanceModel
import com.example.student_teacher_attendence_system.data.model.ClassModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class AdminViewModel : ViewModel() {
    private val attendanceRepository = AttendanceRepository()
    private val classRepository = ClassRepository()

    private val _attendance = MutableStateFlow<List<AttendanceModel>>(emptyList())
    private val _classes = MutableStateFlow<List<ClassModel>>(emptyList())
    private val _attendanceRecords = MutableStateFlow<List<AttendanceModel>>(emptyList())

    val attendance = _attendance.asStateFlow()
    val classes = _classes.asStateFlow()
    val attendanceRecords = _attendanceRecords.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateToEpoch(dateStr: String): Long {

        val formatter = DateTimeFormatter.ofPattern("d MMMM,yyyy", java.util.Locale.ENGLISH)

        val localDate = LocalDate.parse(dateStr, formatter)

        return localDate.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun listenToClassesAndUpdateAttendance(day: String) {

        val parsedSelected = convertDateToEpoch(day)
        Log.d("parsed selected ",parsedSelected.toString())
        classRepository.listenToClasses(
            onChange = { updatedClasses ->
                _classes.value = updatedClasses

                viewModelScope.launch {
                    val records = attendanceRepository.utilityAttendanceToClass(updatedClasses, parsedSelected)
                    _attendanceRecords.value = records
                }
            },
            onError = {
                Log.e("AdminViewModel", "Error listening to classes", it)
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun listenToAttendance(day: String) {

        val parsedSelected = convertDateToEpoch(day)


        attendanceRepository.listenToAttendance(
            parsedSelected,
            onChange = { updatedAttendance ->
                _attendance.value = updatedAttendance

                viewModelScope.launch {
                    val records = attendanceRepository.utilityAttendanceToClass(_classes.value, parsedSelected)
                    _attendanceRecords.value = records
                }
            },
            onError = {
                Log.e("AdminViewModel", "Error listening to attendance", it)
            }
        )
    }
}
