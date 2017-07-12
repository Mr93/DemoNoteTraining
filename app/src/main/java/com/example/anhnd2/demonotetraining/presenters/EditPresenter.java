package com.example.anhnd2.demonotetraining.presenters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.anhnd2.demonotetraining.activities.EditActivity;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpEdit;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/11/2017.
 */

public class EditPresenter implements MvpEdit.ProvidedPresenter, MvpEdit.RequiredPresenter {

	public static final String TAG = EditPresenter.class.getSimpleName();
	private WeakReference<MvpEdit.RequiredView> viewWeakReference;
	private MvpEdit.ProvidedModel model;
	private NoteItem noteItem;
	private List<NoteItem> noteItemList;
	private long lastTimeUpdate = 0;
	private final long WAITING_TIME = 0;
	private boolean needSave = false;

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
		Log.d(TAG, "createNewNote: " + noteItem);
	}

	@Override
	public void onNewNoteCreated(NoteItem noteItem) {
		this.noteItem = noteItem;
		getView().displayData(noteItem);
	}

	@Override
	public void onNoteFetched(NoteItem noteItem) {
		this.noteItem = noteItem;
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
		Log.d(TAG, "onNoteListLoaded: " + this.noteItemList.toString());
		Log.d(TAG, "onNoteListLoaded: " + this.noteItem.toString());
		if (this.noteItemList.size() == 1) {
			getView().disableBackButton();
			getView().disableForwardButton();
		} else {
			if (this.noteItemList.get(0).id == this.noteItem.id) {
				getView().disableBackButton();
			} else if (this.noteItemList.get(this.noteItemList.size() - 1).id == this.noteItem.id) {
				getView().disableForwardButton();
			}
		}
	}

	@Override
	public void updateTitle(String title) {
		this.noteItem.title = title;
		saveData();
	}

	@Override
	public void updateContent(String content) {
		this.noteItem.content = content;
		saveData();
	}

	@Override
	public void updateColor(int colorId) {
		this.noteItem.colorId = colorId;
		saveData();
	}

	@Override
	public void updateImage(List<String> bitmapPathList) {
		this.noteItem.bitmapPathList = bitmapPathList;
		saveData();
	}

	@Override
	public void updateAlarm(Date date) {
		this.noteItem.alarmTime = date;
		saveData();
	}

	@Override
	public void saveData() {
		needSave = true;
		if (System.currentTimeMillis() - lastTimeUpdate > WAITING_TIME) {
			model.saveNote(this.noteItem);
			lastTimeUpdate = System.currentTimeMillis();
		}
	}

	@Override
	public void forceSave() {
		Log.d(TAG, "forceSave: " + this.noteItem);
		Log.d(TAG, "forceSave: " + needSave);
		if (needSave) {
			model.saveNote(this.noteItem);
		}
	}

	@Override
	public void setNoteItemData(NoteItem noteItemData) {
		this.noteItem = noteItemData;
	}

	@Override
	public void deleteNoteById() {
		needSave = false;
		model.deleteNote(this.noteItem);
	}

	@Override
	public void loadListDataForPresenter() {
		model.loadListDataForPresenter();
	}

	@Override
	public void loadNextNote() {
		for (int i = 0; i < this.noteItemList.size(); i++) {
			if (this.noteItem.id == this.noteItemList.get(i).id) {
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
			if (this.noteItem.id == this.noteItemList.get(i).id) {
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
