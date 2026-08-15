# MedLookup - FDA Drug Label Search

MedLookup is an Android application that allows users to search for medicine information using the public FDA Drug Label API.

## Features
- **Incremental Search**: Results update as you type with a 500ms debounce to avoid excessive API calls.
- **Offline Support**: Previously searched medicines are cached locally using Room. If the network is unavailable, the app falls back to the cache.
- **Process Death Survival**: Search query and scroll position are preserved using `SavedStateHandle`.
- **Readable Details**: Long text sections in the medicine details are expandable to save screen space and improve readability.
- **Accessibility**: All interactive elements have proper content descriptions. Layout is tested for readability at large font sizes.

## Technical Stack
- **Kotlin** & **Jetpack Compose** (Declarative UI)
- **Room** (Local Caching)
- **Retrofit** (Networking)
- **Coroutines & Flow** (Asynchronous work)
- **ViewModel** (State management)

## Setup & Run
1. Clone the repository.
2. Open in Android Studio .
3. Build and run the app module on an emulator or physical device .
4. Run tests using `./gradlew test` and `./gradlew connectedCheck`.

## Known Issues / Limitations
- **API Rate Limits**: The app uses a public FDA endpoint without a key. Debouncing is used to mitigate this, but excessive testing might trigger rate limiting.
- **Search Scope**: Current search is limited to `brand_name`. Future versions could include `generic_name` or `manufacturer`.
- **Image Support**: FDA API does not provide medicine images directly; we display text-based label data.
