# Testing Strategy

This document explains the tests included in the project and some additional tests that can be added later.

## Tests Covered

### Unit Tests

1. **FDA Response Mapping**
    - Tested API response mapping with messy data such as null values, empty strings, and missing fields.
    - Verified that the app does not crash and creates the correct domain model.

2. **Offline Fallback**
    - Tested the case where the API request fails.
    - Verified that previously cached data from Room is returned when available.

3. **Search Debounce**
    - Tested rapid changes in the search query.
    - Verified that unnecessary API calls are avoided because of the 500ms debounce.

4. **Error Handling**
    - Tested network/API failure.
    - Verified that the repository/ViewModel exposes the correct error state.

### Compose UI Tests

5. **Search Results**
    - Verified that medicine search results are displayed correctly on the screen.

6. **Error State**
    - Verified that the error message is displayed when the search fails.
    - Also verified that pressing the Retry button starts the search again.

## Additional Tests I Would Add

If I had more time, I would add tests for:

- **Pagination** - Verify loading more results when the API returns a large number of medicines.
- **Large Font Size** - Check that the Detail screen remains readable with larger system font sizes.
- **Network Changes** - Test what happens if the network is lost while an API request is running.
- **Process Death** - Verify that search state is correctly restored after the app process is killed.
- **Large Cache** - Test performance with a large number of medicines stored in Room.
- **Accessibility** - Check TalkBack navigation and focus order on expandable sections.

## Manual Testing

Some network-related cases are difficult to reproduce reliably in automated tests. I manually tested different network conditions using the Android Emulator's network controls, including slow network and no network scenarios.

## Testing Approach

The main focus was not on having a large number of tests, but on testing important user flows and failure cases.

I especially focused on:

- API response mapping
- Offline behavior
- Error handling
- Search behavior
- UI error state
- Process state restoration