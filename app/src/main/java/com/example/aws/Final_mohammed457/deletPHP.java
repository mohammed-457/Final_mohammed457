package com.example.aws.Final_mohammed457;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class deletPHP extends Activity {
    Syn syn;

    Button onDelete;
    ListView listView;
    ListAdapter listAdapter;


    EditText delete_text;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delet_php);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
        getJSON("http://172.20.10.3/sqli/getdata.php");


        onDelete = (Button) findViewById(R.id.onDelete);


        delete_text = (EditText) findViewById(R.id.delte_text);

        syn = new Syn();


        onDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_text = delete_text.getText().toString();
                int id_new = new Integer(id_text);
                String msg = syn.doInDelete(0, id_new);
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();

                getJSON("http://172.20.10.3/sqli/getdata.php");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView idText = view.findViewById(R.id.IV_title_o);
                final String idGetText = idText.getText().toString();

                AlertDialog.Builder alert = new AlertDialog.Builder(deletPHP.this);
                alert.setTitle("Delete!");
                alert.setMessage("Do you Sure want delete ?");
                alert.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int ss = new Integer(idGetText).intValue();
                                syn.doInDelete(0,ss);
                                getJSON("http://172.20.10.3/sqli/getdata.php");



                            }
                        });

                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(deletPHP.this, "No", Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.show();

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
                    // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        listView.setAdapter(null);
        listAdapter.clear();
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(listAdapter);


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
                DataProvider dataProvider = new DataProvider(id,name,phone,email);
                listAdapter.add(dataProvider);
            }

            listView.setAdapter(listAdapter);

        }

    }




