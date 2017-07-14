package com.example.anhnd2.demonotetraining.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.NoCopySpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.application.MyApplication;

/**
 * Created by anhnd2 on 7/12/2017.
 */

public class ChoseColorAdapter extends BaseAdapter {

	private int size = (int) MyApplication.getContext().getResources().getDimension(R.dimen.size_grid_view_color_item);
	private int padding = (int) MyApplication.getContext().getResources().getDimension(R.dimen.padding_note_item);

	private Integer[] listColorId = {
			MyApplication.getContext().getResources().getColor(R.color.colorYellow),
			MyApplication.getContext().getResources().getColor(R.color.colorRed),
			MyApplication.getContext().getResources().getColor(R.color.colorBlue),
			MyApplication.getContext().getResources().getColor(R.color.colorGreen)
	};

	@Override
	public int getCount() {
		return listColorId.length;
	}

	@Override
	public Object getItem(int i) {
		return listColorId[i];
	}

	@Override
	public long getItemId(int i) {
		return listColorId[i];
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ImageView imageView;
		if (view == null){
			imageView = new ImageView(MyApplication.getContext());
			imageView.setLayoutParams(new GridView.LayoutParams(size, size));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(padding, padding, padding, padding);
		}else {
			imageView = (ImageView) view;
		}
		imageView.setBackgroundColor(listColorId[i]);
		return imageView;
	}
}
