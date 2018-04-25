package com.example.user.orarasem.classes;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by User on 17.03.2018.
 */

public class Zi {
    public int id;
    public String nume;
    public Lectie[] lectii = new Lectie[4];
    public static final String[] ore = {"08:00- 09:30", "09:40- 11:10", "11:20- 12:50", "13:00- 14:30"};

    public static void putJSONinSP(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        String json = sharedPref.getString("jsonData", "[]");

        try {
            InputStream is = context.getAssets().open("orarASEM.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            sharedPref.edit().putString("jsonData",json).apply();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }



    public static Zi[] getDataFromSP (Context context) {
        Zi[] zile = new Zi[5];

        SharedPreferences sharedPref = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        String json = sharedPref.getString("jsonData", "[]");

        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray saptamina_jArray = jsonObj.getJSONArray("saptamina");
            for (int i = 0; i < saptamina_jArray.length(); i++) {
                JSONObject zi_jObject = saptamina_jArray.getJSONObject(i);
                Zi zi = new Zi();
                zi.id = (int)zi_jObject.get("idZi");
                zi.nume = (String)zi_jObject.get("numeZi");

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
}
