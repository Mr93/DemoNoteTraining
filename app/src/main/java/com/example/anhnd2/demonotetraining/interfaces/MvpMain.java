package com.example.anhnd2.demonotetraining.interfaces;

import com.example.anhnd2.demonotetraining.beans.NoteItem;

import java.util.List;

/**
 * Created by anhnd2 on 7/7/2017.
 */

public interface MvpMain {

	interface RequiredPresenter {
		void onNoteListLoaded(List<NoteItem> noteItemList);
	}

	interface RequiredView {
		void displayNoteList(List<NoteItem> noteItemList);
	}

	interface ProvidedModel {
		void getNoteItemList();
	}

	interface ProvidedPresenter {
		void getNoteItemList();

		void setView(RequiredView requiredView);

		void setModel(ProvidedModel providedModel);
	}

}
