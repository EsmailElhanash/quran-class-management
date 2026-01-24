package app.islammedia.halaqatalquran.student.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.database.entities.HomeWork
import java.util.ArrayList

class ViewStudentFragment : Fragment() {

    private fun viewStudentMode(studentId: String) {
        setUpToolbar(R.string.view_student)
        progressBar.setVisibility(View.VISIBLE)
        findViewById<View?>(R.id.saveButton2).visibility = View.GONE
        findViewById<View?>(R.id.add_hw_btn).visibility = View.GONE
        val getStudent = Runnable {
            val s = db.myDAO().getStudent(studentId)
            val homeWorkList: MutableList<HomeWork?> = ArrayList()
            val hwsIDs = db.myDAO().getStudentHomeWorks(s.idName)
            for (stHw in hwsIDs) homeWorkList.add(db.myDAO().getHomeWork(stHw))
            runOnUiThread {
                progressBar.setVisibility(View.GONE)
                stName.setText(s.name)
                stInfo.setText(s.info)
                stName.setEnabled(false) //android.R.color.transparent
                stName.setBackgroundResource(android.R.color.transparent)
                stName.setTextColor(resources.getColor(android.R.color.black))
                stInfo.setEnabled(false)
                stInfo.setBackgroundResource(android.R.color.transparent)
                stInfo.setTextColor(resources.getColor(android.R.color.black))
                for (stHw: HomeWork? in homeWorkList) ViewHomeWorkTemplate(this@StudentActivity, hws_holder, 0, stHw)
                progressBar.setVisibility(View.GONE)
            }
        }
        Thread(getStudent).start()
    }









    private fun setUpToolbar(toolBarTitle: Int) {
        val mToolbar = findViewById<Toolbar?>(R.id.student_activity_toolbar)
        setSupportActionBar(mToolbar)
        if (supportActionBar == null) return
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.setDisplayShowHomeEnabled(true)
        supportActionBar.setTitle(toolBarTitle)
    }

}
