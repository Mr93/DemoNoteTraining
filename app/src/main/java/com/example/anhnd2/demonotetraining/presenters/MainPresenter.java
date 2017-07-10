package com.example.anhnd2.demonotetraining.presenters;

import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.interfaces.MvpMain;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/7/2017.
 */

public class MainPresenter implements MvpMain.ProvidedPresenter, MvpMain.RequiredPresenter {

	private WeakReference<MvpMain.RequiredView> viewWeakReference;
	private MvpMain.ProvidedModel providedModel;
	private List<NoteItem> noteItemList;

	public MainPresenter(MvpMain.RequiredView requiredView) {
		this.viewWeakReference = new WeakReference<MvpMain.RequiredView>(requiredView);
		noteItemList = new ArrayList<>();
		addTestList();
	}

	@Override
	public List<NoteItem> getNoteItemList() {
		return noteItemList;
	}

	@Override
	public void setView(MvpMain.RequiredView requiredView) {
		this.viewWeakReference = new WeakReference<MvpMain.RequiredView>(requiredView);
	}

	@Override
	public void setModel(MvpMain.ProvidedModel providedModel) {
		this.providedModel = providedModel;
	}

	private MvpMain.RequiredView getView() throws NullPointerException {
		if (viewWeakReference != null) {
			return viewWeakReference.get();
		} else {
			throw new NullPointerException("View is unavailable");
		}
	}

	private void addTestList() {
		Date createdDate = new Date(System.currentTimeMillis());
		NoteItem noteItem = new NoteItem("AAA", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", createdDate, createdDate);
		for (int i = 0; i < 10; i++) {
			noteItemList.add(noteItem);
		}
	}
}
