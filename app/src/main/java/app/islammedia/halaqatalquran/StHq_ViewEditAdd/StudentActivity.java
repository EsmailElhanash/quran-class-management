package app.islammedia.halaqatalquran.StHq_ViewEditAdd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.islammedia.halaqatalquran.MainActivity;
import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.database_room.MainDataBase;
import app.islammedia.halaqatalquran.database_room.Student;

public class StudentActivity extends AppCompatActivity  {

//    TextView birthDate;
    int curYear;
    int curMonth;
    int curDay;
    EditText stName;
    EditText stInfo;
    ProgressBar insertingProgressBar;
    Button add_hw_btn;
    ViewGroup hws_holder;
    List<String> AlSowar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        AlSowar = Arrays.asList(getResources().getStringArray(R.array.sura_names));


        Bundle b = getIntent().getExtras();
        int mode = 0;
        if (b!=null)
            mode = b.getInt("MODE", 0);






        switch (mode){
            case 1://Add
                //System.out.println("mode=1_add");
                add();
                break;

            case 2://Edit
                //System.out.println("mode=2_edit");
                edit();
                break;

            default:
                //System.out.println("mode=0_view");
                view();
                break;
        }

//        clearPickedDate.setOnClickListener(view -> {
//
//        });
    }

    static class Homework implements DatePickerDialog.OnDateSetListener{
        private TextView deadline_date_view;
        Activity activity;
        Spinner sura1;
        Spinner ayah1;
        Spinner sura2;
        Spinner ayah2;
        Button add_deadline;
        String[] sowarNames;
        int[] sowar_verses_num;
        Calendar curPickedDate = null;

        long week = 604800000;
        long day = 86400000;

        //Date curPickedDate = now.getTime();

//        JSONArray quran_json = null;

        Homework(Activity activity,ViewGroup holder){
            this.activity = activity;
            sowar_verses_num = activity.getResources().getIntArray(R.array.sowar_verses_num);
            sowarNames = activity.getResources().getStringArray(R.array.sura_names);
            setupViews(holder);
            init_date_picker();

            //System.out.println(curPickedDate.get(Calendar.DAY_OF_MONTH) + " " + curPickedDate.get(Calendar.MONTH));
        }

        void setupViews(ViewGroup holder) {
            View hwTemplate = LayoutInflater.from(activity).inflate(R.layout.hw_template, holder, false);
            holder.addView(hwTemplate);
            deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view);
            add_deadline = hwTemplate.findViewById(R.id.add_deadline);

            sura1 = hwTemplate.findViewById(R.id.sura_start_spinner);
            ayah1 = hwTemplate.findViewById(R.id.ayah_start_spinner);
            sura2 = hwTemplate.findViewById(R.id.sura_end_spinner);
            ayah2 = hwTemplate.findViewById(R.id.ayah_end_spinner);

            //Listeners
            sura1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Integer[] ayat = new Integer[sowar_verses_num[i]];
                    for (int v = 0; v < sowar_verses_num[i]; v++) {
                        ayat[v] = 1 + v;
                    }
                    ayah1.setAdapter(new ArrayAdapter<>(activity,
                            android.R.layout.simple_spinner_item,
                            ayat));




                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            sura2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Integer[] ayat2 = new Integer[sowar_verses_num[i]];
                    for (int v = 0; v < sowar_verses_num[i]; v++) {
                        ayat2[v] = 1 + v;
                    }
                    ayah2.setAdapter(new ArrayAdapter<>(activity,
                            android.R.layout.simple_spinner_item,
                            ayat2));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Buttons
            Button delHW = hwTemplate.findViewById(R.id.delete_hw);
            Button moreDay = hwTemplate.findViewById(R.id.moreDay);
            Button moreWeek = hwTemplate.findViewById(R.id.moreWeek);
            Button clearDate = hwTemplate.findViewById(R.id.date_delete);

            delHW.setOnClickListener(view -> new AlertDialog.Builder(activity).setTitle("هل أنت متأكد؟")
                    .setPositiveButton(R.string.yes, (dialogInterface, i) -> holder.removeView(hwTemplate))
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss())
                    .create().show());

            moreDay.setOnClickListener(view -> {
                if (curPickedDate == null) curPickedDate = Calendar.getInstance();
                long newDate = curPickedDate.getTimeInMillis() + day;
                curPickedDate.setTimeInMillis(newDate);
                updateDateTV(curPickedDate.get(Calendar.YEAR) , curPickedDate.get(Calendar.MONTH) , curPickedDate.get(Calendar.DAY_OF_MONTH) );
            });

            moreWeek.setOnClickListener(view -> {
                if (curPickedDate == null) curPickedDate = Calendar.getInstance();
                long newDate = curPickedDate.getTimeInMillis() + week;
                curPickedDate.setTimeInMillis(newDate);
                updateDateTV(curPickedDate.get(Calendar.YEAR) , curPickedDate.get(Calendar.MONTH) , curPickedDate.get(Calendar.DAY_OF_MONTH) );
            });

            clearDate.setOnClickListener(view -> {
                curPickedDate = null;
                deadline_date_view.setText(activity.getResources().getText(R.string.date_not_set));
            });
        }

        void init_date_picker(){

            //homework deadline date


            //DatePickerDialog init
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );

            dpd.setLocale(new Locale("ar"));


            //finding views//

            add_deadline.setOnClickListener(view -> dpd.show(((AppCompatActivity)activity).getSupportFragmentManager(), "Datepickerdialog"));


        }

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            updateDateTV(year,monthOfYear,dayOfMonth);
        }

        private void updateDateTV(int year, int monthOfYear, int dayOfMonth) {

            Calendar c = Calendar.getInstance();
            c.set(year,monthOfYear,dayOfMonth);
            curPickedDate = c;
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", new Locale("ar"));
            String picked_date = sdf.format(new Date(c.getTimeInMillis())) + "  " + year + "-" + ++monthOfYear + "-" + dayOfMonth;
            deadline_date_view.setText(picked_date);
        }
    }



    private void view (){

    }

    private void add(){

        setUpToolbar();
        add_hw_btn = findViewById(R.id.add_hw_btn);
        hws_holder = findViewById(R.id.added_hws_holder);
        add_hw_btn.setOnClickListener(view ->
                {
                    new Homework(this,hws_holder);

                }
        );

        stName = findViewById(R.id.studentName);
        stInfo = findViewById(R.id.studentInfo);

        /*birthDate = findViewById(R.id.birthDate);
        final DatePicker birthDatePicker = findViewById(R.id.birthDatePicker);
        final ImageButton show_hide_Picker = findViewById(R.id.showPicker);
//        final ImageButton clearPickedDate = findViewById(R.id.clearPickedDate);
        final ImageButton savePickedDate = findViewById(R.id.savePickedDate);*/
        insertingProgressBar = findViewById(R.id.st_add_progress_bar);


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
                insertingProgressBar.setVisibility(View.VISIBLE);
                new Thread(addStudent).start();
            }
        });
    }

    private void edit(){

    }

    private void setUpToolbar() {


        Toolbar mToolbar = findViewById(R.id.addStToolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar()==null) return;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



    }

    Runnable addStudent = () -> {
        final MainDataBase db = Room.databaseBuilder(this,MainDataBase.class,"MainDataBase").build();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, curYear);
        cal.set(Calendar.MONTH, curMonth);
        cal.set(Calendar.DAY_OF_MONTH, curDay);

        
        db.myDAO().insertStudent(new Student(stName.getText().toString(),
                stInfo.getText().toString()));

         
        runOnUiThread(() -> {
            insertingProgressBar.setVisibility(View.GONE);
        });

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    };

    /*public void dateUpdate(int year, int month, int day){
        curDay = day;
        curMonth = month;
        curYear = year;
        String datePicked = year + "-" + month + "-" + day;
        birthDate.setText(datePicked);
    }*/
}
