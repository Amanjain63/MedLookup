package com.example.medlookup.ui.search

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.medlookup.domain.repository.MedicineRepository

class SearchViewModelFactory(
    private val repository: MedicineRepository,
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

        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(
                repository = repository,
                savedStateHandle = handle
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}