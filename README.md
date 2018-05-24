## Noted Android App

This is the Android app for the Noted full stack application. This application was built using retrofit and RxJava for handling network calls asynchronously. For local storage of notes and tasks, Room was used as an SQLite wrapper, and changes were emitted via LiveData which the ViewModel would subscribe to. Additionally, Dagger 2 was used for dependency injection. This app was built following Model-View-Presenter, while calls to the local DB was architectured through the Model-View-ViewModel pattern in order to subscribe to LiveData. 
