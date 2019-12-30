package com.example.aws.Final_mohammed457;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class showAllLocalPage extends AppCompatActivity {

    SQLiteDatabase sqLiteDatabase;
    DBHelper  db;
    ListView listView;
    Cursor cursor;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_local_page);
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

    }

}
