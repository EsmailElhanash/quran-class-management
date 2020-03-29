package app.islammedia.halaqatalquran;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu,menu);
        return true;
    }

    public void addHalaqa() {
        startActivity(new Intent(getApplicationContext(),AddingHalaqaActivity.class));
    }

    public void addStudent() {
        startActivity(new Intent(getApplicationContext(),AddingStudentActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
    }

    public void addNew_(View clickedButton){
        PopupMenu popupMenu = new PopupMenu(this,clickedButton);
        popupMenu.getMenuInflater().inflate(R.menu.add_new_halaqa_or_student_popup_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.addHalaqa:addHalaqa();return true;
                    case R.id.addStudent:addStudent();return true;
                    default:return false;
                }
            }
        });
        popupMenu.show();
    }

    private void setUpToolbar(){
        Toolbar myMainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(myMainToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
