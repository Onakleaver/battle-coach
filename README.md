# Battle Coach - AI Chess Training App

<p align="center">
  <img src="docs/images/logo.png" width="200" alt="Battle Coach Logo">
</p>

Battle Coach is an AI-powered chess training app that combines Solo Leveling-inspired progression with advanced chess engines (Stockfish & Maia) to create a unique learning experience.

## ✨ Features

- 🤖 **Advanced AI Opponents**
  - Stockfish engine for precise analysis
  - Maia AI for human-like play
  - Personality-driven bot interactions

- 🏆 **Progression System**
  - E-Rank to S-Rank advancement
  - Boss battles for rank promotion
  - Custom chessboard themes per rank

- 📱 **Modern UI/UX**
  - Material Design 3 with dynamic theming
  - Smooth animations and transitions
  - Offline-first architecture

- 🎮 **Game Modes**
  - Blitz (5+0, 3+2, 3+0)
  - Rapid (10+0, 15+10, 30+0)
  - Classical (45+15, 60+0)

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 11 or later
- Android SDK 21+

### Building the Project
1. Clone the repository: 
```bash
git clone https://github.com/yourusername/battle-coach.git
cd battle-coach
```

2. Open in Android Studio
3. Sync Gradle files
4. Run on emulator or device

### Testing
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 🏗️ Architecture

### Project Structure
```
app/
├── data/
│   ├── local/      # Room database, DAOs
│   ├── remote/     # API clients, network models
│   └── repository/ # Data repositories
├── domain/
│   ├── engine/     # Chess engine integration
│   ├── bot/        # AI personalities
│   └── rank/       # Ranking system
└── ui/
    ├── screens/    # Main UI screens
    ├── components/ # Reusable components
    └── theme/      # App styling
```

### Tech Stack
- **UI**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Database**: Room
- **Network**: Retrofit + OkHttp
- **Testing**: JUnit, Espresso
- **Chess Engines**: Stockfish, Maia

## 🤝 Contributing

We welcome contributions! See our [Contributing Guidelines](CONTRIBUTING.md) for details.

### Issue Reporting

When creating an issue, please include:

- Clear description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Device/Android version info
- Screenshots if applicable

### Pull Request Process

1. Ensure your code follows style guidelines
2. Update documentation if needed
3. Add tests for new features
4. Verify all tests pass
5. Request review from maintainers

## 📱 Screenshots

<p float="left">
  <img src="docs/images/screenshot-1.png" width="200" alt="Game Screen">
  <img src="docs/images/screenshot-2.png" width="200" alt="Training Screen">
  <img src="docs/images/screenshot-3.png" width="200" alt="Profile Screen">
</p>

## 📄 License

Battle Coach is MIT licensed. See [LICENSE](LICENSE) for details.

## 🙏 Acknowledgments

- [Stockfish](https://stockfishchess.org/) - Chess engine
- [Maia Chess](https://maiachess.com/) - Human-like AI
- [Chess.com API](https://www.chess.com/news/view/published-data-api)
- [Lichess API](https://lichess.org/api)

## Development Setup

1. Clone your fork:
```