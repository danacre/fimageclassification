package com.fypic.imageclassification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private Button btnSave,btnDelete,btnMetal,btnPaper,btnPlastic,btnWaste;
    private EditText edititem,editid;

    DatabaseHelper db;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnMetal = (Button) findViewById(R.id.btnMetal);
        btnPaper = (Button) findViewById(R.id.btnPaper);
        btnPlastic = (Button) findViewById(R.id.btnPlastic);
        btnWaste = (Button) findViewById(R.id.btnWaste);

        edititem = (EditText) findViewById(R.id.edititem);
        editid = (EditText) findViewById(R.id.edit_id);
        db = new DatabaseHelper(this);

        Intent editIntent = getIntent();

        selectedID = editIntent.getIntExtra("id",-1);

        selectedName = editIntent.getStringExtra("name");

        //set the text to show the current selected name
        editid.setText(Integer.toString(selectedID));
        edititem.setText(selectedName);

        btnMetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edititem.setText("Metal");
            }
        });

        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edititem.setText("Paper");
            }
        });

        btnPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edititem.setText("Plastic");
            }
        });

        btnWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edititem.setText("Waste");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edititem.getText().toString();
                db.updateObjectMaterial(item,selectedID,selectedName);
                Intent intent=new Intent(EditActivity.this, MainActivityLogged.class);
                startActivity(intent);
                Toast.makeText(EditActivity.this,"Updated", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteObject(selectedID,selectedName);
                Intent intent=new Intent(EditActivity.this, MainActivityLogged.class);
                startActivity(intent);
                Toast.makeText(EditActivity.this,"Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
