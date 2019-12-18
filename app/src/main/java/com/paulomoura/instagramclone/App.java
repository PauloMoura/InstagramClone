package com.paulomoura.instagramclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wSxtK55NKk2cMFdLVnDI6SILxY0jBQkndkkT1TjX")
                .clientKey("AlgR0kADVWJ8jjjQkzHruoTX5TKgjojwhDi2BzUr")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
