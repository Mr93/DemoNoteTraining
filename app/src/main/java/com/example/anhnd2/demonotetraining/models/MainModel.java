package com.example.anhnd2.demonotetraining.models;

import android.os.AsyncTask;

import com.example.anhnd2.demonotetraining.application.MyApplication;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpMain;
import com.example.anhnd2.demonotetraining.utils.DbHelper;

import java.util.List;

/**
 * Created by anhnd2 on 7/10/2017.
 */

public class MainModel implements MvpMain.ProvidedModel {

	private MvpMain.RequiredPresenter presenter;

	public MainModel(MvpMain.RequiredPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void getNoteItemList() {
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
