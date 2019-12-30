package com.example.aws.Final_mohammed457;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class updatePHP extends AppCompatActivity {

    ListView listView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_php);
        this.setTitle("UPDATE PHP SCREEN");

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
        getJSON("http://172.20.10.3/sqli/getdata.php");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView idText = view.findViewById(R.id.IV_title_o);
                final String idGetText = idText.getText().toString();
                int id_2=new Integer(idGetText).intValue();
                Intent intent = new Intent(getApplicationContext(),UpdateEditPHP.class);
                intent.putExtra("id_new",id_2);
                System.out.println(id_2);
                startActivity(intent);
                finish();
            }
        });

    }


    private void getJSON(final String urlWebService) {

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

    private void loadIntoListView(String json) throws JSONException {
        ArrayList<DataProvider> arrayList = new ArrayList<DataProvider>();
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = String.valueOf(obj.getInt("id"));
            String id= heroes[i];
            heroes[i] = obj.getString("name");
            String name =  heroes[i];
            heroes[i] = obj.getString("phone");
            String phone =  heroes[i];
            heroes[i] = obj.getString("email");
            String email =  heroes[i];
            DataProvider dataProvider = new DataProvider(id,name,email,phone);
            listAdapter.add(dataProvider);
        }

        listView.setAdapter(listAdapter);

    }

}