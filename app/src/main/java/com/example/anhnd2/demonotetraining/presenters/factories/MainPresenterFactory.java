package com.example.anhnd2.demonotetraining.presenters.factories;

import com.example.anhnd2.demonotetraining.interfaces.MvpMain;
import com.example.anhnd2.demonotetraining.models.MainModel;
import com.example.anhnd2.demonotetraining.presenters.MainPresenter;

/**
 * Created by anhnd2 on 7/14/2017.
 */

public class MainPresenterFactory {

	private static MainPresenterFactory instance;
	private MainPresenter mainPresenter;

	private MainPresenterFactory() {

	}

	public static MainPresenterFactory getInstance() {
		if (instance == null) {
			instance = new MainPresenterFactory();
		}
		return instance;
	}

	public MvpMain.ProvidedPresenter getMainPresenter(MvpMain.RequiredView view) {
		if (mainPresenter == null) {
			mainPresenter = new MainPresenter(view);
			mainPresenter.setModel(new MainModel(mainPresenter));
		} else {
			mainPresenter.setView(view);
		}
		return mainPresenter;
	}


}
