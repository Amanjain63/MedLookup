package com.example.medlookup.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medlookup.domain.model.Medicine
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(),
    onMedicineClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = uiState.scrollIndex,
        initialFirstVisibleItemScrollOffset = uiState.scrollOffset
    )

    SearchScreenContent(
        uiState = uiState,
        listState = listState,
        onQueryChange = viewModel::onQueryChange,
        onRetry = viewModel::retry,
        onMedicineClick = onMedicineClick
    )

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.firstVisibleItemIndex to
                    listState.firstVisibleItemScrollOffset
        }
            .distinctUntilChanged()
            .collect { (index, offset) ->
                viewModel.updateScrollPosition(
                    index = index,
                    offset = offset
                )
            }
    }
}

@Composable
fun SearchScreenContent(
    uiState: SearchUiState,
    listState: LazyListState,
    onQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onMedicineClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "MedLookup",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = uiState.query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .semantics {
                    contentDescription = "Search medicine"
                },
            placeholder = {
                Text("Search medicine...")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true
        )
        if (uiState.isOfflineResults) {
            Text(
                text = "Showing offline results",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        when {
            // Loading
            uiState.isLoading -> {
                LoadingState()
            }

            // Error
            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage,
                    onRetry = onRetry
                )
            }
            // Empty search
            uiState.query.isBlank() -> {
                InitialState()
            }

            // No results
            uiState.medicines.isEmpty() -> {
                EmptyState(
                    query = uiState.query
                )
            }

            // Results
            else -> {
                MedicineList(
                    medicines = uiState.medicines,
                    onMedicineClick = onMedicineClick,
                    listState = listState
                )
            }
        }
    }
}
@Composable
private fun LoadingState() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator()

        Text(
            text = "Searching medicines...",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}
@Composable
private fun InitialState() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Search for a medicine",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Try Aspirin, Ibuprofen, or Acetaminophen.",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Composable
private fun EmptyState(
    query: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "No medicines found",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "No results for \"$query\".",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Composable
private fun ErrorState(
    message: String?,
    onRetry: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = message ?: "Unable to load medicines.",
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = onRetry,
            modifier = Modifier
                .padding(top = 16.dp)
                .semantics {
                    contentDescription = "Try again"
                }
        ) {
            Text("Try again")
        }
    }
}
@Composable
private fun MedicineList(
    medicines: List<Medicine>,onMedicineClick: (String) -> Unit,listState: LazyListState
) {

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = medicines,
            key = { medicine ->
                medicine.id
            }
        ) { medicine ->

            MedicineCard(
                medicine = medicine,
                onClick = {
                    onMedicineClick(medicine.id)
                }
            )
        }
    }
}
@Composable
private fun MedicineCard(
    medicine: Medicine,onClick:()-> Unit
) {

    Card(
        onClick=onClick,
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                contentDescription =
                    "Medicine ${medicine.brandName}"
            }
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = medicine.brandName,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Generic: ${medicine.genericName}",
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = "Manufacturer: ${medicine.manufacturer}",
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }


}