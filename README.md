# 🌟 ContactsKMP
A **Kotlin Multiplatform (KMP)** application for managing contacts with features like fetching, searching, editing, and deleting contacts.

---

## 🚀 Features
- ✅ Fetch contacts from the device (including Gmail contacts)
- ✅ Search contacts with real-time filtering
- ✅ Edit and update contact details
- ✅ Mark contacts as favorite
- ✅ Delete contacts with swipe-to-right
- ✅ Edit contacts with swipe-to-left
- ✅ Jetpack Compose UI with Material 3
- ✅ KMP shared logic for Android and iOS

---

## 📺 Tech Stack
- **Kotlin Multiplatform (KMP)** - Shared business logic
- **Jetpack Compose** - Modern UI for Android
- **SQLDelight** - Local database storage
- **Coroutines + Flow** - Async programming
- **Moko MVVM** - ViewModel, LiveData, and StateFlow
- **Navigation Compose** - Jetpack Compose Navigation
- **Permissions Handling** - Request runtime permissions
- **Gson** - JSON parsing
- **Coil** - Image loading

---

## 🏠 Project Setup

### 👉 Prerequisites
- Android Studio **Giraffe or later**
- Kotlin **1.9+**
- Gradle **8+**
- Xcode (for iOS development)

### 👉 Clone the Repository
```sh
git clone https://github.com/naveenrajElangovan/ContactsKMP.git
cd ContactsKMP
```

### 👉 Build the Project
Run the following command in Android Studio Terminal:
```sh
./gradlew build
```

---

## 💃 Project Structure
```
ContactsKMP
️│── androidApp/       # Android-specific UI
️│── iosApp/           # iOS Swift UI (To be implemented)
️│── shared/           # KMP shared logic
️│   └── src/commonMain/   # Common business logic
️│   └── src/androidMain/  # Android-specific implementations
️│   └── src/iosMain/      # iOS-specific implementations
```

---

## 🛠️ Dependencies
Add the following dependencies in **shared/build.gradle.kts**:

```kotlin
dependencies {
    implementation("dev.icerock.moko:mvvm-core:0.16.1")  // MVVM
    implementation("dev.icerock.moko:mvvm-livedata:0.16.1")
    implementation("com.squareup.sqldelight:runtime:1.5.5") // SQLDelight
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") // Coroutines
    implementation("androidx.compose.material3:material3:1.1.2") // Compose Material3
    implementation("androidx.navigation:navigation-compose:2.6.0") // Compose Navigation
    implementation("com.google.code.gson:gson:2.10") // Gson for JSON Parsing
}
```

---

## 🏃 Running the App

### 👉 Android
1. Open the project in **Android Studio**
2. Run the app on an **emulator or physical device**

### 👉 iOS (Coming Soon)
1. Open `iosApp` in **Xcode**
2. Run the app on a **simulator or iPhone**

---

## 💪 To-Do List
- [x] Implement Contact List
- [x] Add Search Functionality
- [x] Update Contact Details
- [ ] Implement iOS UI
- [ ] Add Unit Testing

---

## 🤝 Contributing
Contributions are welcome! Please open an **issue** or submit a **pull request**.

---

## 📚 License
This project is **open-source** and available under the **MIT License**.

---

### ⭐ **Like the Project? Give it a Star!** 🌟

