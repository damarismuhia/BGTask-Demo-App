Problem Statement
---
* Image editing app that lets you put filters on images and upload them to the web for the world to see. 
* You want to create a series of background tasks with some constraints that:- 
  1. Applies the filters - sufficient battery constraint
  2. Compresses the images - enough storage space constraint
  3. Uploads the images - network connection constraint




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

Hilt: https://tanyatechzone.com/2023/07/28/basics-of-dependency-injection-in-android-using-hilt-hilt-part-1/