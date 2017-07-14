package com.example.anhnd2.demonotetraining.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.adapters.ChoseColorAdapter;
import com.example.anhnd2.demonotetraining.adapters.ShowImageOfNoteAdapter;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpEdit;
import com.example.anhnd2.demonotetraining.models.EditModel;
import com.example.anhnd2.demonotetraining.presenters.EditPresenter;
import com.example.anhnd2.demonotetraining.utils.Constants;
import com.example.anhnd2.demonotetraining.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements MvpEdit.RequiredView, View.OnClickListener {

	public static final String TAG = EditActivity.class.getSimpleName();
	private static final int REQUEST_IMAGE_CAPTURE = 1;
	private static final int REQUEST_IMAGE_GALLERY = 2;
	private static String EXTRA_NOTE_ITEM = "extra_note_item";
	private static String EXTRA_NOTE_ID = "extra_note_id";
	private MvpEdit.ProvidedPresenter providedPresenter;
	private TextView txtCreatedTime, txtAlarm;
	private EditText editTextTitle, editTextContent;
	private LinearLayout llDateTimePickerContainer, llBottomNavigationBar;
	private ImageView imgHideDateTimePicker, imgBack, imgForward, imgDelete, imgShare;
	private ScrollView scrollViewContainContent;
	private GridView gridViewImageForNote;
	private ShowImageOfNoteAdapter showImageOfNoteAdapter;
	private String tempImagePath;
	private Spinner datePicker, timePicker;
	private String[] listDatePicker, listTimePicker;
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;
	private int currentDatePickerIndex = 0, currentTimePickerIndex = 0;

	private DatePickerDialog.OnDateSetListener customDateSetListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, i);
			calendar.set(Calendar.MONTH, i1);
			calendar.set(Calendar.DAY_OF_MONTH, i2);
			providedPresenter.updateDateAlarm(calendar.getTime());
			setOtherDate(calendar.getTime());
			currentDatePickerIndex = 3;
		}
	};

	private TimePickerDialog.OnTimeSetListener customOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker timePicker, int i, int i1) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, i);
			calendar.set(Calendar.MINUTE, i1);
			providedPresenter.updateTimeAlarm(i, i1);
			setOtherTime(calendar.getTime());
			currentTimePickerIndex = 4;
		}
	};

	private AdapterView.OnItemSelectedListener dateItemSelectedListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> adapterView, final View view, int i, final long l) {
			switch (i) {
				case 0:
					providedPresenter.updateDateAlarm(Utils.getCurrentDate());
					currentDatePickerIndex = i;
					break;
				case 1:
					providedPresenter.updateDateAlarm(Utils.getTomorrow());
					currentDatePickerIndex = i;
					break;
				case 2:
					providedPresenter.updateDateAlarm(Utils.getNextThursday());
					currentDatePickerIndex = i;
					break;
				case 3:
					datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialogInterface) {
							datePicker.setSelection(currentDatePickerIndex);
						}
					});
					datePickerDialog.show();
					break;
				default:
					providedPresenter.updateDateAlarm(Utils.getCurrentDate());
					currentDatePickerIndex = i;
					break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	};

	private AdapterView.OnItemSelectedListener timeOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			switch (i) {
				case 0:
					providedPresenter.updateTimeAlarm(Constants.HOUR_9, Constants.MIN_0);
					currentTimePickerIndex = i;
					break;
				case 1:
					providedPresenter.updateTimeAlarm(Constants.HOUR_13, Constants.MIN_0);
					currentTimePickerIndex = i;
					break;
				case 2:
					providedPresenter.updateTimeAlarm(Constants.HOUR_17, Constants.MIN_0);
					currentTimePickerIndex = i;
					break;
				case 3:
					providedPresenter.updateTimeAlarm(Constants.HOUR_20, Constants.MIN_0);
					currentTimePickerIndex = i;
					break;
				case 4:
					timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialogInterface) {
							timePicker.setSelection(currentTimePickerIndex);
						}
					});
					timePickerDialog.show();
					break;
				default:
					providedPresenter.updateTimeAlarm(Constants.HOUR_9, Constants.MIN_0);
					currentTimePickerIndex = i;
					break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	};


	public static Intent getStartIntent(Context context) {
		Intent intent = new Intent(context, EditActivity.class);
		return intent;
	}

	public static Intent getStartIntent(Context context, NoteItem noteItem) {
		Intent intent = new Intent(context, EditActivity.class);
		intent.putExtra(EXTRA_NOTE_ITEM, noteItem);
		return intent;
	}

	public static Intent getStartIntent(Context context, int noteId) {
		Intent intent = new Intent(context, EditActivity.class);
		intent.putExtra(EXTRA_NOTE_ID, noteId);
		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		initView();
		initSpinner();
		initBottomNavigationBar();
		setupMvp();
		getDataFromPresenter();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: aaaa");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop: ");
		providedPresenter.forceSave();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE) {
			if (resultCode == RESULT_OK) {
				providedPresenter.updateImage(tempImagePath);
				Log.d(TAG, "onActivityResult: " + tempImagePath);
//				showImageOfNoteAdapter.addImagePath(tempImagePath);
			} else {
				Utils.deleteFile(tempImagePath);
			}
		} else if (requestCode == REQUEST_IMAGE_GALLERY) {
			if (resultCode == RESULT_OK) {
				Uri imageUri = data.getData();
				providedPresenter.updateImage(imageUri.toString());
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit, menu);
		if (llBottomNavigationBar.getVisibility() == View.GONE) {
			MenuItem item = menu.findItem(R.id.menu_add_note);
			item.setVisible(false);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_capture:
				addPictureForNote();
				break;
			case R.id.menu_chose_color:
				showChoseColorDialog();
				break;
			case R.id.menu_save:
				providedPresenter.forceSave();
				break;
			case R.id.menu_add_note:
				createNewNote();
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
				providedPresenter.removeAlarm();
				break;
			case R.id.image_back_edit_note:
				providedPresenter.loadPreviousNote();
				break;
			case R.id.image_forward_edit_note:
				providedPresenter.loadNextNote();
				break;
			case R.id.image_delete_edit_note:
				providedPresenter.deleteNoteById();
				break;
			case R.id.image_share_edit_note:
				shareNote();
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
		scrollViewContainContent.setBackgroundColor(noteItem.colorId);
		if (noteItem.alarmTime != null) {
			setOtherDate(noteItem.alarmTime);
			setOtherTime(noteItem.alarmTime);
			txtAlarm.setVisibility(View.GONE);
			llDateTimePickerContainer.setVisibility(View.VISIBLE);
		}
		datePicker.setOnItemSelectedListener(dateItemSelectedListener);
		timePicker.setOnItemSelectedListener(timeOnItemSelectedListener);
		showImageOfNoteAdapter.setListImagePath(noteItem.bitmapPathList);
		scrollViewContainContent.setEnabled(true);
		addTextWatcherForEditTextContent();
		addTextWatcherForEditTextTitle();
	}

	@Override
	public void noteUpdated() {

//		Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void noteDeleted() {
		finish();
	}

	@Override
	public void disableBackButton() {
		if (imgBack != null) {
			imgBack.setEnabled(false);
			imgBack.setAlpha(50);
		}
	}

	@Override
	public void disableForwardButton() {
		if (imgForward != null) {
			imgForward.setEnabled(false);
			imgForward.setAlpha(50);
		}
	}

	@Override
	public Context getActivityContext() {
		return this;
	}

	private void setupMvp() {
		providedPresenter = new EditPresenter(this);
		providedPresenter.setModel(new EditModel((MvpEdit.RequiredPresenter) providedPresenter));
	}

	private void initView() {
		txtAlarm = (TextView) findViewById(R.id.text_alarm_edit_activity);
		txtAlarm.setOnClickListener(this);
		txtCreatedTime = (TextView) findViewById(R.id.text_created_date_edit_activity);
		scrollViewContainContent = (ScrollView) findViewById(R.id.sv_contain_all);
		scrollViewContainContent.setEnabled(false);
		llDateTimePickerContainer = (LinearLayout) findViewById(R.id.layout_date_time_picker);
		imgHideDateTimePicker = (ImageView) findViewById(R.id.img_hide_date_time_picker);
		imgHideDateTimePicker.setOnClickListener(this);
		editTextTitle = (EditText) findViewById(R.id.edit_text_title_edit_activity);
		editTextContent = (EditText) findViewById(R.id.edit_text_content_edit_activity);
		gridViewImageForNote = (GridView) findViewById(R.id.grid_view_image_edit_note);
		showImageOfNoteAdapter = new ShowImageOfNoteAdapter(this);
		gridViewImageForNote.setAdapter(showImageOfNoteAdapter);
	}

	private void initSpinner() {
		initDateSpinner();
		initTimeSpinner();
		datePickerDialog = new DatePickerDialog(this,
				customDateSetListener, Calendar.getInstance().get(Calendar.YEAR),
				Calendar.getInstance().get(Calendar.MONTH),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		timePickerDialog = new TimePickerDialog(this, customOnTimeSetListener,
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true
		);
	}

	@NonNull
	private void initDateSpinner() {
		datePicker = (Spinner) findViewById(R.id.date_picker);
		listDatePicker = getResources().getStringArray(R.array.date_picker);
		final ArrayAdapter<CharSequence> datePickerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
				listDatePicker);
		datePickerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		datePicker.setAdapter(datePickerAdapter);
	}

	private void initTimeSpinner() {
		timePicker = (Spinner) findViewById(R.id.time_picker);
		listTimePicker = getResources().getStringArray(R.array.time_picker);
		ArrayAdapter<CharSequence> timePickerAdapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,
				listTimePicker);
		timePickerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timePicker.setAdapter(timePickerAdapter);
	}

	private void initBottomNavigationBar() {
		llBottomNavigationBar = (LinearLayout) findViewById(R.id.ll_bottom_navigation_bar);
		imgBack = (ImageView) findViewById(R.id.image_back_edit_note);
		imgBack.setOnClickListener(this);
		imgForward = (ImageView) findViewById(R.id.image_forward_edit_note);
		imgForward.setOnClickListener(this);
		imgDelete = (ImageView) findViewById(R.id.image_delete_edit_note);
		imgDelete.setOnClickListener(this);
		imgShare = (ImageView) findViewById(R.id.image_share_edit_note);
		imgShare.setOnClickListener(this);
	}

	private void getDataFromPresenter() {
		if (getIntent() != null && getIntent().getParcelableExtra(EXTRA_NOTE_ITEM) != null) {
			displayData((NoteItem) getIntent().getParcelableExtra(EXTRA_NOTE_ITEM));
			providedPresenter.setNoteItemData((NoteItem) getIntent().getParcelableExtra(EXTRA_NOTE_ITEM));
			providedPresenter.loadListDataForPresenter();
		} else if (getIntent() != null && getIntent().getIntExtra(EXTRA_NOTE_ID, -1) != -1) {
			providedPresenter.notifyShowNoteById(getIntent().getIntExtra(EXTRA_NOTE_ID, -1));
			providedPresenter.loadListDataForPresenter();
		} else {
			providedPresenter.createNewNote();
			llBottomNavigationBar.setVisibility(View.GONE);
		}
	}

	private void shareNote() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, editTextTitle.getText() + "\n" + editTextContent.getText());
		startActivity(Intent.createChooser(intent, getString(R.string.share)));
	}

	private void addTextWatcherForEditTextContent() {
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

	private void addTextWatcherForEditTextTitle() {
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

	private void addPictureForNote() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.menu_capture)
				.setPositiveButton(R.string.action_capture, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						handleCapture();
					}
				})
				.setNegativeButton(R.string.action_chose_from_gallery, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						handleChoseFromGallery();
					}
				})
				.show();
	}

	private void handleCapture() {
		if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
				File photoFile = null;
				try {
					photoFile = Utils.createImageFile(this);
				} catch (IOException ex) {
					Log.e(TAG, "addPictureForNote: ", ex);
				}
				if (photoFile != null) {
					tempImagePath = photoFile.getAbsolutePath();
					Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName(), photoFile);
					Utils.grantUriPermission(this, takePictureIntent, photoURI);
					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
					startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
				}
			}
		}
	}

	private void handleChoseFromGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
	}

	private void showChoseColorDialog() {
		final Dialog dialog = new AppCompatDialog(this);
		dialog.setContentView(R.layout.dialog_chose_color);
		dialog.setTitle(getResources().getString(R.string.menu_chose_color));
		GridView gridView = (GridView) dialog.findViewById(R.id.grid_view_color);
		gridView.setAdapter(new ChoseColorAdapter());
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				int colorId = (int) adapterView.getItemIdAtPosition(i);
				scrollViewContainContent.setBackgroundColor(colorId);
				providedPresenter.updateColor(colorId);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void createNewNote() {
		startActivity(getStartIntent(this));
		finish();
	}

	public void removeImage(int index) {
		providedPresenter.removeImage(index);
	}

	private void setOtherDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		listDatePicker[listDatePicker.length - 1] = dateFormat.format(date);
		((ArrayAdapter) datePicker.getAdapter()).notifyDataSetChanged();
		datePicker.setSelection(listDatePicker.length - 1, true);
	}

	private void setOtherTime(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("hh:mm");
		listTimePicker[listTimePicker.length - 1] = dateFormat.format(date);
		((ArrayAdapter) timePicker.getAdapter()).notifyDataSetChanged();
		timePicker.setSelection(listTimePicker.length - 1, true);
	}
}
