package com.fypic.imageclassification;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {

    DatabaseHelper db;
    private ListView lv;
    int fl;
    int sl;
    Button allBtn,paperBtn, metalBtn, plasticBtn, wasteBtn, summaryBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = (ListView) findViewById(R.id.listView);
        db = new DatabaseHelper(this);
        allBtn = findViewById(R.id.allBtn);
        paperBtn = findViewById(R.id.paperBtn);
        metalBtn = findViewById(R.id.metalBtn);
        plasticBtn = findViewById(R.id.plasticBtn);
        wasteBtn = findViewById(R.id.wasteBtn);
        summaryBtn = findViewById(R.id.summarybutton);

        Cursor data = db.getData();
        Cursor alldata = db.getAllData();
        Cursor paperdata = db.getPaperData();
        Cursor metaldata = db.getMetalData();
        Cursor plasticdata = db.getPlasticData();
        Cursor wastedata = db.getWasteData();

        ArrayList<Object> listObj = new ArrayList<>();
        ArrayList<Object> allObj = new ArrayList<>();
        ArrayList<Object> paperObj = new ArrayList<>();
        ArrayList<Object> metalObj = new ArrayList<>();
        ArrayList<Object> plasticObj = new ArrayList<>();
        ArrayList<Object> wasteObj = new ArrayList<>();

        while(data.moveToNext()){
            fl = data.getInt(1);
            sl = data.getInt(0);

            Object obj = new Object(sl,fl);
            listObj.add(obj);
        }

        while(alldata.moveToNext()){
            fl = alldata.getInt(1);
            sl = alldata.getInt(0);

            Object obj = new Object(sl,fl);
            allObj.add(obj);
        }

        while(paperdata.moveToNext()){
            fl = paperdata.getInt(1);
            sl = paperdata.getInt(0);

            Object obj = new Object(sl,fl);
            paperObj.add(obj);
        }

        while(metaldata.moveToNext()){
            fl = metaldata.getInt(1);
            sl = metaldata.getInt(0);

            Object obj = new Object(sl,fl);
            metalObj.add(obj);
        }

        while(plasticdata.moveToNext()){
            fl = plasticdata.getInt(1);
            sl = plasticdata.getInt(0);

            Object obj = new Object(sl,fl);
            plasticObj.add(obj);
        }

        while(wastedata.moveToNext()){
            fl = wastedata.getInt(1);
            sl = wastedata.getInt(0);

            Object obj = new Object(sl,fl);
            wasteObj.add(obj);
        }


        ObjListAdapter adapter;

        adapter = new ObjListAdapter(this, R.layout.adap_layout, listObj);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent editScreenIntent = new Intent(ListActivity.this, EditActivity.class);
                editScreenIntent.putExtra("id",listObj.get(position).getId());
                editScreenIntent.putExtra("name",listObj.get(position).getMat());
                startActivity(editScreenIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                adapter.addAll(allObj);
                adapter.notifyDataSetChanged();
            }
        });

        paperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                adapter.addAll(paperObj);
                adapter.notifyDataSetChanged();
            }
        });

        metalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                adapter.addAll(metalObj);
                adapter.notifyDataSetChanged();
            }
        });

        plasticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                adapter.addAll(plasticObj);
                adapter.notifyDataSetChanged();
            }
        });

        wasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                adapter.addAll(wasteObj);
                adapter.notifyDataSetChanged();
            }
        });

        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListActivity.this, SummaryActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}