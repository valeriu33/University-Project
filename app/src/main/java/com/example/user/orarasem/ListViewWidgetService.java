package com.example.user.orarasem;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.user.orarasem.classes.Zi;

import java.util.ArrayList;

/**
 * Created by User on 28.03.2018.
 */

public class ListViewWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(this.getApplicationContext(), Zi.getDataFromSP(getApplicationContext()));
    }
}


class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {

    private Zi[] zile;
    private Context context;

    public AppWidgetListView(Context applicationContext, Zi[] dataArray) {
        this.context=applicationContext;
        this.zile = dataArray;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.zi_content);

        if (position %4 != 0)
        {
            views.setViewVisibility(R.id.sumarZi, LinearLayout.GONE);
        }
        else {
            views.setViewVisibility(R.id.sumarZi, LinearLayout.VISIBLE);
            views.setTextViewText(R.id.numeSaptamina, zile[position / 4].nume);
            try {
                views.setTextViewText(R.id.lectie1, zile[position / 4].lectii[position % 4].nume);
            } catch (Exception e) {
                views.setTextViewText(R.id.lectie1, "");
            }
            try {
                views.setTextViewText(R.id.lectie2, zile[position / 4].lectii[position % 4 + 1].nume);
            } catch (Exception e) {
                views.setTextViewText(R.id.lectie2, "");
            }
            try {
                views.setTextViewText(R.id.lectie3, zile[position / 4].lectii[position % 4 + 2].nume);
            } catch (Exception e) {
                views.setTextViewText(R.id.lectie3, "");
            }
            try {
                views.setTextViewText(R.id.lectie4, zile[position / 4].lectii[position % 4 + 3].nume);
            } catch (Exception e) {
                views.setTextViewText(R.id.lectie4, "");
            }
        }

        views.setViewVisibility(R.id.ziSaptamina, TextView.VISIBLE);
        views.setTextViewText(R.id.ziSaptamina, zile[position/4].nume);
        try {
            views.setViewVisibility(R.id.lectie, TextView.VISIBLE);
            views.setViewVisibility(R.id.ora, TextView.VISIBLE);
            views.setViewVisibility(R.id.profesor, TextView.VISIBLE);
            views.setViewVisibility(R.id.tip, TextView.VISIBLE);
            views.setViewVisibility(R.id.nrCabinet, TextView.VISIBLE);

            views.setTextViewText(R.id.lectie, zile[position/4].lectii[position%4].nume);
            views.setTextViewText(R.id.ora, Zi.ore[position%4]);
            views.setTextViewText(R.id.profesor, zile[position/4].lectii[position%4].profesor);
            views.setTextViewText(R.id.tip, zile[position/4].lectii[position%4].tip);
            views.setTextViewText(R.id.nrCabinet, zile[position/4].lectii[position%4].cabinet);
        }
        catch (Exception e)
        {
            if(position % 4 == 3)
            {
                views.setViewVisibility(R.id.lectie, TextView.GONE);
                views.setViewVisibility(R.id.ora, TextView.GONE);
                views.setViewVisibility(R.id.profesor, TextView.GONE);
                views.setViewVisibility(R.id.tip, TextView.GONE);
                views.setViewVisibility(R.id.nrCabinet, TextView.GONE);
                views.setViewVisibility(R.id.ziSaptamina, TextView.GONE);
            }else {
                views.setTextViewText(R.id.lectie, "Fereastra");
                views.setTextViewText(R.id.ora, Zi.ore[position%4]);
                views.setTextViewText(R.id.profesor, "");
                views.setTextViewText(R.id.tip, "");
                views.setTextViewText(R.id.nrCabinet, "");
            }
        }
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {

        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}