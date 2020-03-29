package app.islammedia.halaqatalquran;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.islammedia.halaqatalquran.database_room.Halaqa;
import app.islammedia.halaqatalquran.database_room.MainDataBase;
import app.islammedia.halaqatalquran.database_room.Student;

public class AddingStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_student);
        final MainDataBase db = Room.databaseBuilder(this,MainDataBase.class,"MainDataBase").build();
        final EditText stName = findViewById(R.id.studentName);
        final EditText stInfo = findViewById(R.id.studentInfo);
        final EditText stHWS = findViewById(R.id.studentHWS);
        Button saveB = findViewById(R.id.saveButton2);
        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stName.getText().toString().equals("")){
                    new AlertDialog.Builder(AddingStudentActivity.this)
                            .setTitle(R.string.stNamePlease)
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                }else {
                    db.myDAO().insertStudent(new Student(stName.getText().toString()
                            ,stInfo.getText().toString()
                            ,stHWS.getText().toString()));
                }
            }
        });
    }
}
