package com.example.user.orarasem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.orarasem.classes.Lectie;
import com.example.user.orarasem.classes.Zi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class OrarActivity extends AppCompatActivity {

    Zi[] zile = new Zi[5];
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);



        zile = getZiArray();

        listView = (ListView) findViewById(R.id.ziList);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        int currentTime = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        listView.setSelection(15);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.get(Calendar.HOUR);
        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday

            case Calendar.MONDAY:
                // Current day is Monday

            case Calendar.TUESDAY:
                // etc.
        }
    }


    public int getLessonNumber(Calendar calendar) {
        //        if(calendar.get(Calendar.HOUR) == 8) || (calendar.get(Calendar.HOUR) == 9 && calendar.get(Calendar.))
        return 0;
    }

    public Zi[] getZiArray() {
        Zi[] zile = new Zi[5];

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

                zi.id = (int) zi_jObject.get("idZi");
                zi.nume = (String) zi_jObject.get("numeZi");

                JSONArray lectii_jArray = zi_jObject.getJSONArray("lectii");
                for (int j = 0; j < lectii_jArray.length(); j++) {
                    JSONObject lectie_jObect = lectii_jArray.getJSONObject(j);
                    Lectie lectie = new Lectie();

                    lectie.id = (int) lectie_jObect.get("idLectie");
                    lectie.nume = (String) lectie_jObect.get("nume");
                    lectie.tip = (String) lectie_jObect.get("tip");
                    lectie.profesor = (String) lectie_jObect.get("profesor");
                    lectie.cabinet = (String) lectie_jObect.get("cabinet");
                    lectie.par = (String) lectie_jObect.get("par");

                    zi.lectii[lectie.id] = lectie;
                }
                zile[zi.id] = zi;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return zile;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 4 * 5;
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
                convertView = getLayoutInflater().inflate(R.layout.zi_content, parent, false);
            }

            TextView ziSaptaminaText = (TextView) convertView.findViewById(R.id.ziSaptamina);
            TextView numeText = (TextView) convertView.findViewById(R.id.lectie);
            TextView oraText = (TextView) convertView.findViewById(R.id.ora);
            TextView profesorText = (TextView) convertView.findViewById(R.id.profesor);
            TextView tipText = (TextView) convertView.findViewById(R.id.tip);
            TextView cabinetText = (TextView) convertView.findViewById(R.id.nrCabinet);

            LinearLayout ziTotalLayout = (LinearLayout) convertView.findViewById(R.id.sumarZi);
            TextView lectie1 = (TextView) convertView.findViewById(R.id.lectie1);
            TextView lectie2 = (TextView) convertView.findViewById(R.id.lectie2);
            TextView lectie3 = (TextView) convertView.findViewById(R.id.lectie3);
            TextView lectie4 = (TextView) convertView.findViewById(R.id.lectie4);
            TextView numeSaptanima = (TextView) convertView.findViewById(R.id.numeSaptamina);

            if (position % 4 != 0) {
                ziTotalLayout.setVisibility(LinearLayout.GONE);
                //            ziTotalLayout.setVisibility(LinearLayout.GONE);
            } else {
                ziTotalLayout.setVisibility(LinearLayout.VISIBLE);
                numeSaptanima.setText(zile[position / 4].nume);
                try {
//                    lectie1.setVisibility(TextView.VISIBLE);
                    lectie1.setText(zile[position / 4].lectii[position % 4].nume);
                } catch (Exception e) {
//                    lectie1.setVisibility(TextView.GONE);
                    lectie1.setText("");
                }
                try {
//                    lectie2.setVisibility(TextView.VISIBLE);
                    lectie2.setText(zile[position / 4].lectii[position % 4 + 1].nume);
                } catch (Exception e) {
//                    lectie2.setVisibility(TextView.GONE);
                    lectie2.setText("");
                }
                try {
//                    lectie3.setVisibility(TextView.VISIBLE);
                    lectie3.setText(zile[position / 4].lectii[position % 4 + 2].nume);
                } catch (Exception e) {
//                    lectie3.setVisibility(TextView.GONE);
                    lectie3.setText("");
                }
                try {
//                    lectie4.setVisibility(TextView.VISIBLE);
                    lectie4.setText(zile[position / 4].lectii[position % 4 + 3].nume);
                } catch (Exception e) {
//                    lectie4.setVisibility(TextView.GONE);
                    lectie4.setText("");
                }
            }

            ziSaptaminaText.setVisibility(TextView.VISIBLE);
            ziSaptaminaText.setText(zile[position / 4].nume);
            try {

                numeText.setVisibility(TextView.VISIBLE);
                oraText.setVisibility(TextView.VISIBLE);
                profesorText.setVisibility(TextView.VISIBLE);
                tipText.setVisibility(TextView.VISIBLE);
                cabinetText.setVisibility(TextView.VISIBLE);

                numeText.setText(zile[position / 4].lectii[position % 4].nume);
                oraText.setText(Zi.ore[position % 4]);
                profesorText.setText(zile[position / 4].lectii[position % 4].profesor);
                tipText.setText(zile[position / 4].lectii[position % 4].tip);
                cabinetText.setText(zile[position / 4].lectii[position % 4].cabinet);
            } catch (Exception e) {
                if (position % 4 == 3) {
                    ziSaptaminaText.setVisibility(TextView.GONE);
                    numeText.setVisibility(TextView.GONE);
                    oraText.setVisibility(TextView.GONE);
                    profesorText.setVisibility(TextView.GONE);
                    tipText.setVisibility(TextView.GONE);
                    cabinetText.setVisibility(TextView.GONE);
                } else {
                    numeText.setText("Fereasra");
                    oraText.setText(Zi.ore[position % 4]);
                    profesorText.setText("");
                    tipText.setText("");
                    cabinetText.setText("");
                }
            }


            return convertView;
        }
    }
}
