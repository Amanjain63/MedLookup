package com.example.medlookup.ui.detail

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.medlookup.domain.repository.MedicineRepository

class MedicineDetailViewModelFactory(
    private val repository: MedicineRepository,
    private val medicineId: String,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(
    owner,
    defaultArgs
) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {

        if (
            modelClass.isAssignableFrom(
                MedicineDetailViewModel::class.java
            )
        ) {

            handle["medicineId"] = medicineId

            @Suppress("UNCHECKED_CAST")
            return MedicineDetailViewModel(
                repository = repository,
                savedStateHandle = handle
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}