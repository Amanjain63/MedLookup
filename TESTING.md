# Testing Strategy

This document describes the testing approach taken and future scenarios to be covered.

## Scenarios Tested
1. **Messy Data Mapping**: Verified that the app handles FDA payloads with empty strings, nulls, and missing fields without crashing (Unit Test).
2. **Offline Fallback**: Verified that the repository returns cached results when the network throws an exception (Unit Test).
3. **Search Debounce**: Verified that rapid typing does not trigger multiple immediate requests (Unit Test).
4. **UI Success Path**: Verified that the search results list renders correctly with medicine details (Compose UI Test).
5. **UI Error Path**: Verified that the error state is shown on failure and the "Retry" button triggers a reload (Compose UI Test).

## Future Testing Scenarios
With more time, I would automate the following:
- **Pagination**: Test "Load More" logic when results exceed 20 items.
- **Large Font Accessibility**: Automated screenshot tests at 200% font size to ensure no text overlap in the Detail screen.
- **Connection Transitions**: Verify behavior when switching from 4G to No Connection *while* a request is in flight.
- **Process Death with Full Cache**: Test performance when restoring a search state with a very large local database (1000+ entries).
- **TalkBack Navigation**: Automated accessibility checks to ensure focus order is logical on the expandable Detail sections.

## Automation Constraints
Mobile-specific behaviors like "patchy 4G" are hard to automate reliably in standard CI. These were verified manually by using the Emulator's Extended Controls to simulate different network speeds and latencies.
