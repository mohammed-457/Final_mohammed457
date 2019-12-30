package com.example.aws.Final_mohammed457;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class updatLocalPage extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    DBHelper  db;
    ListView listView;
    Cursor cursor;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updat_local_page);
        listView=(ListView)findViewById(R.id.listView);
        db = new DBHelper(getApplicationContext());
        sqLiteDatabase=db.getReadableDatabase();
        cursor=db.getAllData();
        listAdapter=new ListAdapter(getApplicationContext(),R.layout.row_layout);
        listView.setAdapter(listAdapter);
        if(cursor.moveToFirst())
        {

            do
            {

                String name,mobile,email;
                String id;
                id=cursor.getString(0);
                name=cursor.getString(1);
                mobile=cursor.getString(2);
                email=cursor.getString(3);
                DataProvider dataProvider=new DataProvider(id,name,mobile,email);
                listAdapter.add(dataProvider);


            }while (cursor.moveToNext());

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final TextView id_=view.findViewById(R.id.IV_title_o);

                String getId = id_.getText().toString();
                int intId = new Integer(getId);

                Intent intent = new Intent(getApplicationContext(),editText.class);
                intent.putExtra("getID",intId);
                startActivity(intent);

            }
        });

    }

}
