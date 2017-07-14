package com.example.anhnd2.demonotetraining.interfaces;

import android.content.Context;

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

		void notifyShowNoteById(int id);

		void updateTitle(String title);

		void updateContent(String content);

		void updateColor(int colorId);

		void updateImage(String imagePath);

		void removeImage(int index);

		void updateDateAlarm(Date date);

		void updateTimeAlarm(int hour, int min);

		void removeAlarm();

		void saveData();

		void forceSave();

		void setNoteItemData(NoteItem noteItemData);

		void deleteNoteById();

		void loadListDataForPresenter();

		void loadNextNote();

		void loadPreviousNote();
	}

	interface ProvidedModel {
		void createNewNote();

		void notifyShowNoteById(int id);

		void saveNote(NoteItem noteItem);

		void deleteNote(NoteItem noteItem);

		void loadListDataForPresenter();
	}

	interface RequiredPresenter {
		void onNewNoteCreated(NoteItem noteItem);

		void onNoteNotificationFetched(NoteItem noteItem);

		void onNoteUpdated();

		void onNoteDeleted();

		void onNoteListLoaded(List<NoteItem> noteItemList);
	}

	interface RequiredView {
		void displayData(NoteItem noteItem);

		void noteUpdated();

		void noteDeleted();

		void disableBackButton();

		void disableForwardButton();

		Context getActivityContext();
	}

}
