package com.example.anhnd2.demonotetraining.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.activities.EditActivity;
import com.example.anhnd2.demonotetraining.beans.NoteItem;
import com.example.anhnd2.demonotetraining.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by anhnd2 on 7/10/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

	public static final String TAG = NoteAdapter.class.getSimpleName();
	private List<NoteItem> noteItemList;

	public NoteAdapter(List<NoteItem> noteItemList) {
		this.noteItemList = noteItemList;
	}

	@Override
	public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
		return new NoteHolder(view);
	}

	@Override
	public void onBindViewHolder(NoteHolder holder, int position) {
		final NoteItem noteItem = noteItemList.get(position);
		holder.txtCreatedDate.setText(Utils.formatDateTimeString(noteItem.createdTime));
		holder.txtTitle.setText(noteItem.title);
		holder.txtContent.setText(noteItem.content);
		if (noteItem.alarmTime != null) {
			holder.imgAlarm.setVisibility(View.VISIBLE);
		} else {
			holder.imgAlarm.setVisibility(View.GONE);
		}
		holder.layoutContainter.setBackgroundColor(noteItem.colorId);
		holder.layoutContainter.setClickable(true);
		holder.layoutContainter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				view.getContext().startActivity(EditActivity.getStartIntent(view.getContext(), noteItem));
			}
		});
	}

	@Override
	public int getItemCount() {
		return noteItemList.size();
	}

	public void setNoteItemList(List<NoteItem> noteItemList) {
		this.noteItemList = noteItemList;
	}

	public class NoteHolder extends RecyclerView.ViewHolder {
		public TextView txtTitle, txtContent, txtCreatedDate;
		public ImageView imgAlarm;
		public LinearLayout layoutContainter;

		public NoteHolder(View view) {
			super(view);
			txtTitle = (TextView) view.findViewById(R.id.text_title_note);
			txtContent = (TextView) view.findViewById(R.id.text_content_note);
			txtCreatedDate = (TextView) view.findViewById(R.id.text_created_date_note);
			imgAlarm = (ImageView) view.findViewById(R.id.image_alarm_note);
			layoutContainter = (LinearLayout) view.findViewById(R.id.layout_container_note);
		}
	}
}
