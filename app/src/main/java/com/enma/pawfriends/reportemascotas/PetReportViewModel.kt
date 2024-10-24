package com.enma.pawfriends.reportemascotas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetReportViewModel(private val repository: PetReportRepository) : ViewModel() {

    private val _petReports = MutableStateFlow<List<PetReport>>(emptyList())
    val petReports: StateFlow<List<PetReport>> get() = _petReports

    init {
        loadPetReports()
    }

    private fun loadPetReports() {
        viewModelScope.launch {
            _petReports.value = repository.getPetReports()
        }
    }

    fun reportPet(petReport: PetReport) {
        viewModelScope.launch {
            repository.reportPet(petReport)
        }
    }
}
