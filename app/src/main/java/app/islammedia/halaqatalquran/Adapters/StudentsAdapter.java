package app.islammedia.halaqatalquran.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import app.islammedia.halaqatalquran.Database.MainDataBase;
import app.islammedia.halaqatalquran.R;
import app.islammedia.halaqatalquran.Database.Student;
import app.islammedia.halaqatalquran.SecondaryActivities.StudentActivity;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.HViewHolder> {

    private List<Student> students;
    private Activity a;
    MainDataBase db;

    public StudentsAdapter(List<Student> students,@NonNull Activity a){
        this.students = students;
        this.a = a;
        db = Room.databaseBuilder(a,MainDataBase.class,"MainDataBase").build();
    }

    @NonNull
    @Override
    public StudentsAdapter.HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.students_adapter,parent,false);
        return new StudentsAdapter.HViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAdapter.HViewHolder holder, int position) {
        TextView stName = holder.stName;
        ImageButton deleteThisStudent = holder.deleteStudent;
        ImageButton editThisStudent = holder.editStudent;
        stName.setText(students.get(position).getName());

        stName.setOnClickListener(view -> {
            Intent i = new Intent(a.getApplicationContext(), StudentActivity.class);
            i.putExtra("studentId",students.get(position).getIdName());
            a.startActivity(i);
        });
        deleteThisStudent.setOnClickListener(view -> {
            new AlertDialog.Builder(a)
                    .setTitle(R.string.confirmDeleteStudent)
                    .setPositiveButton(R.string.yes,(dialogInterface, i) -> {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        Runnable r = () -> {
                            db.myDAO().deleteStudent(students.get(position).getIdName());
                            students.remove(holder.getAdapterPosition());
                            StudentsAdapter.this.notifyItemRemoved(holder.getAdapterPosition());
                            StudentsAdapter.this.notifyDataSetChanged();
                            a.runOnUiThread(() -> {
                                holder.progressBar.setVisibility(View.GONE);
                                view.setVisibility(View.VISIBLE);
                            });

                        };
                        new Thread(r).start();

                    }).setNegativeButton(R.string.cancel,(dialogInterface, i) -> dialogInterface.dismiss()).show();
        });
        editThisStudent.setOnClickListener(view -> {
            Intent studentActivityEditMode = new Intent(a.getApplicationContext(),StudentActivity.class);
            studentActivityEditMode.putExtra("MODE",StudentActivity.EDIT);
            studentActivityEditMode.putExtra("studentId",students.get(position).getIdName());
            a.startActivity(studentActivityEditMode);

        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    static class HViewHolder extends RecyclerView.ViewHolder{
        TextView stName;
        ImageButton editStudent;
        ImageButton deleteStudent;
        ProgressBar progressBar;
        public HViewHolder(@NonNull View v) {
            super(v);
            stName = v.findViewById(R.id.studentNameTV);
            deleteStudent = v.findViewById(R.id.deleteThisStudent);
            progressBar = v.findViewById(R.id.studentDeleting);
            editStudent = v.findViewById(R.id.editThisStudent);

        }
    }
}
