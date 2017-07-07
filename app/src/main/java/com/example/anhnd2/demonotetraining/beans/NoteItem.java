package com.example.anhnd2.demonotetraining.beans;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.BitSet;
import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/7/2017.
 */

public class NoteItem implements Parcelable {

	public String title;
	public String content;
	public Date createdTime;
	public Color backgroundColor;
	public List<Bitmap> bitmapList;
	public Date alarmTime;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.content);
		dest.writeLong(this.createdTime != null ? this.createdTime.getTime() : -1);
		dest.writeParcelable((Parcelable) this.backgroundColor, flags);
		dest.writeTypedList(this.bitmapList);
		dest.writeLong(this.alarmTime != null ? this.alarmTime.getTime() : -1);
	}

	public NoteItem() {
	}

	protected NoteItem(Parcel in) {
		this.title = in.readString();
		this.content = in.readString();
		long tmpCreatedTime = in.readLong();
		this.createdTime = tmpCreatedTime == -1 ? null : new Date(tmpCreatedTime);
		this.backgroundColor = in.readParcelable(Color.class.getClassLoader());
		this.bitmapList = in.createTypedArrayList(Bitmap.CREATOR);
		long tmpAlarmTime = in.readLong();
		this.alarmTime = tmpAlarmTime == -1 ? null : new Date(tmpAlarmTime);
	}

	public static final Parcelable.Creator<NoteItem> CREATOR = new Parcelable.Creator<NoteItem>() {
		@Override
		public NoteItem createFromParcel(Parcel source) {
			return new NoteItem(source);
		}

		@Override
		public NoteItem[] newArray(int size) {
			return new NoteItem[size];
		}
	};
}
