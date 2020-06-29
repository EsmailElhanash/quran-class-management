package app.islammedia.halaqatalquran.MainVP2Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.islammedia.halaqatalquran.Adapters.StudentsAdapter;
import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.database_room.MainDataBase;
import app.islammedia.halaqatalquran.database_room.Student;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentsViewFragment extends Fragment {

    public StudentsViewFragment() {
        // Required empty public constructor
    }

    RecyclerView stRv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_students_view, container, false);
        stRv = v.findViewById(R.id.studentsViewList);
        stRv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        stRv.setLayoutManager(layoutManager);
        new Thread(getStList).start();
        return v;
    }

    Runnable getStList = () -> {
        if (getActivity()==null) return;
        MainDataBase db = Room.databaseBuilder(getActivity(),MainDataBase.class,"MainDataBase").build();
        List<Student> sts = db.myDAO().getAllStudents();
        StudentsAdapter stAdapter = new StudentsAdapter(sts);
        getActivity().runOnUiThread(()->
            stRv.setAdapter(stAdapter)
        );
    };
}
