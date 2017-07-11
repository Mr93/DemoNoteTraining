package com.example.anhnd2.demonotetraining.interfaces;

import com.example.anhnd2.demonotetraining.beans.NoteItem;

import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/10/2017.
 */

public interface MvpEdit {

	interface ProvidedPresenter {
		void setView(RequiredView requiredView);

		void setModel(ProvidedModel providedModel);

		void createNewNote();

		void getNoteById(int id);

		void updateNote(NoteItem noteItem);

		void updateTitle(String title);

		void updateContent(String content);

		void updateColor(int colorId);

		void updateImage(List<String> bitmapPathList);

		void updateAlarm(Date date);

		void saveData();

		void forceSave();

		void setNoteItemData(NoteItem noteItemData);
	}

	interface ProvidedModel {
		void createNewNote();

		void getNoteById(int id);

		void saveNote(NoteItem noteItem);
	}

	interface RequiredPresenter {
		void onNewNoteCreated(NoteItem noteItem);

		void onNoteFetched(NoteItem noteItem);

		void onNoteUpdated();
	}

	interface RequiredView {
		void displayData(NoteItem noteItem);

		void noteUpdated();
	}

}
