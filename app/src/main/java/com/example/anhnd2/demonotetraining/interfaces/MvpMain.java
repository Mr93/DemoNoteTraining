package com.example.anhnd2.demonotetraining.interfaces;

import com.example.anhnd2.demonotetraining.beans.NoteItem;

import java.util.List;

/**
 * Created by anhnd2 on 7/7/2017.
 */

public interface MvpMain {

	interface RequiredPresenter {

	}

	interface RequiredView {

	}

	interface ProvidedModel {

	}

	interface ProvidedPresenter {
		List<NoteItem> getNoteItemList();

		void setView(RequiredView requiredView);

		void setModel(ProvidedModel providedModel);
	}

}
