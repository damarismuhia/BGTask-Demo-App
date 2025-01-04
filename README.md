# Overview
Use Android Jetpack’s WorkManager API to schedule necessary background work, such as data backups or fresh content downloads, that keeps running even if the app exits or the device restarts.
- [X] Learn how to create a repository, add an offline cache, and schedule background tasks with WorkManager.
- [X] This app displays a list of videos. The app fetches a list of video URLs from the network using the Retrofit library and displays the list using a LazyColumn.
- Some of the APIs for background processing that Android provides includes:
   1. AlarmManager
   2. JobScheduler
   3. GCMNetworkManager
   4. Broadcast Receiver
# Content
- [X] What is WorkManager and when to use it

What is WorkManager and when to use it
---------------
1. WorkManager - is an Android Jetpack Library that handles background work that needs to run when various constraints are met, regardless of whether the application process is alive or not.
2. Background work can be started when:- 
   - the app is in the background
   - the app is in the foreground, 
   - the app starts in the foreground but goes to the background. 
3. Regardless of what the application is doing, background work should continue to execute, or be restarted if Android kills its process.

    ** When to use WorkManager **
   --------
When the task - 
- [X] Doesn't need to run at a specific time.
- [X] Can be deferred to be executed.
- [X] Is guaranteed to run even after the app is killed or device is restarted.
- [X] Has to meet constraints like battery supply,network availability before execution.
- [X] Ref: https://android-developers.googleblog.com/2018/10/modern-background-execution-in-android.html   

