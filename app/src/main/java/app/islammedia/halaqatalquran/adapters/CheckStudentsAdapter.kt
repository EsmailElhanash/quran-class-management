package app.islammedia.halaqatalquran.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.R
import java.util.*

class CheckStudentsAdapter(
        private val studentsIsChecked: MutableMap<Student, Boolean>,
        var mode: Int) : RecyclerView.Adapter<CheckStudentsAdapter.HViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_students_select, parent, false)
        return HViewHolder(v)
    }

    override fun onBindViewHolder(holder: HViewHolder, position: Int) {
        if (mode != 0) {
            val sts = ArrayList(studentsIsChecked.keys)
            if (studentsIsChecked[sts[position]] === java.lang.Boolean.TRUE) holder.st.isChecked = true
            holder.st.visibility = View.VISIBLE
            holder.st.text = sts[position].getName()
            holder.st.setOnCheckedChangeListener { _: CompoundButton?, b: Boolean -> studentsIsChecked[sts[position]] = b }
        } else {
            holder.st.isEnabled = false
            val sts = ArrayList(studentsIsChecked.keys)
            if (studentsIsChecked[sts[position]] === java.lang.Boolean.TRUE) {
                holder.st.isChecked = true
                holder.st.visibility = View.VISIBLE
                holder.st.text = sts[position].getName()
            }
        }
    }

    override fun getItemCount(): Int {
        return studentsIsChecked.size
    }

    class HViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var st: CheckBox = v.findViewById(R.id.checkStudent)
    }

}