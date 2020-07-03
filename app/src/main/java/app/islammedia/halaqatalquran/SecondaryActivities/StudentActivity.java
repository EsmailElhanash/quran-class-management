package app.islammedia.halaqatalquran.SecondaryActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.islammedia.halaqatalquran.HelpingClasses.HomeworkClass;
import app.islammedia.halaqatalquran.MainActivity;
import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.Database.HomeWork;
import app.islammedia.halaqatalquran.Database.MainDataBase;
import app.islammedia.halaqatalquran.Database.Student;
import app.islammedia.halaqatalquran.Database.StudentsAndHomeWorksCrossRef;



// Activity for Student : View , Add , Edit
public class StudentActivity extends AppCompatActivity  {

    EditText stName;
    EditText stInfo;
    ProgressBar progressBar;
    Button add_hw_btn;
    ViewGroup hws_holder;
    List<String> AlSowar;
    public List<HomeworkClass> hws = new ArrayList<>();
    MainDataBase db;

    public static final int VIEW = 0;
    public static final int ADD = 1;
    public static final int EDIT = 2;
    public static final String MODE_TEXT = "MODE";




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        db = Room.databaseBuilder(this,MainDataBase.class,"MainDataBase").build();
        AlSowar = Arrays.asList(getResources().getStringArray(R.array.sura_names));
        progressBar = findViewById(R.id.student_activity_progress_bar);
        stName = findViewById(R.id.studentName);
        stInfo = findViewById(R.id.studentInfo);
        hws_holder = findViewById(R.id.added_hws_holder);

        Bundle b = getIntent().getExtras();
        int mode = 0;
        String studentId = null;

        if (b!=null) {
            mode = b.getInt("MODE", 0);
            studentId = b.getString("studentId", null);
        }

        switch (mode){
            case 1://Add
                //System.out.println("mode=1_add");
                addStudentMode();
                break;

            case 2://Edit
                if (studentId == null)
                    break;
                editStudentMode(studentId);
                break;

            default:
                //System.out.println("mode=0_view");
                if (studentId == null)
                    break;

                viewStudentMode(studentId);
                break;
        }

    }

    private void viewStudentMode(@NonNull String studentId){
        setUpToolbar(R.string.view_student);
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.saveButton2).setVisibility(View.GONE);
        findViewById(R.id.add_hw_btn).setVisibility(View.GONE);

        Runnable getStudent = () -> {

            Student s = db.myDAO().getStudent(studentId);
            List<HomeWork> homeWorkList = new ArrayList<>();

            List<String> hwsIDs = db.myDAO().getStudent_HomeWorks(s.getIdName());

            for (String stHw : hwsIDs)
                homeWorkList.add(db.myDAO().getHomeWork(stHw));

            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                stName.setText(s.getName());
                stInfo.setText(s.getInfo());
                stName.setEnabled(false);//android.R.color.transparent
                stName.setBackgroundResource(android.R.color.transparent);
                stName.setTextColor(getResources().getColor(android.R.color.black));
                stInfo.setEnabled(false);
                stInfo.setBackgroundResource(android.R.color.transparent);
                stInfo.setTextColor(getResources().getColor(android.R.color.black));

                for (HomeWork stHw : homeWorkList)
                    new HomeworkClass(StudentActivity.this,hws_holder,0,stHw);

                progressBar.setVisibility(View.GONE);

            });
        };
        new Thread(getStudent).start();

    }

    private void editStudentMode(@NonNull String studentId){
        progressBar.setVisibility(View.VISIBLE);
        setUpToolbar(R.string.edit_student_title);
        Map < HomeworkClass , Boolean > homeWorksNewClassification = new HashMap<>();

        //
        Runnable getStudentAndHomeWorks = () -> {


            // we have got the student -> s
            Student s = db.myDAO().getStudent(studentId);

            List<String> thisStudentHomeWorksIDs = db.myDAO().
                    getStudent_HomeWorks(s.getIdName());

            // we have got the student's HomeWorks -> hisHomeWorks
            List<HomeWork> hisHomeWorks = new ArrayList<>();

            for (String str : thisStudentHomeWorksIDs)
                hisHomeWorks.add(db.myDAO().getHomeWork(str));




            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                stName.setText(s.getName());
                stInfo.setText(s.getInfo());

                for (HomeWork h:hisHomeWorks){
                    HomeworkClass hw = new HomeworkClass(StudentActivity.this,
                            hws_holder,EDIT,h);
                    hws.add(hw);
                    homeWorksNewClassification.put(hw,false);
                }

            });
        };

        new Thread(getStudentAndHomeWorks).start();

        add_hw_btn = findViewById(R.id.add_hw_btn);
        add_hw_btn.setOnClickListener(view ->
                {
                    HomeworkClass hw = new HomeworkClass(this,hws_holder,ADD,null);
                    homeWorksNewClassification.put(hw,true);
                    hws.add(hw);
                }
        );
        Button saveB = findViewById(R.id.saveButton2);

        Runnable updateStudentAndHomeWorks = () -> {
            db.myDAO().updateStudent(studentId,stName.getText().toString(),stInfo.getText().toString());
            for (HomeworkClass hw : hws){
                Boolean isNew = homeWorksNewClassification.get(hw);
                if (isNew != null && isNew){
                    String s1 = (String) hw.sura1.getSelectedItem();
                    String s2 = (String) hw.sura2.getSelectedItem();
                    int a1 = (int) hw.ayah1.getSelectedItem();
                    int a2 = (int) hw.ayah2.getSelectedItem();
                    String hwID = s1 + a1 + "_"+s2+a2+"_"+System.currentTimeMillis();
                    db.myDAO().insertHomework(new HomeWork(hw.hw_title.getText().toString(),
                            hw.curPickedDate,
                            s1,
                            s2,
                            a1,
                            a2,
                            hwID));
                    db.myDAO().insertStudentsAndHomeWorksCrossRef(new StudentsAndHomeWorksCrossRef(studentId,hwID));
                }else{
                    /*hw.getHw().getTitle(),hw.getHw().getDueDate(),
                            hw.getHw().getSura1(),hw.getHw().getSura2(),hw.getHw().getAyah1(),hw.getHw().getAyah2()*/
                    db.myDAO().updateHomeWork(hw.getHw().getIdTitle(),hw.getHw_title().getText().toString(),
                            hw.getCurPickedDate(),String.valueOf(hw.getSura1().getSelectedItem()),
                            String.valueOf(hw.getSura2().getSelectedItem()),
                            (int)hw.getAyah1().getSelectedItem(),
                            (int)hw.getAyah2().getSelectedItem());
                }
            }

            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
            });

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        };

        saveB.setOnClickListener(v -> {
            if (stName.getText().toString().equals("")){
                new AlertDialog.Builder(StudentActivity.this)
                        .setTitle(R.string.stNamePlease)
                        .setNeutralButton(android.R.string.ok, (dialog, which) -> dialog.dismiss()).create().show();
            }else {
                progressBar.setVisibility(View.VISIBLE);

                Thread saveStudent = new Thread(updateStudentAndHomeWorks);
                saveStudent.start();
            }
        });


    }

    private void addStudentMode(){

        setUpToolbar(R.string.add_student);
        add_hw_btn = findViewById(R.id.add_hw_btn);
        add_hw_btn.setOnClickListener(view ->
                {
                    hws.add(new HomeworkClass(this,hws_holder,1,null));
                }
        );




        Button saveB = findViewById(R.id.saveButton2);

        saveB.setOnClickListener(v -> {
            if (stName.getText().toString().equals("")){
                new AlertDialog.Builder(StudentActivity.this)
                        .setTitle(R.string.stNamePlease)
                        .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }else {
                progressBar.setVisibility(View.VISIBLE);

                Thread saveStudent = new Thread(addStudent);

                final boolean[] stop = {false};

                for (HomeworkClass hw : hws)
                    if (hw.hw_title.getText().toString().equals("")) {
                        new AlertDialog.Builder(this).setTitle("يوجد واجبات بدون عنوان ، الإستمرار؟")
                                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                                    saveStudent.start();
                                })
                                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> stop[0]=true)
                                .create().show();

                        if (stop[0])
                            return;
                    }
                if (! stop[0])
                    new Thread(addStudent).start();
            }
        });
    }

    private void setUpToolbar(int toolBarTitle) {


        Toolbar mToolbar = findViewById(R.id.student_activity_toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar()==null) return;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle(toolBarTitle);



    }

    Runnable addStudent = () -> {

        List<HomeWork> dbHws = new ArrayList<>();

        String sName = stName.getText().toString();
        String sid = sName +"_"+ System.currentTimeMillis();
        Student s = new Student(sName,
                stInfo.getText().toString(),
                sid);

        //ArrayList<String> assigned = new ArrayList<>();
        //assigned.add(s.getName());

        List<String> hwsIDs = new ArrayList<>();
        for (HomeworkClass hw : hws){
            String s1 = (String) hw.sura1.getSelectedItem();
            String s2 = (String) hw.sura2.getSelectedItem();
            int a1 = (int) hw.ayah1.getSelectedItem();
            int a2 = (int) hw.ayah2.getSelectedItem();
            String hwID = s1 + a1 + "_"+s2+a2+"_"+System.currentTimeMillis();
            hwsIDs.add(hwID);
            dbHws.add(new HomeWork(hw.hw_title.getText().toString(),
                    hw.curPickedDate,
                    s1,
                    s2,
                    a1,
                   a2,
                    hwID));
        }

        db.myDAO().insertStudent(s);
        db.myDAO().insertHomework(dbHws.toArray(new HomeWork[0]));

        for (String str : hwsIDs)
            db.myDAO().insertStudentsAndHomeWorksCrossRef(new StudentsAndHomeWorksCrossRef(
                    sid,str
            ));



        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
        });

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    };



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
