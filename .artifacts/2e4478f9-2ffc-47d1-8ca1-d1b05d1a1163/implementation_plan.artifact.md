# Implementation Plan - Fix Espresso Resolution Issue

The user is encountering a "Failed to resolve: androidx.test.espresso:espresso-core:3.5.0" error during Gradle sync. Analysis suggests this is likely caused by a dependency conflict or an incorrect configuration where test-only libraries are included in the production `implementation` configuration, potentially triggering Gradle's consistent resolution mechanism or causing classpath pollution.

## Proposed Changes

### Build Configuration

#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/app/build.gradle.kts)
- Remove `implementation(libs.androidx.ui.test.junit4)` from the `dependencies` block as it belongs to test configurations.
- Consolidate and clean up the test dependencies.
- Ensure all test-related libraries are correctly scoped to `androidTestImplementation` or `testImplementation`.

#### [MODIFY] [gradle/libs.versions.toml](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/gradle/libs.versions.toml)
- Verify and potentially update the `espressoCore` and `uiTestJunit4` versions to ensure compatibility.
- Currently, `espressoCore` is at `3.7.0` and `uiTestJunit4` is at `1.12.0`. I will keep these as they are the latest stable versions, but ensure they are used correctly.

## Verification Plan

### Automated Tests
- Run Gradle sync to verify the resolution error is gone.
- Run `./gradlew :app:assembleDebug` to ensure the project builds correctly.
- Run `./gradlew :app:assembleAndroidTest` to verify test artifacts.

### Manual Verification
- Confirm with the user that the sync error in Android Studio has disappeared.
