package com.example.aws.Final_mohammed457;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LastREClocalPage extends AppCompatActivity {

    Button last_row;
    EditText name_editText;
    EditText email_editText;
    EditText phone_editText;

    DBHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_reclocal_page);
             mydb=new DBHelper(getApplicationContext());


        last_row = (Button) findViewById(R.id.update_btn);

        last_row.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("georgeLog", "clicked on fetch");
                Cursor getData=mydb.lastRow(); //specific record (id=1)

                if (getData.moveToNext()) {// data?
                    Log.v("georgeLog", "data found in DB...");
                    String dName = getData.getString(getData.getColumnIndex("name"));
                    String dPhone = getData.getString(getData.getColumnIndex("phone"));
                    String dEmail = getData.getString(getData.getColumnIndex("email"));
                    Toast.makeText(getApplicationContext(),
                            "rec: " + dName + ", " + dPhone + ", " + dEmail, Toast.LENGTH_LONG).show();
                    name_editText = (EditText) findViewById(R.id.editName);
                    email_editText = (EditText) findViewById(R.id.editEmail);
                    phone_editText = (EditText) findViewById(R.id.editPhone);


                   name_editText.setText(dName);
                    email_editText.setText(dEmail);
                    phone_editText.setText(dPhone);

                }
                else
                    Toast.makeText(getApplicationContext(),
                            "did not get any data...:-(", Toast.LENGTH_LONG).show();
                getData.close();
            }
        });












    }

}