# Android TODO App

Basic project to test Firebase integration with native android app.

## Features

- TODO C.R.U.D
- Email or Google auth
- Swipe gestures (Edit or delete TODO)

## Run Locally

1. Install both:

   - [Android Studio](https://developer.android.com/studio) or [VS Code](https://code.visualstudio.com/download)

1. See this [guide](https://developer.android.com/studio/run/managing-avds) on how to run an android app using emulation

1. Clone the project:

   ```bash
   git clone https://github.com/AloisCRR/android-todo-app.git
   ```

1. Add your firebase key file to the project with this [guide](https://firebase.google.com/docs/android/setup)

   - In your firebase console, you should enable:
     - Firebase Auth -> Email and password, Google
     - Firebase Firestore

## Screenshots

| App start                                          | App login                                          |
| -------------------------------------------------- | -------------------------------------------------- |
| ![App start](./screenshots/app_start.png?raw=true) | ![App login](./screenshots/app_login.png?raw=true) |

| Email login                                            | TO-DO list                                          |
| ------------------------------------------------------ | --------------------------------------------------- |
| ![Email login](./screenshots/email_login.png?raw=true) | ![TO-DO list](./screenshots/todo_list.png?raw=true) |

## Tech Stack

| Name                                     | Description                 |
| ---------------------------------------- | --------------------------- |
| [Firebase](https://firebase.google.com/) | Backend-as-a-Service (Baas) |

## Roadmap

- [ ] Improve App design system

- [ ] Refactor architecture

- [ ] Add global state management

- [ ] Better error handling
