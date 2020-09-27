package app.islammedia.halaqatalquran.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.database.MainDataBase
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.student.StudentActivity

class StudentsAdapter(private val students: MutableList<Student?>?, private val a: Activity) : RecyclerView.Adapter<StudentsAdapter.HViewHolder>() {
    var db: MainDataBase = Room.databaseBuilder(a, MainDataBase::class.java, "MainDataBase").build()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_students_view, parent, false)
        return HViewHolder(v)
    }

    override fun onBindViewHolder(holder: HViewHolder, position: Int) {
        val stName = holder.stName
        val deleteThisStudent = holder.deleteStudent
        val editThisStudent = holder.editStudent
        stName.text = students[position].getName()
        stName.setOnClickListener {
            val i = Intent(a.applicationContext, StudentActivity::class.java)
            i.putExtra("studentId", students[position].getStudentId())
            a.startActivity(i)
        }
        deleteThisStudent.setOnClickListener { view: View ->
            AlertDialog.Builder(a)
                    .setTitle(R.string.confirmDeleteStudent)
                    .setPositiveButton(R.string.yes) { _: DialogInterface, _: Int ->
                        holder.progressBar.visibility = View.VISIBLE
                        view.visibility = View.GONE
                        val r = Runnable {
                            db.myDAO().deleteStudent(students[position].getStudentId())
                            students.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                            notifyDataSetChanged()
                            a.runOnUiThread {
                                holder.progressBar.visibility = View.GONE
                                view.visibility = View.VISIBLE
                            }
                        }
                        Thread(r).start()
                    }.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }.show()
        }
        editThisStudent.setOnClickListener {
            val studentActivityEditMode = Intent(a.applicationContext, StudentActivity::class.java)
            studentActivityEditMode.putExtra("MODE", StudentActivity.EDIT)
            studentActivityEditMode.putExtra("studentId", students[position].getStudentId())
            a.startActivity(studentActivityEditMode)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    class HViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var stName: TextView = v.findViewById(R.id.studentNameTV)
        var editStudent: ImageButton = v.findViewById(R.id.editThisStudent)
        var deleteStudent: ImageButton = v.findViewById(R.id.deleteThisStudent)
        var progressBar: ProgressBar = v.findViewById(R.id.studentDeleting)

    }

}