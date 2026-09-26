# JustWords2 - Offline-First Language Learning Android Client

## Overview

JustWords2 is a commercial-grade, multi-module Android application designed for interactive vocabulary acquisition and language learning. Built entirely in Kotlin and Jetpack Compose, the project strictly adheres to Clean Architecture principles, Modular Design, and the Model-View-Intent (MVI) architectural pattern.

The application is engineered with an **Offline-First** mindset, ensuring uninterrupted user experience regardless of network availability, backed by an asynchronous background synchronization engine and a dedicated Ktor REST API.

*(Note: While the default vocabulary datasets are configured for English-Polish learning, the underlying domain model is language-agnostic).*

## Visuals

![Main Screen](https://github.com/user-attachments/assets/040a0df4-1cfd-40d4-a19a-36c5fe0adb32)

| Authentication Flow | Learning & Analytics |
| :---: | :---: |
| ![Authentication](https://github.com/user-attachments/assets/5a31ac71-6b46-47dd-b1da-c687ac235781) | ![Gameplay and Stats](https://github.com/user-attachments/assets/4c26e715-4e43-425b-a21d-526dd5941097) |

## Architectural Highlights & Engineering Practices

* **Strict Multi-Module Clean Architecture:** The codebase is decoupled into feature modules (`:auth`, `:word`, `:shop`, `:user`) and shared foundation modules (`:core`). Each feature is further divided into `domain`, `data`, and `presentation` layers. Domain modules are pure JVM libraries (`org.jetbrains.kotlin.jvm`) with zero Android framework dependencies, guaranteeing business logic isolation and testability.
* **Scalable Build System (Gradle Convention Plugins):** Build logic is centralized in a dedicated `build-logic` composite build. Custom convention plugins (e.g., `AndroidFeatureUiConventionPlugin`, `AndroidRoomConventionPlugin`, `JvmKtorConventionPlugin`) alongside a Gradle Version Catalog (`libs.versions.toml`) and Type-Safe Project Accessors eliminate build script boilerplate across all 17 modules.
* **Offline-First & Background Synchronization:** User progress and learning history are persisted locally first via Room (`OfflineFirstWordHistoryRepository`). If a network call fails, failed mutations are queued in a dedicated pending sync table (`HistoryPendingSyncEntity`) and scheduled for background execution via Android `WorkManager` (`SyncHistoryWorkerScheduler`) with exponential backoff and network constraints.
* **Stateless JWT Authentication & Auto-Refresh:** Network communication is powered by the Ktor CIO engine. The `HttpClientFactory` implements an automated Bearer token interceptor that transparently intercepts `401 Unauthorized` responses, hits the `/accessToken` refresh endpoint, and updates session credentials without disrupting active user flows.
* **Security by Design (Encrypted Storage):** Sensitive session payloads (`AuthInfo` containing JWT access and refresh tokens) are serialized and persisted exclusively within hardware-backed `EncryptedSharedPreferences` utilizing `AES256_SIV` (key encryption) and `AES256_GCM` (value encryption) schemes.
* **Unidirectional Data Flow (MVI) & Custom Design System:** The presentation layer is driven by Jetpack Compose and Material 3, utilizing a reusable `:core:presentation:designsystem` module. ViewModels expose immutable UI states and process sealed `Action` intents, while one-off side effects (navigation, toasts) are dispatched via Kotlin `Channel` and collected safely within the lifecycle using a custom `ObserveAsEvents` composable.
* **Type-Safe Functional Error Handling:** Exceptions are caught at the infrastructure boundary (`safeCall`) and mapped to a strongly-typed, domain-specific `Result<D, E: Error>` monad (`DataError.Network`, `DataError.Local`), preventing raw exceptions from leaking into the domain or UI layers.

## Project Structure

```text
JustWords2
├── app/                        # Application entry point, DI root, and Navigation Graph
├── build-logic/                # Custom Gradle Convention Plugins for multi-module setup
├── core/
│   ├── domain/                 # Pure JVM shared domain models, repositories, and Result monad
│   ├── data/                   # Ktor HTTP client factory, EncryptedSessionStorage, Offline-first repos
│   ├── database/               # Room database, DAOs, Entities, and BSON ObjectId generation
│   └── presentation/
│       ├── designsystem/       # Reusable Compose UI components, Typography, and Theme
│       └── ui/                 # UI utilities (UiText, ObserveAsEvents, Error mappers)
├── auth/                       # Registration, Login, and UserDataValidator (:data, :domain, :presentation)
├── shop/                       # Remote vocabulary pack browsing and downloading (:data, :domain, :presentation)
├── word/                       # Core learning loop, flashcards, and WorkManager sync (:data, :domain, :presentation)
└── user/                       # User profile, streaks, and Canvas-based statistical graphs (:data, :domain, :presentation)
```

## Technology Stack

* **Language:** Kotlin 1.9+ (Coroutines, Flow, Kotlinx Serialization)
* **UI Framework:** Jetpack Compose, Material 3, Custom Canvas Graphics, Splash Screen API
* **Dependency Injection:** Koin (with ViewModel and WorkManager integration)
* **Networking:** Ktor Client (CIO Engine, Content Negotiation, Auth Bearer Plugin, Logging)
* **Local Persistence:** Room Database (SQLite), Jetpack DataStore (Preferences), AndroidX Security Crypto (`EncryptedSharedPreferences`)
* **Background Processing:** AndroidX WorkManager (CoroutineWorker)
* **Build System:** Gradle Kotlin DSL, Version Catalogs, Convention Plugins, KSP (Kotlin Symbol Processing)
* **Logging:** Timber

## Getting Started

### Prerequisites

* Android Studio Iguana (or newer)
* JDK 11 or higher (configured via Gradle toolchains/compileOptions)
* Local instance of the **JustWords2 Backend API** running for network features

### Running the Application

1. **Clone the client repository:**
   ```bash
   git clone https://github.com/Wilie12/JustWords2.git
   ```

2. **Set up the Backend API:**
   The application compiles and launches out of the box. However, to perform authentication, fetch vocabulary sets from the Shop, or synchronize learning history, you must run the dedicated Ktor backend server:
    * Clone and follow the setup instructions in the [ktor-justwords2 API Repository](https://github.com/Wilie12/ktor-justwords2).

3. **Configure the Local Base URL:**
   Open `build-logic/convention/src/main/java/com/willaapps/convention/BuildTypes.kt` and update the `BASE_URL` build config field in `configureDebugBuildType()` to match your local machine's IP address (or `10.0.2.2` if running on the Android Emulator):
   ```kotlin
   private fun BuildType.configureDebugBuildType() {
       buildConfigField("String", "BASE_URL", "\"http://YOUR_LOCAL_IP:8080\"")
   }
   ```

4. **Build and Run:**
   Execute the build using the repository-bound Gradle wrapper or run the `app` configuration directly from Android Studio:
   ```bash
   ./gradlew assembleDebug
   ```