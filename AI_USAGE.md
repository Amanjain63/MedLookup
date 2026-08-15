# AI Tool Usage

This project was developed with the assistance of AI tools.

## Tools Used
- **Android Studio Gemini**: Used for generating boilerplate Room entity code and unit test templates.
- **Claude 3.5 Sonnet**: Used for architecting the offline caching strategy and refining the PRD status check.

## Effective Prompt/Workflow
**Workflow**: "Contract-First Refactoring"
1. I asked the AI to analyze the PRD and current code to identify gaps.
2. I then asked it to generate a "Task List" artifact which I used to track progress.
3. For the Room implementation, I provided the domain model and asked for the most efficient mapping to an Entity that would support the "Search" requirement.

## AI Hallucination/Error Case
**Case**: The AI initially suggested using `Hilt` for dependency injection and provided a complex multi-module configuration.
**Observation**: I noticed that applying the Hilt plugin caused a sync error because of an incompatible Kotlin version in the project's baseline.
**Correction**: I decided to pivot to a "Manual Dependency Injection" approach using a singleton `DependencyProvider`. This reduced the build complexity significantly and was more appropriate for the 5-hour timebox, while still satisfying the "no business logic in composables" and "injectable dispatchers" requirements.
