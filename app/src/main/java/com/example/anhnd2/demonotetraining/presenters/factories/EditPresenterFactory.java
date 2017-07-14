package com.example.anhnd2.demonotetraining.presenters.factories;

import com.example.anhnd2.demonotetraining.interfaces.MvpEdit;
import com.example.anhnd2.demonotetraining.models.EditModel;
import com.example.anhnd2.demonotetraining.presenters.EditPresenter;

/**
 * Created by anhnd2 on 7/14/2017.
 */

public class EditPresenterFactory {

	private static EditPresenterFactory instance;
	private EditPresenter editPresenter;

	private EditPresenterFactory() {

	}

	public static EditPresenterFactory getInstance() {
		if (instance == null) {
			instance = new EditPresenterFactory();
		}
		return instance;
	}

	public MvpEdit.ProvidedPresenter getPresenter(MvpEdit.RequiredView view) {
		if (editPresenter == null) {
			editPresenter = new EditPresenter(view);
			editPresenter.setModel(new EditModel(editPresenter));
		} else {
			editPresenter.setView(view);
		}
		return editPresenter;
	}
}
