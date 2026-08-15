# Fix SavedStateProvider registration error

The application is crashing with a `java.lang.IllegalArgumentException: SavedStateProvider with the given key is already registered` error. This is caused by multiple ViewModels trying to register themselves with the same `SavedStateRegistry` (the Activity's) using the same key.

## User Review Required

> [!IMPORTANT]
> The fix involves scoping the ViewModels to their respective `NavBackStackEntry` instead of the `Activity`. This ensures that each instance of a destination in the navigation backstack has its own `SavedStateRegistry`, preventing collisions.

## Proposed Changes

### Navigation

#### [MODIFY] [AppNavigation.kt](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/app/src/main/java/com/example/medlookup/navigation/AppNavigation.kt)

- Scope `SearchViewModel` to the "search" composable.
- Scope `MedicineDetailViewModel` to its specific `NavBackStackEntry`.
- Use the correct `SavedStateRegistryOwner` for each ViewModel factory.

## Verification Plan

### Manual Verification
- Deploy the app to a device or emulator.
- Perform a search.
- Click on a medicine to view details.
- Go back to search.
- Click on another medicine (or the same one again).
- Verify that the app no longer crashes.
- Verify that state (like search query or scroll position) is still preserved correctly across rotations and navigations.
