package com.example.aws.Final_mohammed457;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UpdateEditPHP extends AppCompatActivity {

    Button update_btn;
    EditText name_editText;
    EditText email_editText;
    EditText phone_editText;
    Syn syn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_edit_php);
        this.setTitle("UPDATE EDIT PHP SCREEN");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        syn=new Syn();

        name_editText = (EditText) findViewById(R.id.name_editText);
        email_editText = (EditText) findViewById(R.id.email_editText);
        phone_editText = (EditText) findViewById(R.id.phone_editText);
        update_btn = (Button) findViewById(R.id.update_btn);
        final int getID = getIntent().getExtras().getInt("id_new");
        getJSON("http://172.20.10.3/sqli/getdatabyid.php?username="+getID);


        update_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(name_editText.getText().toString().trim().length() == 0 || email_editText.getText().toString().trim().length() == 0 || phone_editText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "MUST INSERT ALL DATA !!!", Toast.LENGTH_SHORT).show();
                }
                else {

                    String name = name_editText.getText().toString();
                    String phone = phone_editText.getText().toString();
                    String email = email_editText.getText().toString();
                    String id=String.valueOf(getID);
                    String msg = syn.doInUpdate_inphp("update",name, email, phone);
                    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), updatePHP.class);
                    startActivity(intent);
                    finish();


                }
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

    String name,phone,email;
    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = obj.getString("name");
            name =  heroes[i];
            heroes[i] = obj.getString("phone");
            phone =  heroes[i];
            heroes[i] = obj.getString("email");
            email =  heroes[i];

        }

        name_editText.setText(name);
        phone_editText.setText(phone);
        email_editText.setText(email);

    }

}