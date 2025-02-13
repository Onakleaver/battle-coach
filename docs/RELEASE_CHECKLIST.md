# Release Checklist

## Before Release
- [ ] Run all tests
  ```bash
  ./gradlew test
  ./gradlew connectedAndroidTest
  ```
- [ ] Check code coverage
  ```bash
  ./gradlew jacocoTestReport
  ```
- [ ] Update version numbers
  - [ ] `build.gradle`
  - [ ] `README.md`
  - [ ] `CHANGELOG.md`
- [ ] Test on multiple devices
  - [ ] Android 6.0 (API 23)
  - [ ] Android 9.0 (API 28)
  - [ ] Android 13 (API 33)
- [ ] Verify features
  - [ ] Core gameplay
  - [ ] AI opponents
  - [ ] Progression system
  - [ ] Offline mode
  - [ ] Game analysis

## Release Process
1. Create release branch
   ```bash
   git checkout -b release/v1.0.0
   ```
2. Update version
   ```bash
   git commit -am "chore: bump version to 1.0.0"
   ```
3. Tag release
   ```bash
   git tag -a v1.0.0 -m "Release v1.0.0"
   ```
4. Push to trigger workflow
   ```bash
   git push origin v1.0.0
   ```
5. Monitor GitHub Actions

## After Release
- [ ] Verify APK download
- [ ] Test installed APK
- [ ] Update documentation
- [ ] Announce release
  - [ ] GitHub Discussions
  - [ ] Reddit r/chess
  - [ ] Chess.com forums
  - [ ] Discord server 