package com.example.medlookup.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailScreen(
    viewModel: MedicineDetailViewModel,
    onBackClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Medicine Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.semantics {
                            contentDescription = "Back"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        when {

            uiState.isLoading -> {

                LoadingDetailState(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.errorMessage != null -> {

                DetailErrorState(
                    message = uiState.errorMessage,
                    onRetry = viewModel::retry,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            uiState.medicine != null -> {

                MedicineContent(
                    medicine = uiState.medicine!!,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}
@Composable
private fun LoadingDetailState(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator()

        Text(
            text = "Loading medicine information...",
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}
@Composable
private fun DetailErrorState(
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Unable to load medicine",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = message ?: "Something went wrong.",
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
private fun MedicineContent(
    medicine: com.example.medlookup.domain.model.Medicine,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = medicine.brandName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = MaterialTheme.typography.headlineMedium.lineHeight * 1.1
        )

        MedicineInfoRow(
            label = "Generic name",
            value = medicine.genericName
        )

        MedicineInfoRow(
            label = "Manufacturer",
            value = medicine.manufacturer
        )

        MedicineInfoRow(
            label = "Route",
            value = medicine.route
        )

        MedicineInfoRow(
            label = "Product type",
            value = medicine.productType
        )

        HorizontalDivider()

        MedicalDisclaimer()

        LabelSection(
            title = "Purpose",
            content = medicine.purpose
        )

        LabelSection(
            title = "Indications and Usage",
            content = medicine.indicationsAndUsage
        )

        LabelSection(
            title = "Dosage and Administration",
            content = medicine.dosageAndAdministration
        )

        LabelSection(
            title = "Warnings",
            content = medicine.warnings
        )

        LabelSection(
            title = "Do Not Use",
            content = medicine.doNotUse
        )

        LabelSection(
            title = "Ask a Doctor",
            content = medicine.askDoctor
        )

        LabelSection(
            title = "Ask a Doctor or Pharmacist",
            content = medicine.askDoctorOrPharmacist
        )

        LabelSection(
            title = "Stop Use",
            content = medicine.stopUse
        )

        LabelSection(
            title = "Pregnancy or Breast-feeding",
            content = medicine.pregnancyOrBreastFeeding
        )

        LabelSection(
            title = "Keep Out of Reach of Children",
            content = medicine.keepOutOfReachOfChildren
        )

        LabelSection(
            title = "Active Ingredient",
            content = medicine.activeIngredient
        )

        LabelSection(
            title = "Inactive Ingredient",
            content = medicine.inactiveIngredient
        )

        LabelSection(
            title = "Storage and Handling",
            content = medicine.storageAndHandling
        )
    }
}
@Composable
private fun MedicineInfoRow(
    label: String,
    value: String
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
@Composable
private fun MedicalDisclaimer() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Medical Disclaimer",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "This information comes from public FDA drug label data. " +
                    "The data may be incomplete or unvalidated and is not medical advice. " +
                    "Always consult a qualified healthcare professional for medical decisions.",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
@Composable
private fun LabelSection(
    title: String,
    content: String?
) {
    if (content.isNullOrBlank()) return

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .padding(vertical = 8.dp)
            .animateContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (isExpanded) "Collapse $title" else "Expand $title"
            )
        }

        if (isExpanded) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.2,
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
