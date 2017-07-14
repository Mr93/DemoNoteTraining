package com.example.anhnd2.demonotetraining.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.anhnd2.demonotetraining.beans.NoteItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by anhnd2 on 7/11/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

	private static final String TAG = DbHelper.class.getSimpleName();
	private static DbHelper instance;
	private static final String DATABASE_NAME = "MyNoteDatabase.db";
	private static final int DATABASE_VERSION = 1;
	private static final String NOTE_TABLE_NAME = "note";
	private static final String NOTE_COLUMN_ID = "id";
	private static final String NOTE_COLUMN_CONTENT = "content";
	private static final String NOTE_COLUMN_TITLE = "title";
	private static final String NOTE_COLUMN_CREATED_TIME = "created_time";
	private static final String NOTE_COLUMN_COLOR_ID = "color_id";
	private static final String NOTE_COLUMN_IMAGE_LIST = "image_list";
	private static final String NOTE_COLUMN_ALARM_TIME = "alarm_time";

	private DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public synchronized static DbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbHelper(context.getApplicationContext());
		}
		return instance;
	}


	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(
				"create table " + NOTE_TABLE_NAME +
						" (" + NOTE_COLUMN_ID + " integer primary key, "
						+ NOTE_COLUMN_TITLE + " text, "
						+ NOTE_COLUMN_CONTENT + " text, "
						+ NOTE_COLUMN_CREATED_TIME + " text, "
						+ NOTE_COLUMN_COLOR_ID + " integer, "
						+ NOTE_COLUMN_IMAGE_LIST + " text, "
						+ NOTE_COLUMN_ALARM_TIME + " text "
						+ ")"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE_NAME);
		onCreate(sqLiteDatabase);
	}

	public synchronized int insertNote(NoteItem noteItem) {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(NOTE_COLUMN_TITLE, noteItem.title);
		contentValues.put(NOTE_COLUMN_CONTENT, noteItem.content);
		contentValues.put(NOTE_COLUMN_CREATED_TIME, noteItem.createdTime.toString());
		contentValues.put(NOTE_COLUMN_COLOR_ID, noteItem.colorId);
		if (noteItem.bitmapPathList.size() != 0) {
			contentValues.put(NOTE_COLUMN_IMAGE_LIST, Utils.serialize(noteItem.bitmapPathList.toArray(new String[noteItem
					.bitmapPathList.size()])));
		}
		contentValues.put(NOTE_COLUMN_ALARM_TIME, noteItem.alarmTime != null ? noteItem.alarmTime.toString() : "");

		return (int) sqLiteDatabase.insert(NOTE_TABLE_NAME, null, contentValues);
	}

	public synchronized void updateNote(NoteItem noteItem) {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(NOTE_COLUMN_ID, noteItem.id);
		contentValues.put(NOTE_COLUMN_TITLE, noteItem.title);
		contentValues.put(NOTE_COLUMN_CONTENT, noteItem.content);
		contentValues.put(NOTE_COLUMN_CREATED_TIME, noteItem.createdTime.toString());
		contentValues.put(NOTE_COLUMN_COLOR_ID, noteItem.colorId);
		contentValues.put(NOTE_COLUMN_IMAGE_LIST, Utils.serialize(noteItem.bitmapPathList.toArray(new String[noteItem
				.bitmapPathList.size()])));
		contentValues.put(NOTE_COLUMN_ALARM_TIME, noteItem.alarmTime != null ? noteItem.alarmTime.toString() : "");
		sqLiteDatabase.update(NOTE_TABLE_NAME, contentValues, " id = ? ", new String[]{Integer.toString(noteItem.id)});
	}

	public synchronized Integer deleteNote(NoteItem noteItem) {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		Log.d(TAG, "deleteNote: " + noteItem.id);
		return sqLiteDatabase.delete(NOTE_TABLE_NAME, "id = ? ", new String[]{Integer.toString(noteItem.id)});
	}

	public ArrayList<NoteItem> getAllNotes() {
		ArrayList<NoteItem> arrayList = new ArrayList<>();
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		Cursor data = sqLiteDatabase.rawQuery("select * from " + NOTE_TABLE_NAME, null);
		data.moveToFirst();
		while (data.isAfterLast() == false) {
			NoteItem note = parseDataFromCursorToNoteItem(data);
			arrayList.add(note);
			data.moveToNext();
		}
		return arrayList;
	}

	@NonNull
	private NoteItem parseDataFromCursorToNoteItem(Cursor data) {
		NoteItem note = new NoteItem();
		note.id = data.getInt(data.getColumnIndex(NOTE_COLUMN_ID));
		note.title = data.getString(data.getColumnIndex(NOTE_COLUMN_TITLE));
		note.content = data.getString(data.getColumnIndex(NOTE_COLUMN_CONTENT));
		note.colorId = data.getInt(data.getColumnIndex(NOTE_COLUMN_COLOR_ID));
		note.createdTime = new Date(data.getString(data.getColumnIndex(NOTE_COLUMN_CREATED_TIME)));
		String imagePathList = data.getString(data.getColumnIndex(NOTE_COLUMN_IMAGE_LIST));
		if (imagePathList != null && !"".equalsIgnoreCase(imagePathList)) {
			note.bitmapPathList = new ArrayList<>(Arrays.asList(Utils.derialize(imagePathList)));
		}
		String alarmTime = data.getString(data.getColumnIndex(NOTE_COLUMN_ALARM_TIME));
		if (alarmTime != null && !"".equalsIgnoreCase(alarmTime)) {
			note.alarmTime = new Date(alarmTime);
		}
		return note;
	}

	public NoteItem getNote(NoteItem noteItem) {
		SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
		Cursor data = sqLiteDatabase.rawQuery("select * from " + NOTE_TABLE_NAME + " where " + NOTE_COLUMN_ID + " = " + noteItem.id
				+ "", null);
		Log.d(TAG, "getANote: " + "select * from " + NOTE_TABLE_NAME + " where " + NOTE_COLUMN_ID + " = " + noteItem.id + "");
		data.moveToFirst();
		return parseDataFromCursorToNoteItem(data);
	}
}
