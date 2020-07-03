package app.islammedia.halaqatalquran.HelpingClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.islammedia.halaqatalquran.Database.HomeWork;
import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.SecondaryActivities.StudentActivity;

public class HomeworkClass implements DatePickerDialog.OnDateSetListener{
    private final StudentActivity studentActivity;
    private TextView deadline_date_view;
    public Spinner sura1;
    public Spinner ayah1;
    public Spinner sura2;
    public Spinner ayah2;
    Button add_deadline;

    public Calendar getCurPickedDate() {
        return curPickedDate;
    }

    public Spinner getSura1() {
        return sura1;
    }

    public Spinner getAyah1() {
        return ayah1;
    }

    public Spinner getSura2() {
        return sura2;
    }

    public Spinner getAyah2() {
        return ayah2;
    }

    public EditText getHw_title() {
        return hw_title;
    }

    public EditText hw_title;
    String[] sowarNames;
    int[] sowar_verses_num;
    public Calendar curPickedDate = null;
    HomeWork hw;

    long week = 604800000;
    long day = 86400000;

    //Date curPickedDate = now.getTime();

//        JSONArray quran_json = null;

    public HomeWork getHw() {
        return hw;
    }

    public void setHw(HomeWork hw) {
        this.hw = hw;
    }

    public HomeworkClass(StudentActivity studentActivity
            , ViewGroup holder, int mode, @Nullable HomeWork hw){
        this.hw = hw;
        this.studentActivity = studentActivity;
        sowar_verses_num = studentActivity
                .getResources().getIntArray(R.array.sowar_verses_num);
        sowarNames = studentActivity
                .getResources().getStringArray(R.array.sura_names);

        switch (mode){
            case 1://Add
                addStudentMode(holder);
                break;

            case 2://Edit

                editStudentMode(holder,hw);
                break;

            default:
                if (hw == null ) return;

                viewHomeworkMode(holder , hw);
                break;
        }



        //System.out.println(curPickedDate.get(Calendar.DAY_OF_MONTH) + " " + curPickedDate.get(Calendar.MONTH));
    }

    private void editStudentMode(ViewGroup holder, HomeWork hw) {
        View hwTemplate = LayoutInflater.from(studentActivity
        ).inflate(R.layout.hw_template, holder, false);
        holder.addView(hwTemplate);

        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view);
        Calendar c = hw.getDueDate();
        if (c!=null)
            updateDateTV(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));


        add_deadline = hwTemplate.findViewById(R.id.add_deadline);

        hw_title = hwTemplate.findViewById(R.id.hw_title);
        hw_title.setText(hw.getTitle());

        sura1 = hwTemplate.findViewById(R.id.sura_start_spinner);
        sura1.setSelection(Arrays.asList(sowarNames).indexOf(hw.getSura1()));


        ayah1 = hwTemplate.findViewById(R.id.ayah_start_spinner);
        Integer[] ayat = new Integer[sowar_verses_num[hw.getAyah1()]];
        for (int v = 0; v < ayat.length; v++) {
            ayat[v] = 1 + v;
        }
        ayah1.setAdapter(new ArrayAdapter<>(studentActivity
                ,
                android.R.layout.simple_spinner_item,
                ayat));
        ayah1.setSelection(hw.getAyah1()-1);


        sura2 = hwTemplate.findViewById(R.id.sura_end_spinner);
        sura2.setSelection(Arrays.asList(sowarNames).indexOf(hw.getSura2()));


        ayah2 = hwTemplate.findViewById(R.id.ayah_end_spinner);
        Integer[] ayat2 = new Integer[sowar_verses_num[hw.getAyah1()]];
        for (int v = 0; v < ayat2.length; v++) {
            ayat2[v] = 1 + v;
        }
        ayah2.setAdapter(new ArrayAdapter<>(studentActivity
                ,
                android.R.layout.simple_spinner_item,
                ayat2));
        ayah2.setSelection(hw.getAyah1()-1);

        //Listeners
        sura1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Integer[] ayat = new Integer[sowar_verses_num[i]];
                for (int v = 0; v < sowar_verses_num[i]; v++) {
                    ayat[v] = 1 + v;
                }
                ayah1.setAdapter(new ArrayAdapter<>(studentActivity
                        ,
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
                ayah2.setAdapter(new ArrayAdapter<>(studentActivity
                        ,
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
        init_date_picker();

        delHW.setOnClickListener(view -> new AlertDialog.Builder(studentActivity
        ).setTitle("هل أنت متأكد؟")
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    holder.removeView(hwTemplate);
                    studentActivity.hws.remove(HomeworkClass.this);
                })
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
            deadline_date_view.setText(studentActivity
                    .getResources().getText(R.string.date_not_set));
        });
    }

    void addStudentMode(ViewGroup holder) {
        View hwTemplate = LayoutInflater.from(studentActivity
        ).inflate(R.layout.hw_template, holder, false);
        holder.addView(hwTemplate);
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view);
        add_deadline = hwTemplate.findViewById(R.id.add_deadline);

        hw_title = hwTemplate.findViewById(R.id.hw_title);

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
                ayah1.setAdapter(new ArrayAdapter<>(studentActivity
                        ,
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
                ayah2.setAdapter(new ArrayAdapter<>(studentActivity
                        ,
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
        init_date_picker();

        delHW.setOnClickListener(view -> new AlertDialog.Builder(studentActivity
        ).setTitle("هل أنت متأكد؟")
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    holder.removeView(hwTemplate);
                    studentActivity.hws.remove(HomeworkClass.this);
                })
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
            deadline_date_view.setText(studentActivity
                    .getResources().getText(R.string.date_not_set));
        });
    }

    void viewHomeworkMode(ViewGroup holder, HomeWork hw){
        View hwTemplate = LayoutInflater.from(studentActivity
        ).inflate(R.layout.hw_view_template, holder, false);
        holder.addView(hwTemplate);
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view);
        TextView hw_description = hwTemplate.findViewById(R.id.hw_description);
        TextView hw_title = hwTemplate.findViewById(R.id.hw_title);
        hw_title.setText(hw.getTitle());
        String hw_dec = "سورة " + hw.getSura1() + " آية " + hw.getAyah1() + " إلى " + "سورة " + hw.getSura2() + " آية " + hw.getAyah2();
        hw_description.setText(hw_dec);

        if (hw.getDueDate() !=null)
            updateDateTV(hw.getDueDate().get(Calendar.YEAR),hw.getDueDate().get(Calendar.MONTH),hw.getDueDate().get(Calendar.DAY_OF_MONTH));

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

        add_deadline.setOnClickListener(view -> dpd.show(studentActivity.getSupportFragmentManager(), "Datepickerdialog"));


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
        String current_picked_date = sdf.format(new Date(c.getTimeInMillis())) + "  " + year + "-" + ++monthOfYear + "-" + dayOfMonth;
        deadline_date_view.setText(current_picked_date);
    }
}
