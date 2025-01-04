1. Start by declaring an intent filter for send in android manifest since we aim at Sending image to our app from a browser


# WorkManager Classes
- [X] Worker
- [X] WorkRequest
- [X] WorkManager

Content 
---------------
- [X] How to add WorkManager to your project.
- [X] How to schedule a simple task.
- [X] How to configure input and output parameters for workers.
- [X] How to chain workers.

* To implement the above. we will develop an app that blurs photos and saves the results to a file. The app will focus on:
  1. adding WorkManager to the app, 
  2. creating workers to clean up temporary files that are created in blurring an image, 
  3. blurring an image
  4. saving a final copy of the image which you can view when you click the See File button.
  5. monitoring the status of the background work and update the app's UI accordingly.

