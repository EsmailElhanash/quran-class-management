package app.islammedia.halaqatalquran.helping_classes

import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.islammedia.halaqatalquran.adapters.CheckStudentsAdapter
import app.islammedia.halaqatalquran.adapters.HalaqaStudentsView
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.halaqa.HalaqaActivity
import java.util.*

class HalaqaStudentsHelper(holder: FrameLayout?, var studentBooleanMap: MutableMap<Student?, Boolean?>?, mode: Int, var activity: HalaqaActivity?) {
    var allStudents: MutableList<Student?>? = null
    fun getAdapter(): CheckStudentsAdapter? {
        return adapter
    }

    var studentsNamesAdapter: HalaqaStudentsView? = null
    var adapter: CheckStudentsAdapter? = null
    private fun viewMode(holder: FrameLayout?, studentBooleanMap: MutableMap<Student?, Boolean?>?) {
        val studentsContainer = RecyclerView(holder.getContext())
        studentsContainer.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        val studentsNames = ArrayList<String?>()
        val studentsObjects = ArrayList(studentBooleanMap.keys)
        for (i in 0 until studentBooleanMap.size) {
            if (studentBooleanMap.get(studentsObjects[i])) {
                studentsNames.add(studentsObjects[i].getName())
            }
        }
        studentsContainer.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(holder.getContext())
        studentsContainer.layoutManager = layoutManager
        studentsNamesAdapter = HalaqaStudentsView(studentsNames)
        studentsContainer.adapter = studentsNamesAdapter
        holder.addView(studentsContainer)
    }

    private fun addMode(holder: FrameLayout?, studentBooleanMap: MutableMap<Student?, Boolean?>?) {
        val main = LayoutInflater.from(activity).inflate(R.layout.template_students_holder, holder, false)
        holder.addView(main)
        val studentsContainer: RecyclerView = main.findViewById(R.id.studentsContainer)
        studentsContainer.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        studentsContainer.layoutManager = layoutManager
        adapter = CheckStudentsAdapter(studentBooleanMap, 1)
        studentsContainer.adapter = adapter
    }

    private fun editMode(holder: FrameLayout?, studentBooleanMap: MutableMap<Student?, Boolean?>?) {
        val main = LayoutInflater.from(activity).inflate(R.layout.template_students_holder, holder, false)
        holder.addView(main)
        val studentsContainer: RecyclerView = main.findViewById(R.id.studentsContainer)
        studentsContainer.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        studentsContainer.layoutManager = layoutManager
        adapter = CheckStudentsAdapter(studentBooleanMap, 2)
        studentsContainer.adapter = adapter
    }

    init {
        when (mode) {
            1 -> addMode(holder, studentBooleanMap)
            2 -> editMode(holder, studentBooleanMap)
            else -> viewMode(holder, studentBooleanMap)
        }
    }
}