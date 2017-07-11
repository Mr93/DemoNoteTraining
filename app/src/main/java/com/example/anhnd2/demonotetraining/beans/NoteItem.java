package com.example.anhnd2.demonotetraining.beans;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.application.MyApplication;

import java.util.BitSet;
import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/7/2017.
 */

public class NoteItem implements Parcelable {

	public int id = -1;
	public String title = MyApplication.getContext().getString(R.string.default_title);
	public String content = MyApplication.getContext().getString(R.string.default_content);
	public Date createdTime = new Date(System.currentTimeMillis());
	public int colorId = MyApplication.getContext().getResources().getColor(R.color.colorYellow);
	public List<String> bitmapPathList;
	public Date alarmTime;


	public NoteItem() {

	}

	public NoteItem(String title, String content, Date createdTime, Date alarmTime) {
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.alarmTime = alarmTime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.title);
		dest.writeString(this.content);
		dest.writeLong(this.createdTime != null ? this.createdTime.getTime() : -1);
		dest.writeInt(this.colorId);
		dest.writeStringList(this.bitmapPathList);
		dest.writeLong(this.alarmTime != null ? this.alarmTime.getTime() : -1);
	}

	protected NoteItem(Parcel in) {
		this.id = in.readInt();
		this.title = in.readString();
		this.content = in.readString();
		long tmpCreatedTime = in.readLong();
		this.createdTime = tmpCreatedTime == -1 ? null : new Date(tmpCreatedTime);
		this.colorId = in.readInt();
		this.bitmapPathList = in.createStringArrayList();
		long tmpAlarmTime = in.readLong();
		this.alarmTime = tmpAlarmTime == -1 ? null : new Date(tmpAlarmTime);
	}

	public static final Creator<NoteItem> CREATOR = new Creator<NoteItem>() {
		@Override
		public NoteItem createFromParcel(Parcel source) {
			return new NoteItem(source);
		}

		@Override
		public NoteItem[] newArray(int size) {
			return new NoteItem[size];
		}
	};

	@Override
	public String toString() {
		return "NoteItem{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", createdTime=" + createdTime +
				", colorId=" + colorId +
				", bitmapPathList=" + bitmapPathList +
				", alarmTime=" + alarmTime +
				'}';
	}

	public void parseData(NoteItem noteItem){
		this.title = noteItem.title;
		this.content = noteItem.content;
		this.colorId = noteItem.colorId;
		this.bitmapPathList = noteItem.bitmapPathList;
		this.alarmTime = noteItem.alarmTime;
	}
}
