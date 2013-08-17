package com.chodella.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Corneliu Dascalu <corneliu.dascalu@osf-global.com>
 */
public class ChoWidgetPreferenceActivity extends PreferenceActivity {
    private int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        final Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_CANCELED);

        findPreference(getString(R.string.startDateKey))
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {


                    @Override
                    public boolean onPreferenceChange(Preference preference, Object o) {
                        String date = String.valueOf(o);
                        if (date.matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")) {
                            setResult(RESULT_OK, resultValue);
                        } else {
                            Toast.makeText(getBaseContext(), "Invalid date. It should be in the format YYYY/MM/DD",
                                    Toast.LENGTH_SHORT).show();
                            setResult(RESULT_CANCELED);
                            return false;
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        int days = -1;
        try {
            days = getDays(getApplicationContext());
        } catch (Exception e) {
            setResult(RESULT_CANCELED);
            finish();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.main);
        views.setCharSequence(R.id.widgetBadge, "setText", String.valueOf(days));
        appWidgetManager.updateAppWidget(mAppWidgetId, views);
        finish();
    }

    public static int getDays(Context context) throws Exception {
        String value = PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.startDateKey), null);

        if (value == null) {
            throw new Exception("The date is not set");
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            calendar.setTime(sdf.parse(value));
        } catch (ParseException e) {
            Toast.makeText(context, "Failed to parse date", Toast.LENGTH_SHORT).show();
            Log.e("Chodella", "Failed to parse date", e);
        }

        Calendar now = Calendar.getInstance();

        long diff = now.getTimeInMillis() - calendar.getTimeInMillis();
        int days = (int) (diff / 1000 / 60 / 60 / 24);

        return days;
    }
}
