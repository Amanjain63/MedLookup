package com.example.medlookup.ui.search

import com.example.medlookup.domain.model.Medicine

data class SearchUiState(
    val query: String = "",
    val medicines: List<Medicine> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isOfflineResults: Boolean = false,
    val scrollIndex: Int = 0,
    val scrollOffset: Int = 0
)