package app.islammedia.halaqatalquran.student.ui

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import app.islammedia.halaqatalquran.MainActivity
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.database.MainDataBase
import app.islammedia.halaqatalquran.database.MainDataBaseManager
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.database.entities.StudentsAndHomeWorksCrossRef
import app.islammedia.halaqatalquran.utils.Constants
import java.util.*

class EditStudentFragment : Fragment() , View.OnClickListener{
    private lateinit var c : Context
    private lateinit var db : MainDataBase


    private lateinit var studentName: EditText
    private lateinit var studentInfo: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var addHomeWorkButton: Button
    private lateinit var homeWorksViewHolder: ViewGroup
    private lateinit var homeWorks: List<HomeWork>



    override fun onAttach(context: Context) {
        super.onAttach(context)
        c = context
        db = MainDataBaseManager.getMainDataBaseInstance(c)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_add_student,container,false)
        progressBar = view.findViewById(R.id.student_activity_progress_bar)
        studentName = view.findViewById(R.id.studentName)
        studentInfo = view.findViewById(R.id.studentInfo)
        homeWorksViewHolder = view.findViewById(R.id.added_hws_holder)
        addHomeWorkButton = view.findViewById(R.id.add_hw_btn)

        setUpToolbar()


        return view
    }

    private var addStudentToDataBase: Runnable = Runnable {
        val dataBaseHomeWorks: MutableList<HomeWork> = ArrayList()
        val studentNameString: String = studentName.text.toString()
        val studentID = studentNameString + "_" + System.currentTimeMillis()
        val student = Student(studentNameString,
                studentInfo.text.toString(),
                studentID)

        val hwsIDs: MutableList<String?> = ArrayList()
        for (hw in addedHomeWorks) {
            val sura1 = hw.sura1.selectedItem as String
            val sura2 = hw.sura2.selectedItem as String
            val ayah1 = hw.ayah1.selectedItem as Int
            val ayah2 = hw.ayah2.selectedItem as Int
            val hwID = sura1 + ayah1 + "_" + sura2 + ayah2 + "_" + System.currentTimeMillis()
            hwsIDs.add(hwID)
            dbHws.add(HomeWork(hw.hw_title.text.toString(),
                    hw.curPickedDate,
                    sura1,
                    sura2,
                    ayah1,
                    ayah2,
                    hwID))
        }
        db.myDAO().insertStudent(s)
        db.myDAO().insertHomework(*dbHws.toTypedArray())
        for (str in hwsIDs) db.myDAO().insertStudentsAndHomeWorksCrossRef(StudentsAndHomeWorksCrossRef(
                studentID, str
        ))
        runOnUiThread { progressBar.visibility = View.GONE }
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun addStudentMode() {
        addHomeWorkButton = findViewById(R.id.add_hw_btn)
        addHomeWorkButton.setOnClickListener(View.OnClickListener { view: View? -> addedHomeWorks.add(ViewHomeWorkTemplate(this, hwsHolder, 1, null)) }
        )
        val saveB = findViewById<Button?>(R.id.saveButton2)
        saveB.setOnClickListener { v: View? ->
            if ((stName.getText().toString() == "")) {
                AlertDialog.Builder(this@StudentActivity)
                        .setTitle(R.string.stNamePlease)
                        .setNeutralButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).create().show()
            } else {
                progressBar.visibility = View.VISIBLE
                val saveStudent: Thread = Thread(addStudent)
                val stop: BooleanArray = booleanArrayOf(false)
                for (hw: ViewHomeWorkTemplate? in addedHomeWorks) if ((hw.hw_title.text.toString() == "")) {
                    AlertDialog.Builder(this).setTitle("يوجد واجبات بدون عنوان ، الإستمرار؟")
                            .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface?, i: Int -> saveStudent.start() }
                            .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface?, i: Int -> stop.get(0) = true }
                            .create().show()
                    if (stop.get(0)) return@setOnClickListener
                }
                if (!stop.get(0)) Thread(addStudent).start()
            }
        }
    }

    private fill









    private fun setUpToolbar() {
        if (c is StudentActivity){
            val title : String = c.getString(R.string.add_student)
            (c as StudentActivity).setUpToolbar(title)
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.add_hw_btn -> {

            }
        }
    }

}