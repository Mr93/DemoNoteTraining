package com.example.anhnd2.demonotetraining.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by anhnd2 on 7/10/2017.
 */

public class MyApplication extends Application {

	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		this.context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
}
