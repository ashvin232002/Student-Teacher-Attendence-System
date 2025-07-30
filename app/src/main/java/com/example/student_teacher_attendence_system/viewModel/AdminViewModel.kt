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
import com.example.student_teacher_attendence_system.data.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val attendanceRepository : AttendanceRepository,
    private val classRepository: ClassRepository,
    private val dateUtils: DateUtils
): ViewModel() {

    private val _attendance = MutableStateFlow<List<AttendanceModel>>(emptyList())
    private val _classes = MutableStateFlow<List<ClassModel>>(emptyList())
    private val _attendanceRecords = MutableStateFlow<List<AttendanceModel>>(emptyList())

    val attendance = _attendance.asStateFlow()
    val classes = _classes.asStateFlow()
    val attendanceRecords = _attendanceRecords.asStateFlow()



    @RequiresApi(Build.VERSION_CODES.O)
    fun listenToClassesAndUpdateAttendance(day: String) {

        val parsedSelected = dateUtils.convertDateToEpoch(day)
        Log.d("parsed selected ",parsedSelected.toString())
        classRepository.listenToClasses(
            onChange = { updatedClasses ->
                _classes.value = updatedClasses

                viewModelScope.launch {
                    val records = attendanceRepository.getAttendanceWithClassInfo(updatedClasses, parsedSelected)
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


        val parsedSelected = dateUtils.convertDateToEpoch(day)

        attendanceRepository.listenToAttendance(
            parsedSelected,
            onChange = { updatedAttendance ->
                _attendance.value = updatedAttendance

                viewModelScope.launch {
                    val records = attendanceRepository.getAttendanceWithClassInfo(_classes.value, parsedSelected)
                    _attendanceRecords.value = records
                }
            },
            onError = {
                Log.e("AdminViewModel", "Error listening to attendance", it)
            }
        )
    }
}
