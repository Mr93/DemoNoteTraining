package com.example.anhnd2.demonotetraining.models;

import com.example.anhnd2.demonotetraining.interfaces.MvpMain;

/**
 * Created by anhnd2 on 7/10/2017.
 */

public class MainModel implements MvpMain.ProvidedModel {

	private MvpMain.RequiredPresenter presenter;

	public MainModel(MvpMain.RequiredPresenter presenter) {
		this.presenter = presenter;
	}
}
