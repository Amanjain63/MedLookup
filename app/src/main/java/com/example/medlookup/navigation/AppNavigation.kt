package com.example.medlookup.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.medlookup.DependencyProvider
import com.example.medlookup.ui.detail.MedicineDetailScreen
import com.example.medlookup.ui.detail.MedicineDetailViewModel
import com.example.medlookup.ui.detail.MedicineDetailViewModelFactory
import com.example.medlookup.ui.search.SearchScreen
import com.example.medlookup.ui.search.SearchViewModel
import com.example.medlookup.ui.search.SearchViewModelFactory

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "search"
    ) {

        composable("search") { backStackEntry ->

            val searchViewModel: SearchViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = SearchViewModelFactory(
                    repository = DependencyProvider
                        .provideMedicineRepository(context),
                    owner = backStackEntry
                )
            )

            SearchScreen(
                viewModel = searchViewModel,
                onMedicineClick = { medicineId ->
                    navController.navigate(
                        "medicine_detail/$medicineId"
                    )
                }
            )
        }

        composable(
            route = "medicine_detail/{medicineId}",
            arguments = listOf(
                navArgument("medicineId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val medicineId =
                backStackEntry.arguments?.getString("medicineId")

            if (medicineId != null) {

                val detailViewModel: MedicineDetailViewModel = viewModel(
                    viewModelStoreOwner = backStackEntry,
                    factory = MedicineDetailViewModelFactory(
                        repository = DependencyProvider
                            .provideMedicineRepository(context),
                        medicineId = medicineId,
                        owner = backStackEntry
                    )
                )

                MedicineDetailScreen(
                    viewModel = detailViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}