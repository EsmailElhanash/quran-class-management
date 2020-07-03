package app.islammedia.halaqatalquran.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import app.islammedia.halaqatalquran.Adapters.HalaqatAdapter;
import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.Database.Halaqa;
import app.islammedia.halaqatalquran.Database.MainDataBase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HalaqatViewFragment extends Fragment {
    MainDataBase db;
    ProgressBar fhv_progressbar;
    RecyclerView dbView;

    public HalaqatViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_halaqat_view, container, false);
        fhv_progressbar = v.findViewById(R.id.fhv_progressbar);
        fhv_progressbar.setVisibility(View.VISIBLE);
        Thread thread = new Thread(connectDataBase);
        thread.start();

        dbView = v.findViewById(R.id.halaqatViewList);

        return v;
    }

    Runnable connectDataBase = () -> {
        if (getActivity()==null) return;
        db = Room.databaseBuilder(getActivity(), MainDataBase.class, "MainDataBase").build();
        List<Halaqa> halaqat = db.myDAO().getHalaqat();
        HalaqatAdapter hAdapter = new HalaqatAdapter(halaqat);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dbView.setAdapter(hAdapter);
                fhv_progressbar.setVisibility(View.GONE);
            }
        });
    };


}
