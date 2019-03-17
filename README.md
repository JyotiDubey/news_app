# Introduction
Simple News app using the News API

# Features:
Endless scrolling for news

Allows user to see all the news that appeared during his/her last app session even when the device is not connected to internet

# Overview

* RxJava - Used RxJava for easier concurrency and thread management with some operatores to simplify the tasks

* Dagger - For dependency injection 

* Retrofit - Used for easier networking to fetch json and convert them to the required POJOs

* Room DB- Used to store the news for the app to work offline. (Could be replaced with used Retrofit caching)

* Glide- For loading Images efficienty. Although the repo also contains the custom image caching implementation mechanism using
  
  The link to the custom image loader can be found [here](https://github.com/jyotid/news_app_grab_interview/blob/master/app/src/main/java/com/grab/news/data/imageloader/DefaultImageLoader.kt)

* LRU Cache and Okhttp to fetch input stream for Bitmaps

* Ability to scroll and  avoid unnecessary requests for the same api hit

# Architecture

This project used MVVM architecture to avoid logical code in Activity and separate data related code.Loose coupling by making code dependent on interfaces rather than implementations eg: DataManager, ApiHelper etc
Activities are only responsible to wait on event from its respective viewmodel and change the content on the screen.

Test - View model test is achieved by injecting MockDataSource

# Possible Improvements

* Whenever the device gets back the connectivity automatic news fetch will sound like a good feature

* Using work manager to sync the news at particular time in the day (Preferebly in morning considering the app will have more users in that time)

* Test - Needs to add integration tests to avoid breakage due to 3rd parties and improve on View model Unit tests by mocking Room DB

* Better Error handling 














