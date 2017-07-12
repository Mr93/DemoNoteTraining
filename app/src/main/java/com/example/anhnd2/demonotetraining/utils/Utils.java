package com.example.anhnd2.demonotetraining.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anhnd2 on 7/11/2017.
 */

public class Utils {

	private static String ARRAY_DIVIDER = "#a1r2ra5yd2iv1i9der";

	public static String serialize(String content[]) {
		return TextUtils.join(ARRAY_DIVIDER, content);
	}

	public static String[] derialize(String content) {
		return content.split(ARRAY_DIVIDER);
	}

	public static String formatDateTimeString(Date date){
		return (new SimpleDateFormat("dd/MM hh:mm")).format(date);
	}
}
