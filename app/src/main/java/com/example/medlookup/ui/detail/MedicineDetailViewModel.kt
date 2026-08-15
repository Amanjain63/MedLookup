package com.example.medlookup.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medlookup.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MedicineDetailViewModel(
    private val repository: MedicineRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val medicineId: String = savedStateHandle.get<String>("medicineId")
        ?: throw IllegalArgumentException("medicineId is required")

    private val _uiState =
        MutableStateFlow(
            MedicineDetailUiState()
        )

    val uiState: StateFlow<MedicineDetailUiState> =
        _uiState.asStateFlow()

    init {
        loadMedicine()
    }

    private fun loadMedicine() {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            val result =
                repository.getMedicineById(
                    medicineId
                )

            result
                .onSuccess { medicine ->

                    _uiState.value =
                        MedicineDetailUiState(
                            isLoading = false,
                            medicine = medicine,
                            errorMessage = null
                        )
                }
                .onFailure { exception ->

                    _uiState.value =
                        MedicineDetailUiState(
                            isLoading = false,
                            medicine = null,
                            errorMessage =
                                exception.message
                                    ?: "Unable to load medicine"
                        )
                }
        }
    }

    fun retry() {
        loadMedicine()
    }
}
