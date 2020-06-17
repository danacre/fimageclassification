package com.fypic.imageclassification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    TextView metalcount,plasticcount,papercount,wastecount;
    int mcount,plcount,pacount,wcount;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        db = new DatabaseHelper(this);

        metalcount = findViewById(R.id.metalcount);
        plasticcount = findViewById(R.id.plasticcount);
        papercount = findViewById(R.id.papercount);
        wastecount = findViewById(R.id.wastecount);

        mcount = db.getMatMetalCount();
        plcount = db.getMatPlasticCount();
        pacount = db.getMatPaperCount();
        wcount = db.getMatWasteCount();

        metalcount.setText(Integer.toString(mcount));
        plasticcount.setText(Integer.toString(plcount));
        papercount.setText(Integer.toString(pacount));
        wastecount.setText(Integer.toString(wcount));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
