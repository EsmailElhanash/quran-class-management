package app.islammedia.halaqatalquran.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.student.fragments.AddStudentFragment
import app.islammedia.halaqatalquran.student.fragments.EditStudentFragment
import app.islammedia.halaqatalquran.student.fragments.ViewStudentFragment
import app.islammedia.halaqatalquran.utils.Constants

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val b = intent.extras
        when (b?.getInt(Constants.MODE)) {
            Constants.ADD ->
                supportFragmentManager.beginTransaction().add(R.id.fragment_holder,AddStudentFragment()).commit()
            Constants.EDIT -> {
                val studentId = b.getString(Constants.STUDENT_ID)
                val editStudentFragment = EditStudentFragment()
                val b2 = Bundle()
                b2.putString(Constants.STUDENT_ID,studentId)
                editStudentFragment.arguments = b2
                supportFragmentManager.beginTransaction().add(R.id.fragment_holder,editStudentFragment).commit()
            }
            Constants.VIEW -> {
                val studentId = b.getString(Constants.STUDENT_ID)
                val viewStudentFragment = ViewStudentFragment()
                val b2 = Bundle()
                b2.putString(Constants.STUDENT_ID,studentId)
                viewStudentFragment.arguments = b2
                supportFragmentManager.beginTransaction().add(R.id.fragment_holder,viewStudentFragment).commit()
            }
        }
    }

    fun setUpToolbar(title : String) {
        val mToolbar = findViewById<Toolbar?>(R.id.student_activity_toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = title
    }


}