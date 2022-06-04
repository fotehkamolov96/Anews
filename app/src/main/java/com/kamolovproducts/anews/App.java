package com.kamolovproducts.anews;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         *
         Application pre-launch processing.
         Here you can use your part of the code you want to run on cold start too, for example,
         it can be FirebaseAnalytics or Firebase Crashlytics.
         *
         FirebaseApp.initializeApp(this);
         FirebaseAnalytics.getInstance(this);
         *
         */

    }

}