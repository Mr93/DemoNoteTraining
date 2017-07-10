package com.example.anhnd2.demonotetraining.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anhnd2.demonotetraining.R;

public class EditActivity extends AppCompatActivity {

	public static Intent getStartIntent (Context context){
		Intent intent = new Intent(context, EditActivity.class);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
	}
}
