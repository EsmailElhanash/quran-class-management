package app.islammedia.halaqatalquran.student.homework

import android.content.DialogInterface
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.database.MainDataBase
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.student.StudentActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class HomeworkAddTemplate : DatePickerDialog.OnDateSetListener {

    constructor(studentActivity: StudentActivity){
        this.studentActivity = studentActivity
        findViews(studentActivity)
    }

    private var deadline_date_view: TextView? = null

    private lateinit var sura1: Spinner
    private lateinit var ayah1: Spinner
    private lateinit var sura2: Spinner
    private lateinit var ayah2: Spinner
    private lateinit var holder: View
    private lateinit var studentActivity: StudentActivity


    private fun findViews(studentActivity: StudentActivity){
        sura1 = studentActivity.findViewById(R.id.sura_start_spinner)
        ayah1 = studentActivity.findViewById(R.id.ayah_start_spinner)
        sura2 = studentActivity.findViewById(R.id.sura_end_spinner)
        ayah2 = studentActivity.findViewById(R.id.ayah_end_spinner)


    }


    var add_deadline: Button? = null
    var config: Configuration?
    var sw: Int
    var hw_title: EditText? = null
    var sowarNames: Array<String?>?
    var sowar_verses_num: IntArray?
    var curPickedDate: Calendar? = null
    fun getHw(): HomeWork? {
        return hw
    }

    var week: Long = 604800000
    var day: Long = 86400000
    private fun editStudentMode(holder: ViewGroup?, hw: HomeWork?) {
        val hwTemplate = LayoutInflater.from(studentActivity
        ).inflate(R.layout.template_student_hw, holder, false)
        holder.addView(hwTemplate)
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view)
        val c = hw.getDueDate()
        if (c != null) updateDateTV(c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.DAY_OF_MONTH])
        add_deadline = hwTemplate.findViewById(R.id.add_deadline)
        hw_title = hwTemplate.findViewById(R.id.hw_title)
        hw_title.setText(hw.getTitle())
        sura1 = hwTemplate.findViewById(R.id.sura_start_spinner)
        sura1.setAdapter(ArrayAdapter(studentActivity,
                R.layout.spinner_custom_item,
                sowarNames))
        sura1.setSelection(Arrays.asList(*sowarNames).indexOf(hw.getSura1()))
        ayah1 = hwTemplate.findViewById(R.id.ayah_start_spinner)
        val ayat = arrayOfNulls<Int?>(sowar_verses_num.get(hw.getAyah1()))
        for (v in ayat.indices) {
            ayat[v] = 1 + v
        }
        ayah1.setAdapter(ArrayAdapter(studentActivity,
                R.layout.spinner_custom_item,
                ayat))
        ayah1.setSelection(hw.getAyah1() - 1)
        sura2 = hwTemplate.findViewById(R.id.sura_end_spinner)
        sura2.setAdapter(ArrayAdapter(studentActivity,
                R.layout.spinner_custom_item,
                sowarNames))
        sura2.setSelection(Arrays.asList(*sowarNames).indexOf(hw.getSura2()))
        ayah2 = hwTemplate.findViewById(R.id.ayah_end_spinner)
        val ayat2 = arrayOfNulls<Int?>(sowar_verses_num.get(hw.getAyah1()))
        for (v in ayat2.indices) {
            ayat2[v] = 1 + v
        }
        ayah2.setAdapter(ArrayAdapter(studentActivity,
                R.layout.spinner_custom_item,
                ayat2))
        ayah2.setSelection(hw.getAyah1() - 1)

        //Listeners
        sura1.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                val ayat = arrayOfNulls<Int?>(sowar_verses_num.get(i))
                for (v in 0 until sowar_verses_num.get(i)) {
                    ayat[v] = 1 + v
                }
                ayah1.setAdapter(ArrayAdapter(studentActivity,
                        R.layout.spinner_custom_item,
                        ayat))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        sura2.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                val ayat2 = arrayOfNulls<Int?>(sowar_verses_num.get(i))
                for (v in 0 until sowar_verses_num.get(i)) {
                    ayat2[v] = 1 + v
                }
                ayah2.setAdapter(ArrayAdapter(studentActivity,
                        R.layout.spinner_custom_item,
                        ayat2))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        //Buttons
        val delHW = hwTemplate.findViewById<Button?>(R.id.delete_hw)
        val moreDay = hwTemplate.findViewById<Button?>(R.id.nextHalaqa)
        val moreWeek = hwTemplate.findViewById<Button?>(R.id.moreWeek)
        val clearDate = hwTemplate.findViewById<Button?>(R.id.date_delete)
        init_date_picker()
        delHW.setOnClickListener { view: View? ->
            AlertDialog.Builder(studentActivity
            ).setTitle("هل أنت متأكد؟")
                    .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface?, i: Int ->
                        holder.removeView(hwTemplate)
                        studentActivity.hws.remove(this@HomeworkAddTemplate)
                        studentActivity.hwsToDeleteIDs.add(hw.getHomeWorkID())
                    }
                    .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface?, i: Int -> dialogInterface.dismiss() }
                    .create().show()
        }
        moreDay.setOnClickListener { view: View? ->
            if (curPickedDate == null) curPickedDate = Calendar.getInstance()
            val newDate = curPickedDate.getTimeInMillis() + day
            curPickedDate.setTimeInMillis(newDate)
            updateDateTV(curPickedDate.get(Calendar.YEAR), curPickedDate.get(Calendar.MONTH), curPickedDate.get(Calendar.DAY_OF_MONTH))
        }
        moreWeek.setOnClickListener { view: View? ->
            if (curPickedDate == null) curPickedDate = Calendar.getInstance()
            val newDate = curPickedDate.getTimeInMillis() + week
            curPickedDate.setTimeInMillis(newDate)
            updateDateTV(curPickedDate.get(Calendar.YEAR), curPickedDate.get(Calendar.MONTH), curPickedDate.get(Calendar.DAY_OF_MONTH))
        }
        clearDate.setOnClickListener { view: View? ->
            curPickedDate = null
            deadline_date_view.setText(studentActivity
                    .getResources().getText(R.string.date_not_set))
        }
    }

    fun addStudentMode(holder: ViewGroup?) {
        val hwTemplate = LayoutInflater.from(studentActivity
        ).inflate(R.layout.template_student_hw, holder, false)
        holder.addView(hwTemplate)
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view)
        add_deadline = hwTemplate.findViewById(R.id.add_deadline)
        hw_title = hwTemplate.findViewById(R.id.hw_title)
        sura1 = hwTemplate.findViewById(R.id.sura_start_spinner)
        ayah1 = hwTemplate.findViewById(R.id.ayah_start_spinner)
        sura2 = hwTemplate.findViewById(R.id.sura_end_spinner)
        ayah2 = hwTemplate.findViewById(R.id.ayah_end_spinner)
        sura1.setAdapter(ArrayAdapter(studentActivity,
                R.layout.spinner_custom_item,
                sowarNames))
        sura2.setAdapter(ArrayAdapter(studentActivity,
                R.layout.spinner_custom_item,
                sowarNames))

        //Listeners
        sura1.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                val ayat = arrayOfNulls<Int?>(sowar_verses_num.get(i))
                for (v in 0 until sowar_verses_num.get(i)) {
                    ayat[v] = 1 + v
                }
                ayah1.setAdapter(ArrayAdapter(studentActivity,
                        R.layout.spinner_custom_item,
                        ayat))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        sura2.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                val ayat2 = arrayOfNulls<Int?>(sowar_verses_num.get(i))
                for (v in 0 until sowar_verses_num.get(i)) {
                    ayat2[v] = 1 + v
                }
                ayah2.setAdapter(ArrayAdapter(studentActivity,
                        R.layout.spinner_custom_item,
                        ayat2))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        //Buttons
        val delHW = hwTemplate.findViewById<Button?>(R.id.delete_hw)
        val moreDay = hwTemplate.findViewById<Button?>(R.id.nextHalaqa)
        val moreWeek = hwTemplate.findViewById<Button?>(R.id.moreWeek)
        val clearDate = hwTemplate.findViewById<Button?>(R.id.date_delete)
        init_date_picker()
        delHW.setOnClickListener { view: View? ->
            AlertDialog.Builder(studentActivity
            ).setTitle("هل أنت متأكد؟")
                    .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface?, i: Int ->
                        holder.removeView(hwTemplate)
                        studentActivity.hws.remove(this@HomeworkAddTemplate)
                    }
                    .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface?, i: Int -> dialogInterface.dismiss() }
                    .create().show()
        }
        moreDay.setOnClickListener { view: View? ->
            if (curPickedDate == null) curPickedDate = Calendar.getInstance()
            val newDate = curPickedDate.getTimeInMillis() + day
            curPickedDate.setTimeInMillis(newDate)
            updateDateTV(curPickedDate.get(Calendar.YEAR), curPickedDate.get(Calendar.MONTH), curPickedDate.get(Calendar.DAY_OF_MONTH))
        }
        moreWeek.setOnClickListener { view: View? ->
            if (curPickedDate == null) curPickedDate = Calendar.getInstance()
            val newDate = curPickedDate.getTimeInMillis() + week
            curPickedDate.setTimeInMillis(newDate)
            updateDateTV(curPickedDate.get(Calendar.YEAR), curPickedDate.get(Calendar.MONTH), curPickedDate.get(Calendar.DAY_OF_MONTH))
        }
        clearDate.setOnClickListener { view: View? ->
            curPickedDate = null
            deadline_date_view.setText(studentActivity
                    .getResources().getText(R.string.date_not_set))
        }
    }

    fun viewHomeworkMode(holder: ViewGroup?, hw: HomeWork?) {
        val hwTemplate = LayoutInflater.from(studentActivity
        ).inflate(R.layout.template_view_homework, holder, false)
        holder.addView(hwTemplate)
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view)
        val hw_description = hwTemplate.findViewById<TextView?>(R.id.hw_description)
        val hw_title = hwTemplate.findViewById<TextView?>(R.id.hw_title)
        hw_title.text = hw.getTitle()
        val hw_dec = "سورة " + hw.getSura1() + " آية " + hw.getAyah1() + " إلى " + "سورة " + hw.getSura2() + " آية " + hw.getAyah2()
        hw_description.text = hw_dec
        val cur_rating = hwTemplate.findViewById<EditText?>(R.id.cur_rating)
        val plus_half = hwTemplate.findViewById<TextView?>(R.id.plus_half)
        val minus_half = hwTemplate.findViewById<TextView?>(R.id.minus_half)
        val saveRating = hwTemplate.findViewById<ImageButton?>(R.id.check_save_button)
        saveRating.tag = R.drawable.ic_baseline_save_24
        plus_half.setOnClickListener { view: View? ->
            val ratingString = cur_rating.text.toString()
            if (isNumeric(ratingString) && ratingString.toFloat() <= 9.5) {
                val rating = cur_rating.text.toString().toFloat()
                cur_rating.setText((rating + 0.5f).toString())
            }
        }
        minus_half.setOnClickListener { view: View? ->
            val ratingString = cur_rating.text.toString()
            if (isNumeric(ratingString) && ratingString.toFloat() >= 0.5) {
                val rating = cur_rating.text.toString().toFloat()
                cur_rating.setText((rating - 0.5f).toString())
            }
        }
        saveRating.setOnClickListener { view: View? ->
            if ((view as ImageButton?).getTag() as Int == R.drawable.ic_baseline_save_24) {
                Thread {
                    val db = Room.databaseBuilder(studentActivity, MainDataBase::class.java, "MainDataBase").build()
                    db.myDAO().setHomeWorkRating(cur_rating.text.toString().toFloat(), hw.getHomeWorkID())
                }.start()
                cur_rating.isEnabled = false
                saveRating.tag = R.drawable.edit_24
                saveRating.setImageResource(R.drawable.edit_24)
                hwTemplate.findViewById<View?>(R.id.edit_rating_layout).visibility = View.GONE
            } else if ((view as ImageButton?).getTag() as Int == R.drawable.edit_24) {
                saveRating.setImageResource(R.drawable.ic_baseline_save_24)
                cur_rating.isEnabled = true
                saveRating.tag = R.drawable.ic_baseline_save_24
                hwTemplate.findViewById<View?>(R.id.edit_rating_layout).visibility = View.VISIBLE
            }
        }
        if (hw.getDueDate() != null) updateDateTV(hw.getDueDate()[Calendar.YEAR], hw.getDueDate()[Calendar.MONTH], hw.getDueDate()[Calendar.DAY_OF_MONTH])
    }

    fun init_date_picker() {

        //homework deadline date


        //DatePickerDialog init
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this,
                now[Calendar.YEAR],  // Initial year selection
                now[Calendar.MONTH],  // Initial month selection
                now[Calendar.DAY_OF_MONTH] // Inital day selection
        )
        dpd.locale = Locale("ar")


        //finding views//
        add_deadline.setOnClickListener(View.OnClickListener { view: View? -> dpd.show(studentActivity.getSupportFragmentManager(), "Datepickerdialog") })
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        updateDateTV(year, monthOfYear, dayOfMonth)
    }

    private fun updateDateTV(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        var monthOfYear = monthOfYear
        val c = Calendar.getInstance()
        c[year, monthOfYear] = dayOfMonth
        curPickedDate = c
        val sdf = SimpleDateFormat("EEEE", Locale("ar"))
        val current_picked_date = sdf.format(Date(c.timeInMillis)) + "  " + year + "-" + ++monthOfYear + "-" + dayOfMonth
        deadline_date_view.setText(current_picked_date)
    }

    fun getHw_title(): EditText? {
        return hw_title
    }

    fun getSura1(): Spinner? {
        return sura1
    }

    fun getSura2(): Spinner? {
        return sura2
    }

    fun getAyah1(): Spinner? {
        return ayah1
    }

    fun getAyah2(): Spinner? {
        return ayah2
    }

    fun getCurPickedDate(): Calendar? {
        return curPickedDate
    }

    companion object {
        fun isNumeric(str: String?): Boolean {
            return try {
                str.toDouble()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
    }

    init {
        sowar_verses_num = studentActivity
                .getResources().getIntArray(R.array.sowar_verses_num)
        sowarNames = studentActivity
                .getResources().getStringArray(R.array.sura_names)
        config = studentActivity.getApplicationContext().resources.configuration
        sw = config.smallestScreenWidthDp
        when (mode) {
            1 -> addStudentMode(holder)
            2 -> editStudentMode(holder, hw)
            else -> {
                if (hw == null) return
                viewHomeworkMode(holder, hw)
            }
        }


        //System.out.println(curPickedDate.get(Calendar.DAY_OF_MONTH) + " " + curPickedDate.get(Calendar.MONTH));
    }
}