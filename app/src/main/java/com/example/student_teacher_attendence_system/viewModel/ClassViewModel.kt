package com.example.student_teacher_attendence_system.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_teacher_attendence_system.data.repository.ClassRepository
import com.example.student_teacher_attendence_system.data.model.ClassModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassViewModel @Inject constructor(
    private val classRepository: ClassRepository
) : ViewModel() {


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
