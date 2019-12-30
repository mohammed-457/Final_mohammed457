package com.example.aws.Final_mohammed457;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editText extends AppCompatActivity {

    DBHelper mydb;

    Button updatButt;


    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text);

        ArrayAdapter<String> arrayAdapter;

        mydb = new DBHelper(this);

        editTextName = (EditText)findViewById(R.id.editName);
        editTextPhone = (EditText)findViewById(R.id.editPhone);
        editTextEmail = (EditText)findViewById(R.id.editEmail);


            final int gg= getIntent().getExtras().getInt("getID");
        Cursor getData=mydb.getData(gg);

        if (getData.moveToNext()) {
            String dName = getData.getString(getData.getColumnIndex("name"));
            String dPhone = getData.getString(getData.getColumnIndex("phone"));
            String dEmail = getData.getString(getData.getColumnIndex("email"));
            Toast.makeText(getApplicationContext(),
                    "rec: " + dName + ", " + dPhone + ", " + dEmail, Toast.LENGTH_LONG).show();
            editTextName.setText(dName);
            editTextPhone.setText(dPhone);
            editTextEmail.setText(dEmail);
        }
        else
            Toast.makeText(getApplicationContext(),
                    "did not get any data...:-(", Toast.LENGTH_LONG).show();
        getData.close();






        updatButt = (Button) findViewById(R.id.update_btn);
        updatButt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                String dname,dphone,demail;
                dname=editTextName.getText().toString();
                dphone=editTextPhone.getText().toString();
                demail=editTextEmail.getText().toString();
                boolean ffs=mydb.updateContact(gg,dname,dphone,demail);
                if(ffs=true)
                {
                    Intent intent = new Intent(getApplicationContext(),updatLocalPage.class);
                    startActivity(intent);


                }
                else


                    Toast.makeText(getApplicationContext(),
                            "cheek your edit...",Toast.LENGTH_SHORT).show();
                {


                }
            }
        });

    }
}
