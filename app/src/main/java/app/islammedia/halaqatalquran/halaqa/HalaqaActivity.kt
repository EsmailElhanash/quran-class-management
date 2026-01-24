package app.islammedia.halaqatalquran.halaqa

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import app.islammedia.halaqatalquran.database.entities.*
import app.islammedia.halaqatalquran.database.helpers.HalaqaDate
import app.islammedia.halaqatalquran.database.MainDataBase
import app.islammedia.halaqatalquran.helping_classes.HalaqaDatesHelper
import app.islammedia.halaqatalquran.helping_classes.HalaqaHomeworkClass
import app.islammedia.halaqatalquran.helping_classes.HalaqaStudentsHelper
import app.islammedia.halaqatalquran.MainActivity
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.student.ui.StudentActivity
import java.util.*

class HalaqaActivity : AppCompatActivity() {
    var add_halaqa_date: Button? = null
    var add_halaqa_homework: Button? = null
    var add_halaqa_students: Button? = null
    var halaqaInfo: EditText? = null
    var halaqaNameEditText: EditText? = null
    var halaqa_dates_holder: LinearLayout? = null
    var halaqa_homeWorks_holder: LinearLayout? = null
    var db: MainDataBase? = null
    var hws: MutableList<HalaqaHomeworkClass?>? = ArrayList()
    var hwsToDelete: MutableList<String?>? = ArrayList()
    var halaqaDates: MutableList<HalaqaDatesHelper?>? = ArrayList()
    var halaqaStudentsHelper: HalaqaStudentsHelper? = null
    var saveButton: Button? = null
    var halaqaActionProgressBar: ProgressBar? = null
    var studentsHolder: FrameLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaqa)
        studentsHolder = findViewById(R.id.studentsHolder)
        db = Room.databaseBuilder(this, MainDataBase::class.java, "MainDataBase").build()
        halaqaNameEditText = findViewById(R.id.halaqaNameEditText)
        halaqaActionProgressBar = findViewById(R.id.halaqaActionProgressBar)
        add_halaqa_students = findViewById(R.id.add_halaqa_students)
        halaqaInfo = findViewById(R.id.halaqaInfo)
        add_halaqa_date = findViewById(R.id.add_halaqa_date)
        add_halaqa_homework = findViewById(R.id.add_halaqa_homework)
        saveButton = findViewById(R.id.saveButton)
        halaqa_dates_holder = findViewById(R.id.halaqa_dates_holder)
        halaqa_homeWorks_holder = findViewById(R.id.halaqa_home_works_holder)
        add_halaqa_date.setOnClickListener(View.OnClickListener { view: View? -> halaqaDates.add(HalaqaDatesHelper(halaqa_dates_holder, this, 1, null)) })
        add_halaqa_homework.setOnClickListener(View.OnClickListener { view: View? -> hws.add(HalaqaHomeworkClass(this, halaqa_homeWorks_holder, 1, null)) })
        val b = intent.extras
        var mode = 0
        var halaqaID: String? = null
        if (b != null) {
            mode = b.getInt("MODE", 0)
            halaqaID = b.getString("halaqaID", null)
        }
        when (mode) {
            1 ->                 //System.out.println("mode=1_add");
                addHalaqaMode()
            2 -> {
                if (halaqaID == null) break
                editHalaqaMode(halaqaID)
            }
            else -> {
                //System.out.println("mode=0_view");
                if (halaqaID == null) break
                viewHalaqaMode(halaqaID)
            }
        }
    }

    private fun addHalaqaMode() {
        setUpToolbar(R.string.add_halaqa)
        saveButton.setOnClickListener(View.OnClickListener { view: View? ->
            if (halaqaNameEditText.getText().toString() == "") {
                AlertDialog.Builder(this)
                        .setTitle(R.string.halaqaNamePlease)
                        .setNeutralButton(android.R.string.ok) { dialog: DialogInterface?, which: Int -> dialog.dismiss() }.create().show()
            } else {
                halaqaActionProgressBar.setVisibility(View.VISIBLE)
                Thread(addHalaqa).start()
            }
        })
        add_halaqa_students.setOnClickListener(View.OnClickListener { view: View? ->
            view.setVisibility(View.GONE)
            val getStudents = Runnable {
                val students = db.myDAO().allStudents
                val studentBooleanMap: MutableMap<Student?, Boolean?> = HashMap()
                for (i in students.indices) {
                    studentBooleanMap[students[i]] = false
                }
                runOnUiThread { halaqaStudentsHelper = HalaqaStudentsHelper(studentsHolder, studentBooleanMap, 1, this) }
            }
            Thread(getStudents).start()
        })
    }

    var addHalaqa: Runnable? = Runnable {
        val dbHws: MutableList<HomeWork?> = ArrayList()
        val hName = halaqaNameEditText.getText().toString()
        val halaqaID = hName + "_" + System.currentTimeMillis()
        val hDates: MutableList<HalaqaDate?> = ArrayList()
        for (hdh in halaqaDates) {
            if (hdh.getCurPickedDate() == null) continue
            hDates.add(HalaqaDate(hdh.getCurPickedDate(), hdh.getRepeatPeriodInDays()))
        }
        val halaqa = Halaqa(hName,
                halaqaInfo.getText().toString(),
                hDates,
                halaqaID)

        //ArrayList<String> assigned = new ArrayList<>();
        //assigned.add(s.getName());
        val hwsIDs: MutableList<String?> = ArrayList()
        for (hw in hws) {
            val s1 = hw.sura1.selectedItem as String
            val s2 = hw.sura2.selectedItem as String
            val a1 = hw.ayah1.selectedItem as Int
            val a2 = hw.ayah2.selectedItem as Int
            val hwID = s1 + a1 + "_" + s2 + a2 + "_" + System.currentTimeMillis()
            hwsIDs.add(hwID)
            dbHws.add(HomeWork(hw.hw_title.text.toString(),
                    hw.curPickedDate,
                    s1,
                    s2,
                    a1,
                    a2,
                    hwID))
        }
        db.myDAO().insertHalaqa(halaqa)
        db.myDAO().insertHomework(*dbHws.toTypedArray())
        halaqaStudentsHelper = HalaqaStudentsHelper(studentsHolder, studentBooleanMap, 1, this)
        val studentsCheckState = halaqaStudentsHelper.getAdapter().studentsIsChecked
        val students = ArrayList(studentsCheckState.keys)
        val thisHalaqaStudents = ArrayList<Student?>()
        for (s in students) {
            if (studentsCheckState[s] === java.lang.Boolean.TRUE) {
                thisHalaqaStudents.add(s)
                db.myDAO().insertHalaqaAndStudentsCrossRef(HalaqatAndStudentsCrossRef(
                        halaqaID, s.getStudentId()
                ))
            }
        }
        for (str in hwsIDs) {
            db.myDAO().insertHalaqaAndHomeWorksCrossRef(HalaqatAndHomeWorksCrossRef(
                    halaqaID, str
            ))
            for (s in thisHalaqaStudents) db.myDAO().insertStudentsAndHomeWorksCrossRef(StudentsAndHomeWorksCrossRef(s.getStudentId(), str))
        }
        runOnUiThread { halaqaActionProgressBar.setVisibility(View.GONE) }
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun editHalaqaMode(halaqaID: String?) {
        halaqaActionProgressBar.setVisibility(View.VISIBLE)
        val isHomeWorksNewClassification: MutableMap<HalaqaHomeworkClass?, Boolean?> = HashMap()
        val thisHalaqaStudents = ArrayList<Student?>()
        setUpToolbar(R.string.edit_halaqa)
        val getHalaqaAndHomeWorks = Runnable {


            // we have got the student -> s
            val h = db.myDAO().getHalaqa(halaqaID)
            val thisHalaqaHomeWorksIDs = db.myDAO().getHalaqaHomeWorks(h.halaqaID)

            // we have got the halaqa's HomeWorks -> hisHomeWorks
            val halaqaHomeWorks: MutableList<HomeWork?> = ArrayList()
            for (str in thisHalaqaHomeWorksIDs) halaqaHomeWorks.add(db.myDAO().getHomeWork(str))
            runOnUiThread {
                halaqaActionProgressBar.setVisibility(View.GONE)
                halaqaNameEditText.setText(h.name)
                halaqaInfo.setText(h.info)
                for (hw in halaqaHomeWorks) {
                    val hhc = HalaqaHomeworkClass(this@HalaqaActivity, halaqa_homeWorks_holder, StudentActivity.Companion.EDIT, hw)
                    hws.add(hhc)
                    isHomeWorksNewClassification[hhc] = false
                }
                if (h.halaqaDates != null) for (hd in h.halaqaDates) {
                    val hdh = HalaqaDatesHelper(halaqa_dates_holder, this@HalaqaActivity, StudentActivity.Companion.EDIT, hd)
                    halaqaDates.add(hdh)
                }
            }
        }
        val editHalaqa = Runnable {
            val hName = halaqaNameEditText.getText().toString()
            val hDates: MutableList<HalaqaDate?> = ArrayList()
            for (hdh in halaqaDates) {
                if (hdh.getCurPickedDate() == null) continue
                hDates.add(HalaqaDate(hdh.getCurPickedDate(), hdh.getRepeatPeriodInDays()))
            }
            db.myDAO().updateHalaqa(halaqaID, halaqaNameEditText.getText().toString(), halaqaInfo.getText().toString(), hDates)
            val studentsCheckState = halaqaStudentsHelper.getAdapter().studentsIsChecked
            val students = ArrayList(studentsCheckState.keys)
            val oldCheckedIDs = db.myDAO().getHalaqaStudents(halaqaID)
            for (s in students) {
                if (studentsCheckState[s] === java.lang.Boolean.TRUE && !oldCheckedIDs.contains(s.getStudentId())) {
                    db.myDAO().insertHalaqaAndStudentsCrossRef(HalaqatAndStudentsCrossRef(
                            halaqaID, s.getStudentId()
                    ))
                } else {
                    if (oldCheckedIDs.contains(s.getStudentId()) && studentsCheckState[s] === java.lang.Boolean.FALSE) {
                        db.myDAO().deleteStudentFromHalaqa(s.getStudentId())
                    }
                }
            }
            for (hw in hws) {
                val isNew = isHomeWorksNewClassification[hw]
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
                    db.myDAO().insertHalaqaAndHomeWorksCrossRef(HalaqatAndHomeWorksCrossRef(halaqaID, hwID))
                    for (s in thisHalaqaStudents) db.myDAO().insertStudentsAndHomeWorksCrossRef(StudentsAndHomeWorksCrossRef(s.getStudentId(), hwID))
                } else {
                    /*hw.getHw().getTitle(),hw.getHw().getDueDate(),
                            hw.getHw().getSura1(),hw.getHw().getSura2(),hw.getHw().getAyah1(),hw.getHw().getAyah2()*/
                    db.myDAO().updateHomeWork(hw.getHw().idTitle, hw.getHw_title().text.toString(),
                            hw.getCurPickedDate(), hw.getSura1().selectedItem.toString(), hw.getSura2().selectedItem.toString(),
                            hw.getAyah1().selectedItem as Int,
                            hw.getAyah2().selectedItem as Int)
                }
            }
            for (hwToDelete in hwsToDelete) {
                db.myDAO().deleteHalaqaHomeworkCrossRef(halaqaID, hwToDelete)
                for (student in students) if (studentsCheckState[student] === java.lang.Boolean.TRUE) db.myDAO().deleteStudentHomeworkCrossRef(student.getStudentId(), hwToDelete)
            }
            runOnUiThread { halaqaActionProgressBar.setVisibility(View.GONE) }

            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish()
        }
        add_halaqa_homework.setOnClickListener(View.OnClickListener { view: View? ->
            val hhc = HalaqaHomeworkClass(this, halaqa_homeWorks_holder, 1, null)
            isHomeWorksNewClassification[hhc] = true
            hws.add(hhc)
        })
        add_halaqa_date.setOnClickListener(View.OnClickListener { view: View? ->
            val halaqaDate = HalaqaDatesHelper(halaqa_dates_holder, this, 1, null)
            halaqaDates.add(halaqaDate)
        })
        Thread(getHalaqaAndHomeWorks).start()
        saveButton.setOnClickListener(View.OnClickListener { view: View? ->
            if (halaqaNameEditText.getText().toString() == "") {
                AlertDialog.Builder(this)
                        .setTitle(R.string.halaqaNamePlease)
                        .setNeutralButton(android.R.string.ok) { dialog: DialogInterface?, which: Int -> dialog.dismiss() }.create().show()
            } else {
                halaqaActionProgressBar.setVisibility(View.VISIBLE)
                Thread(editHalaqa).start()
            }
        })
        add_halaqa_students.setVisibility(View.GONE)
        val getStudents = Runnable {
            val students = db.myDAO().allStudents
            val checkedStudents = db.myDAO().getHalaqaStudents(halaqaID)
            val studentBooleanMap: MutableMap<Student?, Boolean?> = HashMap()
            for (i in students.indices) {
                if (checkedStudents.contains(students[i].idName)) {
                    thisHalaqaStudents.add(students[i])
                    studentBooleanMap[students[i]] = true
                } else studentBooleanMap[students[i]] = false
            }
            runOnUiThread { halaqaStudentsHelper = HalaqaStudentsHelper(studentsHolder, studentBooleanMap, 1, this) }
        }
        Thread(getStudents).start()
    }

    private fun viewHalaqaMode(halaqaID: String?) {
        add_halaqa_date.setVisibility(View.GONE)
        add_halaqa_homework.setVisibility(View.GONE)
        saveButton.setVisibility(View.GONE)
        add_halaqa_students.setVisibility(View.GONE)
        setUpToolbar(R.string.view_halaqa)
        halaqaActionProgressBar.setVisibility(View.VISIBLE)
        val getHalaqa = Runnable {
            val halaqa = db.myDAO().getHalaqa(halaqaID)
            val homeWorkList: MutableList<HomeWork?> = ArrayList()
            val dateList = halaqa.halaqaDates
            val hwsIDs = db.myDAO().getHalaqaHomeWorks(halaqa.halaqaID)
            for (stHw in hwsIDs) homeWorkList.add(db.myDAO().getHomeWork(stHw))
            runOnUiThread {
                halaqaActionProgressBar.setVisibility(View.GONE)
                halaqaNameEditText.setText(halaqa.name)
                halaqaInfo.setText(halaqa.info)
                halaqaNameEditText.setEnabled(false) //android.R.color.transparent
                halaqaNameEditText.setBackgroundResource(android.R.color.transparent)
                halaqaNameEditText.setTextColor(resources.getColor(android.R.color.black))
                halaqaInfo.setEnabled(false)
                halaqaInfo.setBackgroundResource(android.R.color.transparent)
                halaqaInfo.setTextColor(resources.getColor(android.R.color.black))
                for (stHw in homeWorkList) HalaqaHomeworkClass(this, halaqa_homeWorks_holder, 0, stHw)
                if (dateList != null) for (date in dateList) HalaqaDatesHelper(halaqa_dates_holder, this, 0, date)
            }
        }
        Thread(getHalaqa).start()
        val getStudents = Runnable {
            val students = db.myDAO().allStudents
            val checkedStudents = db.myDAO().getHalaqaStudents(halaqaID)
            val studentBooleanMap: MutableMap<Student?, Boolean?> = HashMap()
            for (i in students.indices) {
                studentBooleanMap[students[i]] = checkedStudents.contains(students[i].idName)
            }
            runOnUiThread { halaqaStudentsHelper = HalaqaStudentsHelper(studentsHolder, studentBooleanMap, 0, this) }
        }
        Thread(getStudents).start()
    }

    private fun setUpToolbar(toolBarTitle: Int) {
        val mToolbar = findViewById<Toolbar?>(R.id.mainToolbar)
        setSupportActionBar(mToolbar)
        if (supportActionBar == null) return
        supportActionBar.setDisplayHomeAsUpEnabled(true)
        supportActionBar.setDisplayShowHomeEnabled(true)
        supportActionBar.setTitle(toolBarTitle)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}