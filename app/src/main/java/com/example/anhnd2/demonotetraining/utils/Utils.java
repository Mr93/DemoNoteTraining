package com.example.anhnd2.demonotetraining.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by anhnd2 on 7/11/2017.
 */

public class Utils {

	public static final String TAG = Utils.class.getSimpleName();
	private static String ARRAY_DIVIDER = "#a1r2ra5yd2iv1i9der";

	public static String serialize(String content[]) {
		return TextUtils.join(ARRAY_DIVIDER, content);
	}

	public static String[] derialize(String content) {
		return content.split(ARRAY_DIVIDER);
	}

	public static String formatDateTimeString(Date date) {
		return (new SimpleDateFormat("dd/MM hh:mm")).format(date);
	}

	public static File createImageFile(Context context) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,
				".jpg",
				storageDir
		);
		return image;
	}

	public static void grantUriPermission(Context context, Intent intent, Uri uri) {
		List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager
				.MATCH_DEFAULT_ONLY);
		for (ResolveInfo resolveInfo : resInfoList) {
			String packageName = resolveInfo.activityInfo.packageName;
			context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
					| Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		boolean check = file.delete();
		Log.d(TAG, "deleteFile: " + check);
	}

	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	public static Date getNextThursday() {
		int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		int plusDay = 0;
		while (currentDay != Calendar.THURSDAY) {
			if (currentDay == Calendar.SATURDAY) {
				currentDay = Calendar.SUNDAY;
			} else {
				currentDay++;
			}
			plusDay++;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, plusDay);
		return calendar.getTime();
	}
}
