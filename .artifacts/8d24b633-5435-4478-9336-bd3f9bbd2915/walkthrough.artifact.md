# Walkthrough - Offline Support & Robustness

I have implemented full offline support using Room and improved the app's robustness against process death and messy data.

## Changes Made

### 1. Offline Caching (CR-1)
- Added **Room Database** to cache FDA drug label data.
- **Network-First Strategy**: The app tries to fetch live data from the FDA API first. If the request fails (e.g., no internet), it seamlessly falls back to the local Room cache.
- **Visual Indicator**: If results are served from the cache, a "Showing offline results" message appears below the search bar.

### 2. Process Death Recovery (CR-2, FR-6)
- ViewModels now use `SavedStateHandle` to preserve the search query and scroll position.
- If the app is killed by Android in the background, it will restore the exact search and scroll state when the user returns.

### 3. Architecture Improvements
- Refactored `MedicineRepositoryImpl` to handle caching logic cleanly.
- Implemented **Manual Dependency Injection** with a `DependencyProvider` to manage singletons (Retrofit, Room, Repository) without the complexity of Hilt in a small project.
- Updated Navigation to use `ViewModelFactory` for passing dependencies and `SavedStateHandle`.

### 4. Messy Data Handling (FR-3)
- Strengthened the `toDomainModel` mapping in the repository to provide sensible fallbacks for inconsistent FDA fields (e.g., "Unknown brand", "Route unavailable").

## How to Verify

### Offline Test
1. Search for a common drug (e.g., "Aspirin") while online.
2. Observe the results.
3. Turn off your internet connection.
4. Clear the search and type "Aspirin" again.
5. **Observation:** The results should appear immediately from the cache with the "Showing offline results" indicator.

### Process Death Test
1. Search for "Ibuprofen" and scroll down the list.
2. Background the app.
3. Run `adb shell am kill com.example.medlookup`.
4. Open the app from the recents menu.
5. **Observation:** The app should restore directly to the "Ibuprofen" search results at the exact scroll position.

## Next Steps
- Implement Unit and UI tests as planned in the PRD.
- Add better typography for long detail sections (FR-11).
