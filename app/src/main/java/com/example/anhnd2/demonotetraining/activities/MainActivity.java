package com.example.anhnd2.demonotetraining.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.adapters.NoteAdapter;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpMain;
import com.example.anhnd2.demonotetraining.models.MainModel;
import com.example.anhnd2.demonotetraining.presenters.MainPresenter;
import com.example.anhnd2.demonotetraining.presenters.factories.MainPresenterFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MvpMain.RequiredView {

	public static final String TAG = MainActivity.class.getSimpleName();
	private MvpMain.ProvidedPresenter presenter;
	private NoteAdapter noteAdapter;
	private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		presenter = MainPresenterFactory.getInstance().getMainPresenter(this);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.menu_add_note) {
			startActivity(EditActivity.getStartIntent(this));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			default:
				break;
		}
	}

	@Override
	public void displayNoteList(List<NoteItem> noteItemList) {
		if (noteAdapter == null) {
			noteAdapter = new NoteAdapter(noteItemList);
			recyclerView.setAdapter(noteAdapter);
		} else {
			noteAdapter.setNoteItemList(noteItemList);
			noteAdapter.notifyDataSetChanged();
		}
	}

	private void setupMvp() {
		presenter = new MainPresenter(this);
		presenter.setModel(new MainModel((MvpMain.RequiredPresenter) presenter));
	}

	private void initView() {
		presenter.getNoteItemList();
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
		recyclerView.setLayoutManager(layoutManager);
	}
}
