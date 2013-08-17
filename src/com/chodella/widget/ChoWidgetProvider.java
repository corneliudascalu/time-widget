package com.chodella.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * @author Corneliu Dascalu <corneliu.dascalu@osf-global.com>
 */
public class ChoWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.main);
        views.setOnClickPendingIntent(R.id.widgetBadge, PendingIntent.getActivity(context, 123,
                new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE), PendingIntent.FLAG_CANCEL_CURRENT));
        views.setOnClickPendingIntent(R.id.widgetDescription, PendingIntent.getActivity(context, 123,
                new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE), PendingIntent.FLAG_CANCEL_CURRENT));
        AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(appWidgetIds[0]);
        appWidgetManager.updateAppWidget(appWidgetIds[0], views);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId,
                                          Bundle newOptions) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);


    }
}
