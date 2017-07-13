package com.example.anhnd2.demonotetraining.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.anhnd2.demonotetraining.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anhnd2 on 7/12/2017.
 */

public class ShowImageOfNoteAdapter extends BaseAdapter {

	public static final String TAG = ShowImageOfNoteAdapter.class.getSimpleName();
	private List<String> listImagePath = new ArrayList<>();
	private Context context;

	public ShowImageOfNoteAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return listImagePath.size();
	}

	@Override
	public Object getItem(int i) {
		return listImagePath.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View item;
		if (view == null) {
			item = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_grid_view_edit_note, viewGroup, false);
		} else {
			item = view;
		}
		ImageView imageView = item.findViewById(R.id.image_content);
		ImageView imageViewDelete = item.findViewById(R.id.icon_delete_image);
		final Uri uri = Uri.fromFile(new File(listImagePath.get(i)));
		Glide.with(viewGroup.getContext()).load(uri).into(imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(uri, "image/*");
				context.startActivity(intent);
			}
		});
		imageViewDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d(TAG, "onClick: bbbbb");
			}
		});
		return item;
	}

	public void setListImagePath(List<String> listImagePath) {
		this.listImagePath = listImagePath;
		this.notifyDataSetChanged();
	}

	public void addImagePath(String imagePath) {
		this.listImagePath.add(imagePath);
		this.notifyDataSetChanged();
	}
}
