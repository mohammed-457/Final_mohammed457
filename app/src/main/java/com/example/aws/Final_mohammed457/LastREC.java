package com.example.aws.Final_mohammed457;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LastREC extends AppCompatActivity {

    EditText name_editText;
    EditText email_editText;
    EditText phone_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_rec);
        this.setTitle("LAST ROW PHP SCREEN");
        name_editText = (EditText) findViewById(R.id.editName);
        email_editText = (EditText) findViewById(R.id.editPhone);
        phone_editText = (EditText) findViewById(R.id.editEmile);
        getJSON("http://172.20.10.3/sqli/lastRow.php");
    }
    private void getJSON ( final String urlWebService){

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView (String json) throws JSONException {
        String name = "";
        String phone = "";
        String email = "";
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = obj.getString("name");
            name = heroes[i];
            heroes[i] = obj.getString("phone");
            phone = heroes[i];
            heroes[i] = obj.getString("email");
            email = heroes[i];
        }

        name_editText.setText(name);
        phone_editText.setText(phone);
        email_editText.setText(email);

    }

}