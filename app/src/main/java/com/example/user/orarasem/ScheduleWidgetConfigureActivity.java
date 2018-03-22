package com.example.user.orarasem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.orarasem.classes.Lectie;
import com.example.user.orarasem.classes.Zi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * The configuration screen for the {@link ScheduleWidget ScheduleWidget} AppWidget.
 */
public class ScheduleWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "com.example.user.orarasem.ScheduleWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Zi[] zile = new Zi[5];

    EditText mAppWidgetText;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = ScheduleWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            saveTitlePref(context, mAppWidgetId, widgetText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ScheduleWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public ScheduleWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.schedule_widget_configure);
        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mAppWidgetText.setText(loadTitlePref(ScheduleWidgetConfigureActivity.this, mAppWidgetId));
    }

    public Zi[] getZiArray()
    {
        Zi[] zile = new Zi[7];

        String json = null;
        try {
            InputStream is = getAssets().open("orarASEM.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray saptamina_jArray = obj.getJSONArray("saptamina");

            for (int i = 0; i < saptamina_jArray.length(); i++) {
                JSONObject zi_jObject = saptamina_jArray.getJSONObject(i);
                Zi zi = new Zi();

                zi.id = (int)zi_jObject.get("idZi");
                zi.nume = (String)zi_jObject.get("numeZi");

                JSONArray lectii_jArray = zi_jObject.getJSONArray("lectii");
                for (int j = 0; j < lectii_jArray.length(); j++) {
                    JSONObject lectie_jObect = lectii_jArray.getJSONObject(j);
                    Lectie lectie = new Lectie();

                    lectie.id = (int)lectie_jObect.get("idLectie");
                    lectie.ora = (String)lectie_jObect.get("ora");
                    lectie.nume = (String)lectie_jObect.get("nume");
                    lectie.tip = (String)lectie_jObect.get("tip");
                    lectie.profesor = (String)lectie_jObect.get("profesor");
                    lectie.cabinet = (String)lectie_jObect.get("cabinet");
                    lectie.par = (String)lectie_jObect.get("par");

                    zi.lectii[lectie.id] = lectie;
                }
                zile[zi.id] = zi;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return zile;
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return 4*5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            convertView = getLayoutInflater().inflate(R.layout.zi_content, null);

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.zi_content, parent,false);
            }

            TextView ziSaptaminaText = (TextView) convertView.findViewById(R.id.ziSaptamina);
            TextView numeText = (TextView) convertView.findViewById(R.id.lectie);
            TextView oraText = (TextView) convertView.findViewById(R.id.ora);
            TextView profesorText = (TextView) convertView.findViewById(R.id.profesor);
            TextView tipText = (TextView) convertView.findViewById(R.id.tip);
            TextView cabinetText = (TextView) convertView.findViewById(R.id.nrCabinet);


            ziSaptaminaText.setText(zile[position/4].nume);
            try {
                numeText.setText(zile[position/4].lectii[position%4].nume);
                oraText.setText(Zi.ore[position%4]);
                profesorText.setText(zile[position/4].lectii[position%4].profesor);
                tipText.setText(zile[position/4].lectii[position%4].tip);
                cabinetText.setText(zile[position/4].lectii[position%4].cabinet);
            }
            catch (Exception e)
            {
                numeText.setText("Fereasra");
                oraText.setText(Zi.ore[position%4]);
                profesorText.setText("");
                tipText.setText("");
                cabinetText.setText("");
            }


            return convertView;
        }
    }
}

