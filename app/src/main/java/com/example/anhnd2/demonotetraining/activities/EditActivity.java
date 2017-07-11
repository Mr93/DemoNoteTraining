package com.example.anhnd2.demonotetraining.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpEdit;
import com.example.anhnd2.demonotetraining.models.EditModel;
import com.example.anhnd2.demonotetraining.presenters.EditPresenter;
import com.example.anhnd2.demonotetraining.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EditActivity extends AppCompatActivity implements MvpEdit.RequiredView, View.OnClickListener {

	private static String EXTRA_NOTE_ID = "extra_note_id";
	private static String EXTRA_NOTE_ITEM = "extra_note_item";
	private MvpEdit.ProvidedPresenter providedPresenter;
	private TextView txtCreatedTime, txtAlarm, txtDatePicker, txtTimePicker;
	private EditText editTextTitle, editTextContent;
	private LinearLayout llDateTimePickerContainer, llContainAll;
	private ImageView imgHideDateTimePicker;

	public static Intent getStartIntent(Context context) {
		Intent intent = new Intent(context, EditActivity.class);
		return intent;
	}

	public static Intent getStartIntent(Context context, int noteId) {
		Intent intent = new Intent(context, EditActivity.class);
		intent.putExtra(EXTRA_NOTE_ID, noteId);
		return intent;
	}

	public static Intent getStartIntent(Context context, NoteItem noteItem) {
		Intent intent = new Intent(context, EditActivity.class);
		intent.putExtra(EXTRA_NOTE_ITEM, noteItem);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		setupMvp();
		initView();
		getDataFromPresenter();
	}

	@Override
	protected void onStop() {
		super.onStop();
		providedPresenter.forceSave();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_capture:
				break;
			case R.id.menu_chose_color:
				break;
			case R.id.menu_save:
				providedPresenter.forceSave();
				break;
			case R.id.menu_add_note:
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.text_alarm_edit_activity:
				txtAlarm.setVisibility(View.GONE);
				llDateTimePickerContainer.setVisibility(View.VISIBLE);
				break;
			case R.id.img_hide_date_time_picker:
				txtAlarm.setVisibility(View.VISIBLE);
				llDateTimePickerContainer.setVisibility(View.GONE);
				break;
			case R.id.date_picker:
				break;
			case R.id.time_picker:
				break;
			default:
				break;
		}
	}

	@Override
	public void displayData(NoteItem noteItem) {
		editTextTitle.setText(noteItem.title);
		editTextContent.setText(noteItem.content);
		txtCreatedTime.setText(Utils.formatDateTimeString(noteItem.createdTime));
		llContainAll.setBackgroundColor(noteItem.colorId);
		if (noteItem.alarmTime != null) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			txtDatePicker.setText(dateFormat.format(noteItem.alarmTime));
			dateFormat = new SimpleDateFormat("hh:mm");
			txtTimePicker.setText(dateFormat.format(noteItem.alarmTime));
		}
		llContainAll.setEnabled(true);
		initEditTextContent();
		initEditTextTitle();
	}

	@Override
	public void noteUpdated() {
//		Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
	}

	private void setupMvp() {
		providedPresenter = new EditPresenter(this);
		providedPresenter.setModel(new EditModel((MvpEdit.RequiredPresenter) providedPresenter));
	}

	private void initView() {
		txtAlarm = (TextView) findViewById(R.id.text_alarm_edit_activity);
		txtAlarm.setOnClickListener(this);
		txtCreatedTime = (TextView) findViewById(R.id.text_created_date_edit_activity);
		txtDatePicker = (TextView) findViewById(R.id.date_picker);
		txtDatePicker.setOnClickListener(this);
		txtTimePicker = (TextView) findViewById(R.id.time_picker);
		txtTimePicker.setOnClickListener(this);
		llContainAll = (LinearLayout) findViewById(R.id.ll_contain_all);
		llContainAll.setEnabled(false);
		llDateTimePickerContainer = (LinearLayout) findViewById(R.id.layout_date_time_picker);
		imgHideDateTimePicker = (ImageView) findViewById(R.id.img_hide_date_time_picker);
		imgHideDateTimePicker.setOnClickListener(this);
		editTextTitle = (EditText) findViewById(R.id.edit_text_title_edit_activity);
		editTextContent = (EditText) findViewById(R.id.edit_text_content_edit_activity);
	}

	private void initEditTextContent() {
		editTextContent.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				providedPresenter.updateContent(editable.toString());
			}
		});
	}

	private void initEditTextTitle() {
		editTextTitle.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				providedPresenter.updateTitle(editable.toString());
			}
		});
	}


	private void getDataFromPresenter() {
		if (getIntent() != null && getIntent().getIntExtra(EXTRA_NOTE_ID, -1) != -1) {
			providedPresenter.getNoteById(getIntent().getIntExtra(EXTRA_NOTE_ID, -1));
		} else if (getIntent() != null && getIntent().getParcelableExtra(EXTRA_NOTE_ITEM) != null) {
			displayData((NoteItem) getIntent().getParcelableExtra(EXTRA_NOTE_ITEM));
			providedPresenter.setNoteItemData((NoteItem) getIntent().getParcelableExtra(EXTRA_NOTE_ITEM));
		} else {
			providedPresenter.createNewNote();
		}
	}
}
