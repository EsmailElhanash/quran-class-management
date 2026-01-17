package app.islammedia.halaqatalquran.helping_classes

import android.view.LayoutInflater
import android.view.View
import android.widget.*
import app.islammedia.halaqatalquran.database.helpers.HalaqaDate
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.halaqa.HalaqaActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class HalaqaDatesHelper(var holder: LinearLayout?, var halaqaActivity: HalaqaActivity?, mode: Int, date: HalaqaDate?) : DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    var mainView: View? = null
    var pickedDateTextView: TextView? = null
    var curPickedDate: Calendar? = null
    var tpd: TimePickerDialog? = null
    var ymd: IntArray? = IntArray(3)
    var custom_rep_write: LinearLayout? = null
    var repeatChoose: RadioGroup? = null
    var custom_rep_editText: EditText? = null
    var clearDate: Button? = null

    //int repeatPeriod = 0;
    fun getCurPickedDate(): Calendar? {
        return curPickedDate
    }

    fun getRepeatPeriodInDays(): Int {
        // 0 is no rep , -1 is monthly in some day
        return when (repeatChoose.getCheckedRadioButtonId()) {
            R.id.other_rep -> custom_rep_editText.getText().toString().toInt()
            R.id.daily_rep -> 1
            R.id.weekly_rep -> 7
            R.id.monthly_rep -> -1
            R.id.no_rep -> 0
            else -> -2
        }
    }

    fun addHalaqaMode(holder: LinearLayout?) {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this,
                now[Calendar.YEAR],  // Initial year selection
                now[Calendar.MONTH],  // Initial month selection
                now[Calendar.DAY_OF_MONTH] // Inital day selection
        )
        dpd.locale = Locale("ar")
        tpd = TimePickerDialog.newInstance(this,
                false)
        tpd.enableSeconds(false)
        tpd.setLocale(Locale("ar"))
        dpd.show(halaqaActivity.getSupportFragmentManager(), "Datepickerdialog")
        mainView = LayoutInflater.from(halaqaActivity).inflate(R.layout.template_date_holder, holder, false)
        custom_rep_write = mainView.findViewById(R.id.custom_rep_write)
        pickedDateTextView = mainView.findViewById(R.id.pickedDateTextView)
        repeatChoose = mainView.findViewById(R.id.reps_rad_group)
        custom_rep_editText = mainView.findViewById(R.id.custom_rep_editText)
        clearDate = mainView.findViewById(R.id.clearDate)
        repeatChoose.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            if (i == R.id.other_rep) {
                custom_rep_write.setVisibility(View.VISIBLE)
            } else {
                custom_rep_write.setVisibility(View.GONE)
            }
        })
        clearDate.setOnClickListener(View.OnClickListener { view: View? ->
            curPickedDate = null
            holder.removeView(mainView)
        })
    }

    fun editHalaqaMode(holder: LinearLayout?, date: HalaqaDate?) {
        val now = Calendar.getInstance()
        mainView = LayoutInflater.from(halaqaActivity).inflate(R.layout.template_date_holder, holder, false)
        holder.addView(mainView)
        custom_rep_write = mainView.findViewById(R.id.custom_rep_write)
        pickedDateTextView = mainView.findViewById(R.id.pickedDateTextView)
        ymd.get(0) = date.getDate()[Calendar.YEAR]
        ymd.get(1) = date.getDate()[Calendar.MONTH]
        ymd.get(2) = date.getDate()[Calendar.DAY_OF_MONTH]
        updateDateTVWithTime(date.getDate()[Calendar.HOUR_OF_DAY], date.getDate()[Calendar.MINUTE])
        repeatChoose = mainView.findViewById(R.id.reps_rad_group)
        custom_rep_editText = mainView.findViewById(R.id.custom_rep_editText)
        clearDate = mainView.findViewById(R.id.clearDate)
        repeatChoose.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup: RadioGroup?, i: Int ->
            if (i == R.id.other_rep) {
                custom_rep_write.setVisibility(View.VISIBLE)
            } else {
                custom_rep_write.setVisibility(View.GONE)
            }
        })
        clearDate.setOnClickListener(View.OnClickListener { view: View? ->
            curPickedDate = null
            holder.removeView(mainView)
        })
        when (date.getRep()) {
            -1 -> {
                val month = mainView.findViewById<RadioButton?>(R.id.monthly_rep)
                month.isChecked = true
            }
            0 -> {
                val noRep = mainView.findViewById<RadioButton?>(R.id.no_rep)
                noRep.isChecked = true
            }
            1 -> {
                val daily = mainView.findViewById<RadioButton?>(R.id.daily_rep)
                daily.isChecked = true
            }
            7 -> {
                val weekly = mainView.findViewById<RadioButton?>(R.id.weekly_rep)
                weekly.isChecked = true
            }
            else -> {
                val other = mainView.findViewById<RadioButton?>(R.id.other_rep)
                other.isChecked = true
            }
        }
    }

    fun viewHalaqaMode(holder: LinearLayout?, date: HalaqaDate?) {
        mainView = LayoutInflater.from(halaqaActivity).inflate(R.layout.template_date_holder, holder, false)
        holder.addView(mainView)
        custom_rep_write = mainView.findViewById(R.id.custom_rep_write)
        pickedDateTextView = mainView.findViewById(R.id.pickedDateTextView)
        repeatChoose = mainView.findViewById(R.id.reps_rad_group)
        custom_rep_editText = mainView.findViewById(R.id.custom_rep_editText)
        clearDate = mainView.findViewById(R.id.clearDate)
        clearDate.setVisibility(View.GONE)
        mainView.findViewById<View?>(R.id.rep_text).visibility = View.GONE
        repeatChoose.setVisibility(View.GONE)
        when (date.getRep()) {
            -1 -> {
                val monthlyTimeOfTheDay = String.format(Locale("ar"), "%02d:%02d", date.getDate()[Calendar.HOUR_OF_DAY], date.getDate()[Calendar.MINUTE])
                val halaqaDateMonthly = ("شهرياً يوم" + date.getDate()[Calendar.DAY_OF_MONTH]
                        + " "
                        + " الساعة"
                        + monthlyTimeOfTheDay)
                pickedDateTextView.setText(halaqaDateMonthly)
            }
            0 -> {
                ymd.get(0) = date.getDate()[Calendar.YEAR]
                ymd.get(1) = date.getDate()[Calendar.MONTH]
                ymd.get(2) = date.getDate()[Calendar.DAY_OF_MONTH]
                updateDateTVWithTime(date.getDate()[Calendar.HOUR_OF_DAY],
                        date.getDate()[Calendar.MINUTE])
            }
            1 -> {
                val dailyTime = String.format(Locale("ar"), "%02d:%02d", date.getDate()[Calendar.HOUR_OF_DAY], date.getDate()[Calendar.MINUTE])
                val halaqaDateDaily = "يومياً الساعة $dailyTime"
                pickedDateTextView.setText(halaqaDateDaily)
            }
            7 -> {
                val weeklyTimeOfDay = String.format(Locale("ar"), "%02d:%02d", date.getDate()[Calendar.HOUR_OF_DAY], date.getDate()[Calendar.MINUTE])
                val sdf = SimpleDateFormat("EEEE", Locale("ar"))
                val halaqaDateWeekly = "إسبوعيا يوم " + sdf.format(date.getDate().time) + " " + weeklyTimeOfDay
                pickedDateTextView.setText(halaqaDateWeekly)
            }
            else -> {
                val dates = date.getDate()[Calendar.YEAR].toString() + "-" +
                        date.getDate()[Calendar.MONTH] + "-" +
                        date.getDate()[Calendar.DAY_OF_MONTH] + " ويكرر كل " + date.getRep() + " يوم"
                pickedDateTextView.setText(dates)
            }
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        ymd.get(0) = year
        ymd.get(1) = monthOfYear
        ymd.get(2) = dayOfMonth
        tpd.show(halaqaActivity.getSupportFragmentManager(), "TimePickerDialog")
    }

    private fun updateDateTVWithTime(hourOfDay: Int, minute: Int) {
        val c = Calendar.getInstance()
        c[ymd.get(0), ymd.get(1), ymd.get(2), hourOfDay] = minute
        curPickedDate = c
        val sdf = SimpleDateFormat("EEEE", Locale("ar"))
        val curTime = String.format(Locale("ar"), "%02d:%02d", hourOfDay, minute)
        val current_picked_date_time = sdf.format(Date(c.timeInMillis)) + "  " + ymd.get(0) + "-" + ++ymd.get(1) + "-" + ymd.get(2) + " الساعة " + curTime
        pickedDateTextView.setText(current_picked_date_time)
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        updateDateTVWithTime(hourOfDay, minute)
        holder.addView(mainView)
    }

    companion object {
        const val NO_REP = 0
        const val MONTHLY_REP = -1
    }

    init {
        when (mode) {
            1 -> addHalaqaMode(holder)
            2 -> {
                if (date == null) return
                editHalaqaMode(holder, date)
            }
            else -> {
                if (date == null) return
                viewHalaqaMode(holder, date)
            }
        }
    }
}