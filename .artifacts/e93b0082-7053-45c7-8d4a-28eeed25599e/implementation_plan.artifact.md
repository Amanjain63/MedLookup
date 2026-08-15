# Implementation Plan - Fix Dependency Conflict for espresso-core

The build is failing due to a version conflict for `androidx.test.espresso:espresso-core`. This is caused by `androidx.compose.ui:ui-test-junit4` being incorrectly included in the `implementation` configuration of the `:app` module, which pulls it into the production runtime classpath. This triggers Gradle's "consistent resolution" mechanism, which forces the test classpath to use the same (older) version of Espresso found in the production classpath, conflicting with the explicitly requested newer version.

## Proposed Changes

### Build Configuration

#### [MODIFY] [build.gradle.kts](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/app/build.gradle.kts)
- Remove `libs.androidx.ui.test.junit4` from the `implementation` block.
- Consolidate and clean up the `dependencies` block:
    - Remove duplicate `androidTestImplementation(platform(libs.androidx.compose.bom))` calls.
    - Remove duplicate `androidTestImplementation` for `ui-test-junit4`.
    - Ensure all test-related libraries are in `androidTestImplementation` or `testImplementation`.
    - Use the version-catalog-managed `libs.androidx.compose.ui.test.junit4` consistently.

#### [MODIFY] [libs.versions.toml](file:///C:/Users/Dell/AndroidStudioProjects/MedLookup/gradle/libs.versions.toml)
- Remove the redundant and potentially problematic `androidx-ui-test-junit4` (version 1.12.0) if it's not needed, or align it with the BOM version. Given the error, it's safer to rely on the BOM-managed version.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify the project builds.
- Run `./gradlew :app:assembleDebugAndroidTest` to verify test artifacts are generated without version conflicts.

### Manual Verification
- Synchronize the project with Gradle in Android Studio.
