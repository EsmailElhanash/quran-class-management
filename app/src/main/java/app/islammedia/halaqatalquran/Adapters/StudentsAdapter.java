package app.islammedia.halaqatalquran.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.database_room.Student;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.HViewHolder> {

    private List<Student> students;

    public StudentsAdapter(List<Student> students){
        this.students = students;
    }

    @NonNull
    @Override
    public StudentsAdapter.HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_adapter,parent,false);
        return new StudentsAdapter.HViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAdapter.HViewHolder holder, int position) {
        holder.stName.setText(students.get(position).name);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    static class HViewHolder extends RecyclerView.ViewHolder{
        TextView stName;
        public HViewHolder(@NonNull View v) {
            super(v);
            stName = v.findViewById(R.id.studentNameTV);
        }
    }
}
