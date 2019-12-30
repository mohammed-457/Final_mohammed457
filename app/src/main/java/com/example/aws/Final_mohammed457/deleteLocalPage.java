package com.example.aws.Final_mohammed457;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class deleteLocalPage extends AppCompatActivity {
    DBHelper mydb;
    EditText delte_text;
    Button delte;
    SQLiteDatabase sqLiteDatabase;
    DBHelper db;
    ListView listView;
    Cursor cursor;
    ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_local_page);
        mydb = new DBHelper(this);
        delte = (Button) findViewById(R.id.delte);

        listView = (ListView) findViewById(R.id.listView);
        db = new DBHelper(getApplicationContext());
        sqLiteDatabase = db.getReadableDatabase();
        cursor = db.getAllData();
        listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(listAdapter);
        if (cursor.moveToFirst()) {

            do {

                String name, mobile, email;
                String id;
                id = cursor.getString(0);
                name = cursor.getString(1);
                mobile = cursor.getString(2);
                email = cursor.getString(3);
                DataProvider dataProvider = new DataProvider(id, name, mobile, email);
                listAdapter.add(dataProvider);


            } while (cursor.moveToNext());

        }



        delte.setOnClickListener(new View.OnClickListener()

        {

            public void onClick (View v){
                delte_text = (EditText) findViewById(R.id.delte_text);
                String id_1 = delte_text.getText().toString();
                int ss = new Integer(id_1).intValue();
                mydb.deleteContact(ss);

                cursor = db.getAllData();
                listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
                listView.setAdapter(listAdapter);
                if (cursor.moveToFirst()) {

                    do {

                        String name, mobile, email;
                        String id;
                        id = cursor.getString(0);
                        name = cursor.getString(1);
                        mobile = cursor.getString(2);
                        email = cursor.getString(3);
                        DataProvider dataProvider = new DataProvider(id, name, mobile, email);
                        listAdapter.add(dataProvider);


                    } while (cursor.moveToNext());

                }



            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView idText = view.findViewById(R.id.IV_title_o);
                final String idGetText = idText.getText().toString();

                AlertDialog.Builder alert = new AlertDialog.Builder(deleteLocalPage.this);
                alert.setTitle("Delete!");
                alert.setMessage("Do you Sure want delete ?");
                alert.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int ss = new Integer(idGetText).intValue();
                                mydb.deleteContact(ss);


                                cursor = db.getAllData();
                                listAdapter = new ListAdapter(getApplicationContext(), R.layout.row_layout);
                                listView.setAdapter(listAdapter);
                                if (cursor.moveToFirst()) {

                                    do {

                                        String name, mobile, email;
                                        String id;
                                        id = cursor.getString(0);
                                        name = cursor.getString(1);
                                        mobile = cursor.getString(2);
                                        email = cursor.getString(3);
                                        DataProvider dataProvider = new DataProvider(id, name, mobile, email);
                                        listAdapter.add(dataProvider);


                                    } while (cursor.moveToNext());

                                }



                            }
                        });

                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(deleteLocalPage.this, "No", Toast.LENGTH_SHORT).show();
                            }
                        });
                alert.show();

            }
        });

    }
}
