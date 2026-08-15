# MedLookup - FDA Drug Label Search

MedLookup is an Android app that lets users search for medicine information using the public FDA Drug Label API.

## Features

- **Medicine Search** - Search for medicines as you type with a 500ms debounce to reduce unnecessary API calls.
- **Offline Support** - Previously searched medicines are saved locally using Room and can be viewed when the network is not available.
- **Process Death Support** - Search query and scroll position are restored using `SavedStateHandle`.
- **Medicine Details** - Long sections of medicine information can be expanded or collapsed for easier reading.
- **Accessibility** - Interactive elements include proper descriptions and the UI is designed to remain readable with larger font sizes.

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Room Database
- Retrofit
- Kotlin Coroutines & Flow
- ViewModel
- SavedStateHandle

## Project Structure

The project follows a simple layered architecture:

- **UI** - Compose screens and UI state
- **ViewModel** - Handles UI state and user actions
- **Repository** - Connects the API and local database
- **Remote Data Source** - Retrofit API service
- **Local Data Source** - Room database

## Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle sync and download the required dependencies.
4. Run the `app` module on an emulator or physical Android device.

### Run Tests

For unit tests:

```bash
./gradlew test