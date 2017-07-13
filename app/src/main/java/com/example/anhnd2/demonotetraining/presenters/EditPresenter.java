package com.example.anhnd2.demonotetraining.presenters;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;

import com.example.anhnd2.demonotetraining.activities.EditActivity;
import com.example.anhnd2.demonotetraining.application.MyApplication;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.broadcastreceivers.AlarmBroadcastReceiver;
import com.example.anhnd2.demonotetraining.interfaces.MvpEdit;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/11/2017.
 */

public class EditPresenter implements MvpEdit.ProvidedPresenter, MvpEdit.RequiredPresenter {

	public static final String TAG = EditPresenter.class.getSimpleName();
	private WeakReference<MvpEdit.RequiredView> viewWeakReference;
	private MvpEdit.ProvidedModel model;
	private NoteItem currentNote;
	private List<NoteItem> noteItemList;
	private long lastTimeUpdate = 0;
	private final long WAITING_TIME = 0;
	private boolean needSave = false;
	private Calendar calendar = Calendar.getInstance();

	public EditPresenter(MvpEdit.RequiredView requiredView) {
		this.viewWeakReference = new WeakReference<MvpEdit.RequiredView>(requiredView);
	}

	@Override
	public void setView(MvpEdit.RequiredView requiredView) {
		this.viewWeakReference = new WeakReference<MvpEdit.RequiredView>(requiredView);
	}

	@Override
	public void setModel(MvpEdit.ProvidedModel providedModel) {
		this.model = providedModel;
	}

	@Override
	public void createNewNote() {
		model.createNewNote();
	}

	@Override
	public void notifyShowNoteById(int id) {
		model.notifyShowNoteById(id);
	}

	@Override
	public void onNewNoteCreated(NoteItem noteItem) {
		this.currentNote = noteItem;
		getView().displayData(noteItem);
	}

	@Override
	public void onNoteNotificationFetched(NoteItem noteItem) {
		this.currentNote = noteItem;
		//getView().displayData(noteItem);
	}

	@Override
	public void onNoteUpdated() {
		getView().noteUpdated();
		needSave = false;
	}

	@Override
	public void onNoteDeleted() {
		getView().noteDeleted();
	}

	@Override
	public void onNoteListLoaded(List<NoteItem> noteItemList) {
		this.noteItemList = noteItemList;
		if (this.noteItemList.size() == 1) {
			getView().disableBackButton();
			getView().disableForwardButton();
		} else {
			if (this.noteItemList.get(0).id == this.currentNote.id) {
				getView().disableBackButton();
			} else if (this.noteItemList.get(this.noteItemList.size() - 1).id == this.currentNote.id) {
				getView().disableForwardButton();
			}
		}
	}

	@Override
	public void updateTitle(String title) {
		this.currentNote.title = title;
		saveData();
	}

	@Override
	public void updateContent(String content) {
		this.currentNote.content = content;
		saveData();
	}

	@Override
	public void updateColor(int colorId) {
		this.currentNote.colorId = colorId;
		saveData();
	}

	@Override
	public void updateImage(String imagePath) {
		this.currentNote.bitmapPathList.add(imagePath);
		saveData();
	}

	@Override
	public void removeImage(int index) {
		this.currentNote.bitmapPathList.remove(index);
		saveData();
	}

	@Override
	public void updateDateAlarm(Date date) {
		calendar.setTime(date);
		this.currentNote.alarmTime = calendar.getTime();
		Log.d(TAG, "updateDateAlarm: " + this.currentNote.alarmTime);
		saveData();
	}

	@Override
	public void updateTimeAlarm(int hour, int min) {
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, 0);
		this.currentNote.alarmTime = calendar.getTime();
		Log.d(TAG, "updateTimeAlarm: " + this.currentNote.alarmTime);
		saveData();
	}

	@Override
	public void removeAlarm() {
		this.currentNote.alarmTime = null;
		saveData();
	}

	@Override
	public void saveData() {
		needSave = true;
		if (System.currentTimeMillis() - lastTimeUpdate > WAITING_TIME) {
			model.saveNote(this.currentNote);
			lastTimeUpdate = System.currentTimeMillis();
		}
	}

	@Override
	public void forceSave() {
		if (needSave) {
			model.saveNote(this.currentNote);
		}
		if (this.currentNote.alarmTime != null) {
			AlarmManager alarmManager = (AlarmManager) MyApplication.getContext().getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, this.currentNote.alarmTime.getTime(), AlarmBroadcastReceiver
					.getPendingIntent(getView().getActivityContext(), this.currentNote.title, this.currentNote.content,
							this.currentNote.id));
		}
	}

	@Override
	public void setNoteItemData(NoteItem noteItemData) {
		this.currentNote = noteItemData;
	}

	@Override
	public void deleteNoteById() {
		needSave = false;
		model.deleteNote(this.currentNote);
	}

	@Override
	public void loadListDataForPresenter() {
		model.loadListDataForPresenter();
	}

	@Override
	public void loadNextNote() {
		for (int i = 0; i < this.noteItemList.size(); i++) {
			if (this.currentNote.id == this.noteItemList.get(i).id) {
				Context context = getView().getActivityContext();
				context.startActivity(EditActivity.getStartIntent(context, this.noteItemList.get(i + 1)));
				((Activity) context).finish();
				break;
			}
		}
	}

	@Override
	public void loadPreviousNote() {
		for (int i = 0; i < this.noteItemList.size(); i++) {
			if (this.currentNote.id == this.noteItemList.get(i).id) {
				Context context = getView().getActivityContext();
				context.startActivity(EditActivity.getStartIntent(context, this.noteItemList.get(i - 1)));
				((Activity) context).finish();
				break;
			}
		}
	}

	private MvpEdit.RequiredView getView() throws NullPointerException {
		if (viewWeakReference != null) {
			return viewWeakReference.get();
		} else {
			throw new NullPointerException("View is unavailable");
		}
	}


}
