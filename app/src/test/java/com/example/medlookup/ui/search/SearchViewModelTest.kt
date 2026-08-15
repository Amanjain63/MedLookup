package com.example.medlookup.ui.search

import androidx.lifecycle.SavedStateHandle
import com.example.medlookup.domain.model.Medicine
import com.example.medlookup.domain.repository.MedicineRepository
import com.example.medlookup.domain.repository.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @Mock
    lateinit var repository: MedicineRepository

    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(repository, SavedStateHandle())
    }

    @Test
    fun `initial state is correct`() {
        val state = viewModel.uiState.value
        assertEquals("", state.query)
        assertTrue(state.medicines.isEmpty())
        assertFalse(state.isLoading)
    }

    @Test
    fun `query change updates state and triggers search after debounce`() = runTest {
        // Given
        val medicines = listOf(
            Medicine("1", "Advil", "Ibuprofen", "Pfizer", "Oral", "OTC",
                null, null, null, null, null, null, null, null, null, null, null, null, null)
        )
        whenever(repository.searchMedicines(any(), any(), any()))
            .thenReturn(Result.success(SearchResult(medicines, false)))

        // When
        viewModel.onQueryChange("Advil")
        
        // Then: Immediate update
        assertEquals("Advil", viewModel.uiState.value.query)
        assertFalse(viewModel.uiState.value.isLoading)

        // Advance time for debounce (500ms)
        advanceTimeBy(600)
        
        // Then: Loading starts and eventually results are shown
        // In our ViewModel, searchMedicines is called in a launch block.
        // We need to wait for it.
        testScheduler.runCurrent()
        
        val state = viewModel.uiState.value
        assertEquals(medicines, state.medicines)
        assertFalse(state.isLoading)
    }

    @Test
    fun `search failure updates error message`() = runTest {
        // Given
        whenever(repository.searchMedicines(any(), any(), any()))
            .thenReturn(Result.failure(RuntimeException("Search failed")))

        // When
        viewModel.onQueryChange("Error")
        advanceTimeBy(600)
        testScheduler.runCurrent()

        // Then
        val state = viewModel.uiState.value
        assertEquals("Search failed", state.errorMessage)
        assertFalse(state.isLoading)
    }
}
