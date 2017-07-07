package com.example.anhnd2.demonotetraining.presenters;

import com.example.anhnd2.demonotetraining.interfaces.MvpMain;

import java.lang.ref.WeakReference;

/**
 * Created by anhnd2 on 7/7/2017.
 */

public class MainPresenter implements MvpMain.ProvidedPresenter, MvpMain.RequiredPresenter {

	private WeakReference<MvpMain.ProvidedView> viewWeakReference;
	private MvpMain.RequiredModel requiredModel;

	public MainPresenter(WeakReference<MvpMain.ProvidedView> viewWeakReference) {
		this.viewWeakReference = viewWeakReference;
	}

}
