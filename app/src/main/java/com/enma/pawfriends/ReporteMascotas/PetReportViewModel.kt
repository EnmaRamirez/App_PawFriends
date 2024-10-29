package com.enma.pawfriends.ReporteMascotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PetReportViewModel( val repository: PetReportRepository) : ViewModel() {
    private val _petReports = MutableStateFlow<List<PetReport>>(emptyList())
    val petReports = _petReports.asStateFlow()

    init {
        fetchPetReports()
    }

    private fun fetchPetReports() {
        viewModelScope.launch {
            try {
                _petReports.value = repository.getPetReports()
            } catch (e: Exception) {
                // Manejar el error (log, mostrar mensaje, etc.)
                _petReports.value = emptyList() // O podrías establecer un estado de error
            }
        }
    }
}
