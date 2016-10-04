package us.deusexmachina.atbeatwidget;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
//import android.util.Log;
import android.widget.RemoteViews;

public class AtBeatWidgetSmallProvider extends AppWidgetProvider{
	//private static final String TAG = "AtBeatWidget";
	private static final String ACTION_START_BEAT_ALARM = "us.deusexmachina.atbeatwidget.ACTION_START_BEAT_ALARM";
	private final long interval = (60 * 60 * 24 / 1000) * 1000; // Beat

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		//Log.d(TAG, "onUpdate");
		// To prevent any ANR timeouts, we perform the update in a service.
		context.startService(new Intent(context, AtBeatService.class));
		setAlarm(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		//Log.d(TAG, "onReceive");
		super.onReceive(context, intent);
		if (intent.getAction().equals(ACTION_START_BEAT_ALARM)) {
		    context.startService(new Intent(context, AtBeatService.class));
	        setAlarm(context);
		}
	}

	private void setAlarm(Context context) {
		//Log.d(TAG, "setAlarm");
		Intent alarmIntent = new Intent(context, AtBeatWidgetSmallProvider.class);
		alarmIntent.setAction(ACTION_START_BEAT_ALARM);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		long now = System.currentTimeMillis();
		long oneBeatAfter = now + interval - now % (interval);
		alarmManager.set(AlarmManager.RTC, oneBeatAfter, pendingIntent);
	}

	public static RemoteViews settingsPreference(Context context){
		// For Setting Activity
		Intent clickIntent = new Intent(context, Settings.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.atbeat_widget_small);
		remoteViews.setOnClickPendingIntent(R.id.atBeat, pendingIntent);
		remoteViews.setTextColor(R.id.atBeat, Settings.getColor(context));
		return remoteViews;
	}

	public static class AtBeatService extends Service{
		private static String atBeat = new String();
		private static String temp = new String();
	    //private static int progress = 0;

		@Override
		public void onStart(Intent intent, int startId){
			//Log.d(TAG, "onStart");

            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.atbeat_widget_small);
            remoteViews = settingsPreference(this); // For Setting Activity
            getBeat();
            remoteViews.setTextViewText(R.id.atBeat, atBeat);
            //remoteViews.setProgressBar(R.id.progressBarHorizontal, 1000, progress, false);

			// Push update for this widget to the home screen
			ComponentName componentName = new ComponentName(this, AtBeatWidgetSmallProvider.class);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
			appWidgetManager.updateAppWidget(componentName, remoteViews);
		}

		// It should be used UTC instead of GMT. However I could not find how to handle UTC with JAVA.
	    private void getBeat(){
	    	TimeZone tz = TimeZone.getTimeZone("GMT+1"); // Biel, (Switzerland) Mean Time, BMT
	    	Calendar cal = Calendar.getInstance(tz);  // Create Instance
	        int hour = cal.get(Calendar.HOUR_OF_DAY); // Get hour
	        int minute = cal.get(Calendar.MINUTE);    // Get minute
	        int second = cal.get(Calendar.SECOND);    // Get second
	    	temp = Math.round((((((hour * 60) + minute) * 60) + second) / 86.4)) + "";
	    	//progress = Integer.parseInt(temp);
	    	int l = temp.length();
	    	if(l == 4){
	    		atBeat = "@" + "000";
	    	}else if (l == 1){
	    		atBeat = "@" + "00" + temp;
	    	}else if (l == 2){
	    		atBeat = "@" + "0" + temp;
	    	}else if (l == 3){
	    		atBeat = "@" + temp;
	    	}
	    }

		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
