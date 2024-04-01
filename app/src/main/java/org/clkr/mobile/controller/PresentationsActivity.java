package org.clkr.mobile.controller;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.clkr.mobile.R;
import org.clkr.mobile.model.Presentation;

import java.util.ArrayList;
import java.util.List;

public class PresentationsActivity extends AppCompatActivity {
    private ListView presentationsListView;
    private PresentationAdapter presentationAdapter;

    private List<Presentation> presentations = List.of(new Presentation()); //new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Presentations");
        }

        presentationsListView = findViewById(R.id.presentationsListView);
        presentationsListView.setEmptyView(findViewById(android.R.id.empty));

        presentationAdapter = new PresentationAdapter(this, presentations);
        presentationsListView.setAdapter(presentationAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}