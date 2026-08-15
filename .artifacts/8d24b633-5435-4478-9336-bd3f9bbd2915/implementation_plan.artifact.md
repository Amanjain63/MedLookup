# Implementation Plan - Fix SavedStateProvider registration error

The application crashes with `java.lang.IllegalArgumentException: SavedStateProvider with the given key is already registered` when navigating between screens or during recomposition. This is caused by multiple ViewModels attempting to register their saved state with the same `SavedStateRegistryOwner` (the Activity) using conflicting keys.

## User Review Required

> [!IMPORTANT]
> The `SearchViewModel` will be moved from being Activity-scoped to being Destination-scoped (scoped to the "search" destination in the NavHost). This means it will be cleared if the search screen is completely removed from the backstack, but it will survive navigation to the detail screen and back. This is the standard and safer practice with `NavHost`.

## Proposed Changes

### Navigation Layer

#### [MODIFY] [AppNavigation.kt](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/app/src/main/java/com/example/medlookup/navigation/AppNavigation.kt)

- Remove the duplicate `viewModel` import.
- Move the `SearchViewModel` instantiation inside the `composable("search")` destination block.
- Use the `NavBackStackEntry` (provided as `backStackEntry` in the destination block) as the `owner` for both `SearchViewModelFactory` and `MedicineDetailViewModelFactory`.
- This ensures that each destination has its own `SavedStateRegistry`, avoiding collisions at the Activity level.

### UI Layer

#### [MODIFY] [SearchScreen.kt](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/app/src/main/java/com/example/medlookup/ui/search/SearchScreen.kt)

- Remove the default `viewModel = viewModel()` parameter value. Since `SearchViewModel` requires a custom factory, the default `viewModel()` call would fail or create an empty/broken ViewModel if triggered. Making it explicit ensures proper DI via the navigation layer.

## Verification Plan

### Manual Verification
- Deploy the app to a device/emulator.
- Perform searches and navigate to multiple different medicine details.
- Navigate back and forth between search and details multiple times.
- Rotate the screen while on both screens to trigger recomposition and saved state restoration.
- Verify that the crash no longer occurs.
