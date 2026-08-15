# Architectural & Product Decisions

This document outlines the key decisions made during the development of MedLookup.

### 1. Does Screen 2 receive the full object from Screen 1, or re-fetch by ID?
**Decision**: Screen 2 re-fetches the medicine by ID.
**Reasoning**: While passing the full object is faster, re-fetching by ID ensures that the detail screen is the source of truth and can be deep-linked easily. It also handles the case where the user returns to the app via a direct link or after process death when the large object might have been cleared from memory. Given our offline caching, this "re-fetch" is nearly instantaneous if the data is already in the local database.

### 2. What exactly do you cache, where, and when does it expire?
**Decision**: We cache the full `Medicine` domain model in a Room database.
**Reasoning**: Since the FDA label data is relatively static (doesn't change minute-to-minute), we cache results immediately after a successful API call. For this exercise, data does not explicitly "expire" within a session, but new API searches refresh the cache. In a production app, we would add a `last_updated` timestamp and expire entries after 7 days.

### 3. How do you distinguish "the search returned nothing" from "the request failed"?
**Decision**: Distinguish via distinct UI states in the `SearchUiState`.
**Reasoning**: "No results" is a success state where the API returned a 200 OK with an empty results array. "Request failed" is a failure state (catch block) due to network errors or server issues. The UI shows a helpful "No results for X" message for the former, and an "Error" screen with a "Retry" button for the latter.

### 4. What is your strategy for keystroke-to-request, and what latency does the user experience as a result?
**Decision**: 500ms `debounce` on the search query Flow.
**Reasoning**: This provides a balance between responsiveness and rate-limiting. The user experiences a half-second pause after they stop typing before the network request starts. This prevents firing 10+ requests for a single word and respects the FDA's unkeyed rate limits.

### 5. How is your data layer structured, and what would change if we added a second data source?
**Decision**: Repository pattern with a domain-level `MedicineRepository` interface.
**Reasoning**: The `MedicineRepositoryImpl` manages both the `ApiService` and `MedicineDao`. If a second data source (e.g., a proprietary pharma DB) were added, we would create a new implementation of the interface or update the existing one to merge results, keeping the UI layer completely unaware of where the data comes from.

### 6. What did you deliberately not build, and what would you do next with another five hours?
**Decision**: Skipped Hilt DI and multi-module setup.
**Reasoning**: Given the 5-hour timebox, I prioritized functionality (offline, process death, polish) over boilerplate-heavy DI frameworks. I used a simple `DependencyProvider`. With more time, I would:
1. Implement full Hilt DI for better testability.
2. Add "Search Suggestions" based on local history.
3. Add a "Recently Viewed" section to the initial search screen.
4. Add more robust UI animations for list transitions.
