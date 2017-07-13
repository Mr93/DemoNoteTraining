package com.example.anhnd2.demonotetraining.models;

import android.os.AsyncTask;
import android.util.Log;

import com.example.anhnd2.demonotetraining.application.MyApplication;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpEdit;
import com.example.anhnd2.demonotetraining.utils.DbHelper;

import java.util.List;

/**
 * Created by anhnd2 on 7/11/2017.
 */

public class EditModel implements MvpEdit.ProvidedModel {

	public static final String TAG = EditModel.class.getSimpleName();
	MvpEdit.RequiredPresenter presenter;

	public EditModel(MvpEdit.RequiredPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void createNewNote() {
		final NoteItem noteItem = new NoteItem();
		new AsyncTask<Void, Void, Integer>() {
			@Override
			protected Integer doInBackground(Void... voids) {
				return DbHelper.getInstance(MyApplication.getContext()).insertNote(noteItem);
			}

			@Override
			protected void onPostExecute(Integer integer) {
				super.onPostExecute(integer);
				noteItem.id = integer;
				presenter.onNewNoteCreated(noteItem);
				Log.d(TAG, "onPostExecute: " + integer);
			}
		}.execute();
	}

	@Override
	public void notifyShowNoteById(int id) {
		final NoteItem noteItem = new NoteItem();
		noteItem.id = id;
		new AsyncTask<Void, Void, NoteItem>() {
			@Override
			protected NoteItem doInBackground(Void... voids) {
				NoteItem temp = DbHelper.getInstance(MyApplication.getContext()).getNote(noteItem);
				temp.alarmTime = null;
				DbHelper.getInstance(MyApplication.getContext()).updateNote(temp);
				return temp;
			}

			@Override
			protected void onPostExecute(NoteItem result) {
				super.onPostExecute(result);
				presenter.onNoteNotificationFetched(result);
			}
		}.execute();
	}

	@Override
	public void saveNote(final NoteItem noteItem) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... voids) {
				DbHelper.getInstance(MyApplication.getContext()).updateNote(noteItem);
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				presenter.onNoteUpdated();
			}
		}.execute();
	}

	@Override
	public void deleteNote(final NoteItem noteItem) {
		new AsyncTask<Void, Void, Integer>() {
			@Override
			protected Integer doInBackground(Void... voids) {
				return DbHelper.getInstance(MyApplication.getContext()).deleteNote(noteItem);
			}

			@Override
			protected void onPostExecute(Integer integer) {
				super.onPostExecute(integer);
				if (integer > 0){
					presenter.onNoteDeleted();
				}
			}
		}.execute();
	}

	@Override
	public void loadListDataForPresenter() {
		new AsyncTask<Void, Void , List<NoteItem>>() {
			@Override
			protected List<NoteItem> doInBackground(Void... voids) {
				return DbHelper.getInstance(MyApplication.getContext()).getAllNotes();
			}

			@Override
			protected void onPostExecute(List<NoteItem> noteItemList) {
				super.onPostExecute(noteItemList);
				presenter.onNoteListLoaded(noteItemList);
			}
		}.execute();
	}
}
