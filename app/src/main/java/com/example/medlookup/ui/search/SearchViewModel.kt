package com.example.medlookup.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medlookup.domain.repository.MedicineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: MedicineRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SearchUiState(
            query = savedStateHandle.get<String>("query") ?: "",
            scrollIndex = savedStateHandle.get<Int>("scroll_index") ?: 0,
            scrollOffset = savedStateHandle.get<Int>("scroll_offset") ?: 0
        )
    )

    val uiState: StateFlow<SearchUiState> =
        _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow(_uiState.value.query)

    init {
        observeSearchQuery()
    }

    fun onQueryChange(query: String) {

        _uiState.value = _uiState.value.copy(
            query = query,
            errorMessage = null,
            isOfflineResults = false
        )

        searchQuery.value = query
        savedStateHandle["query"] = query
    }

    private fun observeSearchQuery() {

        viewModelScope.launch {

            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->

                    if (query.isBlank()) {

                        _uiState.value = _uiState.value.copy(
                            medicines = emptyList(),
                            isLoading = false,
                            errorMessage = null,
                            isOfflineResults = false
                        )

                        return@collect
                    }

                    searchMedicines(query)
                }
        }
    }

    private suspend fun searchMedicines(query: String) {

        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMessage = null
        )

        val result = repository.searchMedicines(
            query = query
        )

        result
            .onSuccess { searchResult ->
                _uiState.value = _uiState.value.copy(
                    medicines = searchResult.medicines,
                    isOfflineResults = searchResult.isFromCache,
                    isLoading = false,
                    errorMessage = null
                )
            }

            .onFailure { exception ->

                _uiState.value = _uiState.value.copy(
                    medicines = emptyList(),
                    isLoading = false,
                    errorMessage = exception.message
                        ?: "Something went wrong"
                )
            }
    }

    fun retry() {

        val query = _uiState.value.query

        if (query.isNotBlank()) {

            viewModelScope.launch {
                searchMedicines(query)
            }
        }
    }

    fun updateScrollPosition(
        index: Int,
        offset: Int
    ) {
        _uiState.value = _uiState.value.copy(
            scrollIndex = index,
            scrollOffset = offset
        )
        savedStateHandle["scroll_index"] = index
        savedStateHandle["scroll_offset"] = offset
    }
}
