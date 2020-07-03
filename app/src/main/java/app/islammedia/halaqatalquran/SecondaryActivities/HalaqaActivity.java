package app.islammedia.halaqatalquran.SecondaryActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.Database.Halaqa;
import app.islammedia.halaqatalquran.Database.MainDataBase;

public class HalaqaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaqa);

        final MainDataBase db = Room.databaseBuilder(this,MainDataBase.class,"MainDataBase").build();

        final EditText halaqaName = findViewById(R.id.halaqaName);
        final EditText halaqaTime = findViewById(R.id.halaqaTime);
        final EditText halaqaInfo = findViewById(R.id.halaqaInfo);
        Button save = findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (halaqaName.getText().toString().equals("")) {
                    new AlertDialog.Builder(HalaqaActivity.this)
                            .setTitle(R.string.namePlease)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }else {
                    db.myDAO().insertHalaqa(new Halaqa(halaqaName.getText().toString()
                            ,halaqaTime.getText().toString()
                            ,halaqaInfo.getText().toString()));
                }
            }
        });

    }



}
