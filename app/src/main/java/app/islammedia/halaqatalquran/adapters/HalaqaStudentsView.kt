package app.islammedia.halaqatalquran.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.islammedia.halaqatalquran.adapters.HalaqaStudentsView.MyViewHolder
import app.islammedia.halaqatalquran.R
import java.util.*

class HalaqaStudentsView(private val studentsNames: ArrayList<String?>) : RecyclerView.Adapter<MyViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_halaqa_students, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.stName.text = studentsNames[position]
    }

    override fun getItemCount(): Int {
        return studentsNames.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var stName: TextView = itemView.findViewById(R.id.halaqaStudentNameView)

    }
}