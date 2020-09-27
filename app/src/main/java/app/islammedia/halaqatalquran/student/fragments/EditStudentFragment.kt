package app.islammedia.halaqatalquran.student.fragments

import android.content.DialogInterface
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.database.entities.StudentsAndHomeWorksCrossRef
import app.islammedia.halaqatalquran.student.homework.StudentHomeworkViewTemplate
import java.util.ArrayList
import java.util.HashMap

class EditStudentFragment : Fragment(){

    private fun editStudentMode(studentId: String) {
        progressBar.setVisibility(View.VISIBLE)
        setUpToolbar(R.string.edit_student_title)
        val homeWorksNewClassification: MutableMap<StudentHomeworkViewTemplate?, Boolean?> = HashMap()

        //
        val getStudentAndHomeWorks = Runnable {


            // we have got the student -> s
            val s = db.myDAO().getStudent(studentId)
            val thisStudentHomeWorksIDs = db.myDAO().getStudentHomeWorks(s.idName)

            // we have got the student's HomeWorks -> hisHomeWorks
            val hisHomeWorks: MutableList<HomeWork?> = ArrayList()
            for (str in thisStudentHomeWorksIDs) hisHomeWorks.add(db.myDAO().getHomeWork(str))
            runOnUiThread {
                progressBar.setVisibility(View.GONE)
                stName.setText(s.name)
                stInfo.setText(s.info)
                for (h: HomeWork? in hisHomeWorks) {
                    val hw: StudentHomeworkViewTemplate = StudentHomeworkViewTemplate(this@StudentActivity,
                            hws_holder, EDIT, h)
                    hws.add(hw)
                    homeWorksNewClassification[hw] = false
                }
            }
        }
        Thread(getStudentAndHomeWorks).start()
        add_hw_btn = findViewById(R.id.add_hw_btn)
        add_hw_btn.setOnClickListener(View.OnClickListener { view: View? ->
            val hw = StudentHomeworkViewTemplate(this, hws_holder, ADD, null)
            homeWorksNewClassification[hw] = true
            hws.add(hw)
        }
        )
        val saveB = findViewById<Button?>(R.id.saveButton2)
        val updateStudentAndHomeWorks = Runnable {
            db.myDAO().updateStudent(studentId, stName.getText().toString(), stInfo.getText().toString())
            for (hw in hws) {
                val isNew = homeWorksNewClassification[hw]
                if (isNew != null && isNew) {
                    val s1 = hw.sura1.selectedItem as String
                    val s2 = hw.sura2.selectedItem as String
                    val a1 = hw.ayah1.selectedItem as Int
                    val a2 = hw.ayah2.selectedItem as Int
                    val hwID = s1 + a1 + "_" + s2 + a2 + "_" + System.currentTimeMillis()
                    db.myDAO().insertHomework(HomeWork(hw.hw_title.text.toString(),
                            hw.curPickedDate,
                            s1,
                            s2,
                            a1,
                            a2,
                            hwID))
                    db.myDAO().insertStudentsAndHomeWorksCrossRef(StudentsAndHomeWorksCrossRef(studentId, hwID))
                } else {
                    /*hw.getHw().getTitle(),hw.getHw().getDueDate(),
                            hw.getHw().getSura1(),hw.getHw().getSura2(),hw.getHw().getAyah1(),hw.getHw().getAyah2()*/
                    db.myDAO().updateHomeWork(hw.getHw().idTitle, hw.getHw_title().text.toString(),
                            hw.getCurPickedDate(), hw.getSura1().selectedItem.toString(), hw.getSura2().selectedItem.toString(),
                            hw.getAyah1().selectedItem as Int,
                            hw.getAyah2().selectedItem as Int)
                }
            }
            for (hw2Del in hwsToDeleteIDs) db.myDAO().deleteStudentHomeworkCrossRef(studentId, hw2Del)
            runOnUiThread { progressBar.setVisibility(View.GONE) }

            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish()
        }
        saveB.setOnClickListener { v: View? ->
            if ((stName.getText().toString() == "")) {
                AlertDialog.Builder(this@StudentActivity)
                        .setTitle(R.string.stNamePlease)
                        .setNeutralButton(android.R.string.ok) { dialog: DialogInterface?, which: Int -> dialog.dismiss() }.create().show()
            } else {
                progressBar.setVisibility(View.VISIBLE)
                val saveStudent: Thread = Thread(updateStudentAndHomeWorks)
                saveStudent.start()
            }
        }
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