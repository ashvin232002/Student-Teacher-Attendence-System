package com.example.student_teacher_attendence_system.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_teacher_attendence_system.data.repository.ClassRepository
import com.example.student_teacher_attendence_system.data.model.ClassModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ClassViewModel: ViewModel() {

    private val classRepository = ClassRepository()

    private val _classes = MutableStateFlow<List<ClassModel>>(emptyList())
    val classes = _classes.asStateFlow()

    init {

        startListeningToClasses()
    }

    private fun startListeningToClasses() {
        classRepository.listenToClasses(
            onChange = { classList ->
                _classes.value = classList
                Log.d("ClassViewModel", "Classes updated: ${classList.size} classes")
            },
            onError = { exception ->
                Log.e("ClassViewModel", "Error listening to classes", exception)
            }
        )
    }

    fun getAllClasses() {

        Log.d("ClassViewModel", "getAllClasses called - using real-time listeners")
    }

    fun createClass(obj: ClassModel) {
        viewModelScope.launch {
            try {
                classRepository.addClass(obj)

                Log.d("ClassViewModel", "Class added successfully")
            } catch (e: Exception) {
                Log.e("ClassViewModel", "Error adding class", e)
            }
        }
    }
}
