package com.example.medlookup.ui.search

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.medlookup.domain.model.Medicine
import com.example.medlookup.ui.theme.MedLookupTheme
import org.junit.Rule
import org.junit.Test

class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchScreen_showsResultsWhenFound() {
        val medicines = listOf(
            Medicine("1", "Advil", "Ibuprofen", "Pfizer", "Oral", "OTC",
                null, null, null, null, null, null, null, null, null, null, null, null, null)
        )
        val uiState = SearchUiState(
            query = "Advil",
            medicines = medicines,
            isLoading = false
        )

        composeTestRule.setContent {
            MedLookupTheme {
                SearchScreenContent(
                    uiState = uiState,
                    listState = rememberLazyListState(),
                    onQueryChange = {},
                    onRetry = {},
                    onMedicineClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Advil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Generic: Ibuprofen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Manufacturer: Pfizer").assertIsDisplayed()
    }

    @Test
    fun searchScreen_showsErrorStateAndRetries() {
        var retryClicked = false
        val uiState = SearchUiState(
            query = "Error Query",
            errorMessage = "No internet connection",
            isLoading = false
        )

        composeTestRule.setContent {
            MedLookupTheme {
                SearchScreenContent(
                    uiState = uiState,
                    listState = rememberLazyListState(),
                    onQueryChange = {},
                    onRetry = { retryClicked = true },
                    onMedicineClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText("No internet connection").assertIsDisplayed()
        
        composeTestRule.onNodeWithContentDescription("Try again").performClick()
        assert(retryClicked)
    }
}
