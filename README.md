# Android News App

A modern Android news application built with Kotlin and Jetpack Compose, following clean architecture principles with modular design.

## 📱 Features

- **Biometric Authentication**: Secure app access using fingerprint authentication
- **Multi-source News**: Support for different news sources (BBC, CNN) via product flavors
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Clean Architecture**: Modular design with data, domain, and presentation layers
- **Error Handling**: User-friendly error messages with centralized error mapping
- **Image Loading**: Efficient image loading with caching using Coil
- **Network Debugging**: Integrated Chucker for network inspection (debug builds)

## 🏗️ Architecture

This project follows **Clean Architecture** principles with a **modular approach**:

### Module Structure

```
📦 android-news-app
├── 📁 app (Presentation Layer)
│   ├── UI Components (Compose)
│   ├── ViewModels
│   ├── Navigation
│   ├── Dependency Injection
│   └── Authentication
├── 📁 data (Data Layer)
│   ├── Remote API
│   ├── DTOs & Mappers
│   ├── Repository Implementations
│   └── Error Handling
└── 📁 domain (Domain Layer)
    ├── Models
    ├── Repository Interfaces
    ├── Use Cases
    └── Business Logic
```

### Architecture Benefits

- **Separation of Concerns**: Each module has a specific responsibility
- **Testability**: Easy to unit test each layer independently
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Easy to add new features and modules
- **Reusability**: Domain and data modules can be reused across different UI implementations

## 🛠️ Tech Stack

### Core Libraries

- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit for Android
- **Material 3**: Google's latest design system
- **Coroutines**: Asynchronous programming
- **StateFlow**: Reactive state management

### Networking

- **Retrofit**: HTTP client for REST API calls
- **Moshi**: JSON serialization/deserialization
- **OkHttp**: Underlying HTTP client

### Dependency Injection

- **Koin**: Lightweight dependency injection framework
  - `koin-core`: Core DI functionality
  - `koin-android`: Android-specific extensions
  - `koin-androidx-compose`: Compose integration

### Image Loading

- **Coil**: Modern image loading library for Android
  - `coil-compose`: Jetpack Compose integration
  - `coil-network-okhttp`: Network layer integration

### Authentication

- **AndroidX Biometric**: Biometric authentication (fingerprint, face, etc.)

### Testing

- **JUnit**: Unit testing framework
- **MockK**: Mocking library for Kotlin
- **Coroutines Test**: Testing utilities for coroutines

### Development Tools

- **Chucker**: Network inspector for debugging HTTP requests/responses
- **Shimmer**: Loading placeholder animations
- **Navigation Compose**: Type-safe navigation for Compose

## 🌐 News API Integration

This app integrates with [NewsAPI.org](https://newsapi.org/docs/endpoints/top-headlines) to fetch news articles.

### API Endpoint Used

- **Top Headlines**: `GET /v2/top-headlines`
  - Fetches breaking news headlines specific sources
  - Returns articles with title, description, URL, image, publication date, and source

### API Features Used

- **Source Filtering**: Fetch news from specific sources (BBC, CNN)
- **Sorting**: Articles sorted by publication date
- **Error Handling**: Proper HTTP error code handling

## 🔑 Setup Instructions

### Prerequisites

- Android Studio Narwhal 3 Feature Drop
- Android SDK 27 or higher
- JDK 11 or higher

### API Key Configuration

1. Get your free API key from [NewsAPI.org](https://newsapi.org/register)

2. Create or update the `local.properties` file in the project root:

```properties
# NewsAPI.org API Key
NEWS_API_KEY=your_api_key_here
```

3. The API key will be automatically injected into the build configuration and made available as `BuildConfig.NEWS_API_KEY`

**⚠️ Important Notes:**
- If the API key is not configured, the app will show a user-friendly message instructing you to add it

### Building the Project

1. Clone the repository
2. Open in Android Studio
3. Add your API key to `local.properties`
4. Sync project with Gradle files
5. Run the app

## 🎯 Product Flavors

The app includes two product flavors to demonstrate multi-source news aggregation:

### Available Flavors

#### 1. BBC Flavor (`bbc`)
- **Application ID**: `com.prado.eduardo.luiz.newsapp.bbc`
- **News Source**: BBC News
- **BuildConfig**: `SOURCES = "bbc-news"`

#### 2. CNN Flavor (`cnn`)
- **Application ID**: `com.prado.eduardo.luiz.newsapp.cnn`
- **News Source**: CNN
- **BuildConfig**: `SOURCES = "cnn"`

### Building Specific Flavors

```bash
# Build BBC version
./gradlew assembleBbcDebug

# Build CNN version
./gradlew assembleCnnDebug

# Install BBC version
./gradlew installBbcDebug

# Install CNN version
./gradlew installCnnDebug
```

## 🧪 Testing

### Unit Tests

- **Repository Tests**: Mock API responses and test data layer
- **Use Case Tests**: Test business logic in isolation
- **ViewModel Tests**: Test UI state management and user interactions
- **Error Mapper Tests**: Test error handling and user messages

### Test Coverage

- Data layer: Repository implementations and mappers
- Domain layer: Use cases and business logic
- Presentation layer: ViewModels and UI state
- Authentication: Biometric authentication flow

### Running Tests

```bash
# Run all unit tests
./gradlew test

# Run tests for specific flavor
./gradlew testBbcDebugUnitTest

# Run tests with coverage
./gradlew testDebugUnitTestCoverage
```

## 🚀 Getting Started

1. **Clone the repository**
2. **Get your NewsAPI key** from https://newsapi.org/register
3. **Add API key** to `local.properties`
4. **Choose your flavor**: Decide whether you want BBC or CNN news
5. **Build and run** the app
6. **Authenticate** using your fingerprint (if available)
7. **Browse news** from your selected source

**Note**: This app requires an active internet connection and a valid NewsAPI.org API key to function properly.
