package app.islammedia.halaqatalquran.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.islammedia.halaqatalquran.adapters.StudentsAdapter
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.database.MainDataBaseManager

class StudentsViewFragment : Fragment() {
    private lateinit var allStudentsView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_students_view, container, false)
        allStudentsView = v.findViewById(R.id.studentsViewList)
        allStudentsView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(v.context)
        allStudentsView.layoutManager = layoutManager
        Thread(getStudentsList).start()
        return v
    }

    private var getStudentsList: Runnable = Runnable {
        if (activity == null) return@Runnable

        val db = MainDataBaseManager.getMainDataBaseInstance(requireActivity())


        val students = db.myDAO().getAllStudents()
        val studentsAdapter = StudentsAdapter(students, requireActivity())
        requireActivity().runOnUiThread {
            allStudentsView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            allStudentsView.adapter = studentsAdapter
        }
    }
}