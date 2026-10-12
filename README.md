# AniWall Android app

Kotlin + Jetpack Compose starter app with Firebase Email/Password Authentication, Firestore user profiles, Home/Collection/Profile navigation, and a daily 20-coin claim.

## Setup for GitHub / Android Studio
1. Create a Firebase project at https://console.firebase.google.com/.
2. Add an Android app with package name `com.aniwall.aniwall`.
3. Enable **Authentication → Email/Password** and create a **Cloud Firestore** database.
4. Download Firebase `google-services.json` and put it at `app/google-services.json`. Do not commit private keys or unrelated secrets.
5. Open the repository in Android Studio, use JDK 17, sync Gradle, and build.

## Important
- `google-services.json` is intentionally not included. Use the file downloaded from your own Firebase project.
- Add and test Firestore security rules before real users use the app. The daily bonus is a client-side starter implementation and should be moved to a trusted backend/Cloud Function before public launch to prevent abuse.
- Top-up buttons are UI placeholders only; no real money is charged and no coins are purchased yet. Integrate Google Play Billing and verify purchases on a trusted backend before publishing.
- Anime rows are demo placeholders; actual wallpaper images, saving, and collection persistence are not implemented yet.
