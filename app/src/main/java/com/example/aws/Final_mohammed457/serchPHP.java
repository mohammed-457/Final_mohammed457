package com.example.aws.Final_mohammed457;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class serchPHP extends AppCompatActivity {
    EditText search_editText;
    Button search_btn;
    ListView listView;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch_php);
        this.setTitle("SEARCH PHP SCREEN");

        search_btn = (Button) findViewById(R.id.search_btn);
        listView = (ListView) findViewById(R.id.listView);

        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                search_editText = (EditText) findViewById(R.id.search_editText);
                if (search_editText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "MUST ENTER THE DATA .. )!", Toast.LENGTH_SHORT).show();
                    search_editText.setError("MUST ENTER VALUE");

                } else {
                    search_editText =(EditText) findViewById(R.id.search_editText);
                    String ss = search_editText.getText().toString();
                    Toast.makeText(getApplicationContext(),
                            ss, Toast.LENGTH_SHORT).show();
                    listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
                    listView.setAdapter(listAdapter);
                    getJSON("http://172.20.10.3/sqli/search.php?idOfSerch="+ss);
                    search_editText.setText("");

                }
            }
        });
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
        listView.setAdapter(null);// delete all old from list and show agin
        listAdapter.clear();
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(listAdapter);
        String id = "";
        String name = "";
        String phone = "";
        String email = "";
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = String.valueOf(obj.getInt("id"));
            id = heroes[i];
            heroes[i] = obj.getString("name");
            name = heroes[i];
            heroes[i] = obj.getString("phone");
            phone = heroes[i];
            heroes[i] = obj.getString("email");
            email = heroes[i];
            DataProvider dataProvider = new DataProvider(id, name, email, phone);
            listAdapter.add(dataProvider);
        }

        listView.setAdapter(listAdapter);

    }

}
