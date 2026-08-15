package com.example.medlookup.ui.detail
import com.example.medlookup.domain.model.Medicine

data class MedicineDetailUiState(
    val isLoading: Boolean = true,
    val medicine: Medicine? = null,
    val errorMessage: String? = null
)