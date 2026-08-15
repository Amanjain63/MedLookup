# Architectural & Product Decisions

This file explains some important decisions I made while building MedLookup.

## 1. How does the Detail Screen get the medicine?

**Decision:** The Detail Screen gets the medicine using its ID instead of receiving the complete object from the Search Screen.

**Reason:** Passing the complete object would be simpler, but using the ID makes the Detail Screen more independent. It also works better if the app is opened from a direct link or recreated after process death. Since the medicine is stored locally, getting it by ID is also fast when the data is already cached.

## 2. What data is stored offline?

**Decision:** The complete Medicine data is stored in the Room database.

**Reason:** Medicine information does not change very frequently, so keeping it locally makes sense. After a successful API response, the data is saved in Room and can be used when the app is offline.

For this project, I did not add automatic expiry. In a production app, I would add a last updated time and refresh old data after a fixed period.

## 3. How do we show no results vs an error?

**Decision:** These are handled as different UI states.

**Reason:** No results is not an error. It means the request was successful but there was no matching medicine. A network or server problem is an actual error.

So the UI shows:
- No results → "No results found"
- Request failed → Error message with a Retry option

## 4. How is search handled while typing?

**Decision:** I used a 500ms debounce on the search query.

**Reason:** Without debounce, every keystroke could trigger an API request. For example, typing "aspirin" could result in multiple requests.

The debounce waits until the user stops typing for a short time before starting the search. This reduces unnecessary API calls while keeping the search responsive.

## 5. How is the data layer structured?

**Decision:** I used a Repository pattern with a `MedicineRepository` interface.

**Reason:** The UI does not need to know whether the data is coming from the API or the local database.

The repository handles:
- API calls
- Room database
- Deciding where the data should come from

If another data source is added later, it can be handled inside the repository without making major changes to the UI.

## 6. What did I not build?

Because the project had a 5-hour time limit, I focused on the main requirements instead of adding extra complexity.

I did not add:
- Hilt dependency injection
- Multi-module architecture
- Search suggestions
- Recently viewed medicines
- Advanced animations

I used a simple `DependencyProvider` instead of Hilt to keep the project easier to configure and avoid unnecessary build complexity.

With another five hours, I would mainly work on:
1. Adding Hilt for dependency injection.
2. Adding search history/suggestions.
3. Adding a recently viewed section.
4. Improving list and screen animations.
5. Adding more tests for edge cases.