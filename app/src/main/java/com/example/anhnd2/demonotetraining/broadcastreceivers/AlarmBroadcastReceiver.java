package com.example.anhnd2.demonotetraining.broadcastreceivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.anhnd2.demonotetraining.R;
import com.example.anhnd2.demonotetraining.activities.EditActivity;
import com.example.anhnd2.demonotetraining.application.MyApplication;

/**
 * Created by anhnd2 on 7/13/2017.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

	public static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();
	private static final int REQUEST_ALARM = 1234;
	private static final String EXTRA_CONTENT = "content";
	private static final String EXTRA_ID = "id";

	public static PendingIntent getPendingIntent(Context context, String title, String content, int requestCode) {
		Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
		intent.putExtra(Intent.EXTRA_TITLE, title);
		intent.putExtra(EXTRA_CONTENT, content);
		intent.putExtra(EXTRA_ID, requestCode);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(MyApplication.getContext(), requestCode,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String title = "", content = "";
		int extraId = intent.getIntExtra(EXTRA_ID, 0);
		if (intent.getStringExtra(Intent.EXTRA_TITLE) != null) {
			title = intent.getStringExtra(Intent.EXTRA_TITLE);
		}
		if (intent.getStringExtra(EXTRA_CONTENT) != null) {
			content = intent.getStringExtra(EXTRA_CONTENT);
		}
		Log.d(TAG, "onReceive: " + extraId);
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(context)
						.setSmallIcon(R.drawable.icon_note)
						.setContentTitle(title)
						.setAutoCancel(true)
						.setContentText(content);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, extraId, EditActivity.getStartIntent(context, extraId),
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(0, mBuilder.build());
	}
}
