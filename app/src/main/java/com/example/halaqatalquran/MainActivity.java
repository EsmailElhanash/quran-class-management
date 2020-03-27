package com.example.halaqatalquran;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.halaqatalquran.HalaqatAdapter.HalaqatAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu,menu);
        return true;
    }

    public void addHalaqa(MenuItem item) {
        startActivity(new Intent(getApplicationContext(),AddingHalaqaActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpHalaqatRecyclerView();





    }

    private void setUpToolbar(){
        Toolbar myMainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(myMainToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setUpHalaqatRecyclerView(){
        RecyclerView halaqatRecyclerView = findViewById(R.id.halaqati);
        RecyclerView.Adapter halaqatAdapter = new HalaqatAdapter();
        halaqatRecyclerView.setAdapter(halaqatAdapter);
    }
}
