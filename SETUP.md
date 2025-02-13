# Setting Up Battle Coach for Development

This guide will help you set up Battle Coach for local development.

## Prerequisites

### Required Software
- Android Studio Arctic Fox or later
- JDK 11 or later
- Git
- Android SDK (API 21+)

### Recommended Tools
- [Scrcpy](https://github.com/Genymobile/scrcpy) for device mirroring
- [Android Studio Chess Plugin](https://plugins.jetbrains.com/plugin/13814-chess) for PGN editing

## Setup Steps

### 1. Clone the Repository
```bash
# Clone the repo
git clone https://github.com/yourusername/battle-coach.git

# Navigate to project directory
cd battle-coach

# Set up git hooks
./gradlew installGitHooks
```

### 2. Configure API Keys
Create a `local.properties` file in the project root:
```properties
sdk.dir=/path/to/your/Android/sdk
CHESS_COM_API_KEY=your_key_here
LICHESS_API_KEY=your_key_here
```

### 3. Build the Project
```bash
# Clean and build
./gradlew clean build

# Run tests
./gradlew test
```

### 4. Run the App
- Open in Android Studio
- Select a device/emulator
- Click "Run" (⇧F10)

## Development Workflow

### Branch Naming
- Features: `feature/description`
- Bugs: `fix/description`
- Docs: `docs/description`

### Commit Messages
Follow the [Conventional Commits](https://www.conventionalcommits.org/) format:
```
feat: add new board theme
fix: correct move validation
docs: update setup instructions
```

### Pull Request Process
1. Create feature branch
2. Implement changes
3. Run tests
4. Update documentation
5. Submit PR
6. Address review feedback

## Common Issues

### Build Failures
```bash
# Invalidate caches
File -> Invalidate Caches / Restart

# Clean project
./gradlew clean
```

### Missing Dependencies
```bash
# Sync project with Gradle files
Tools -> Android -> Sync Project with Gradle Files
```

## Testing

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# Single test class
./gradlew test --tests "com.battlecoach.YourTestClass"
```

### Test Coverage
```bash
# Generate coverage report
./gradlew jacocoTestReport
```

## Debugging

### Logging
Use Timber for logging:
```kotlin
Timber.d("Debug message")
Timber.i("Info message")
Timber.e("Error message")
```

### Performance Monitoring
Use the built-in PerformanceMonitor:
```kotlin
@Composable
fun YourComposable() {
    MeasureComposablePerformance("screen_name") {
        // Your composable content
    }
}
``` 