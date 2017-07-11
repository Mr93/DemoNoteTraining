package com.example.anhnd2.demonotetraining.presenters;

import android.util.Log;

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
	private long lastTimeUpdate = 0;
	private final long WAITING_TIME = 0;
	private boolean isDataChanged = false;

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
		isDataChanged = false;
	}

	@Override
	public void getNoteById(int id) {

	}

	@Override
	public void updateNote(NoteItem noteItem) {
		this.noteItem.parseData(noteItem);
		isDataChanged = true;
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
		isDataChanged = true;
		if(System.currentTimeMillis() - lastTimeUpdate > WAITING_TIME){
			model.saveNote(this.noteItem);
			lastTimeUpdate = System.currentTimeMillis();
		}

	}

	@Override
	public void forceSave() {
		Log.d(TAG, "forceSave: " + this.noteItem);
		Log.d(TAG, "forceSave: " + isDataChanged);
		if (isDataChanged){
			model.saveNote(this.noteItem);
		}
	}

	@Override
	public void setNoteItemData(NoteItem noteItemData) {
		this.noteItem = noteItemData;
	}

	private MvpEdit.RequiredView getView() throws NullPointerException{
		if(viewWeakReference != null){
			return viewWeakReference.get();
		}else {
			throw new NullPointerException("View is unavailable");
		}
	}


}
