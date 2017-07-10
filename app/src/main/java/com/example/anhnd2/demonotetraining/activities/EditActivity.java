package com.example.anhnd2.demonotetraining.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_capture:
				break;
			case R.id.menu_chose_color:
				break;
			case R.id.menu_save:
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
