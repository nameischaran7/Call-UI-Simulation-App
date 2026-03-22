Call UI Simulation App

This Android application simulates a complete phone calling experience using Kotlin and MVVM architecture.

Features:
- Dial pad with number input
- Outgoing call screen
- Incoming call simulation
- Active call screen with timer
- Mute and Speaker toggles
- Real call trigger using Android Intent

Architecture:
- MVVM (ViewModel + LiveData)
- Fragment-based navigation
- State-driven UI (Idle → Calling → Ringing → Active → Ended)

Note:
This app primarily simulates call flow for UI and state management purposes. Real calling is triggered using system intent and handled by the default dialer.

Tech Stack:
- Kotlin
- Android SDK
- ViewModel + LiveData
