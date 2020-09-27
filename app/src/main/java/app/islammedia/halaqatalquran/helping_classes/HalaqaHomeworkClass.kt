package app.islammedia.halaqatalquran.helping_classes

import android.content.DialogInterface
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.R
import app.islammedia.halaqatalquran.halaqa.HalaqaActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*

class HalaqaHomeworkClass(private val halaqaActivity: HalaqaActivity?, holder: ViewGroup?, mode: Int, var hw: HomeWork?) : DatePickerDialog.OnDateSetListener {
    private var deadline_date_view: TextView? = null
    var sura1: Spinner? = null
    var ayah1: Spinner? = null
    var sura2: Spinner? = null
    var ayah2: Spinner? = null
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
    private fun editHalaqaMode(holder: ViewGroup?, hw: HomeWork?) {
        val hwTemplate = LayoutInflater.from(halaqaActivity
        ).inflate(R.layout.template_halaqa_homework, holder, false)
        holder.addView(hwTemplate)
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view)
        val c = hw.getDueDate()
        if (c != null) updateDateTV(c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.DAY_OF_MONTH])
        add_deadline = hwTemplate.findViewById(R.id.add_deadline)
        hw_title = hwTemplate.findViewById(R.id.hw_title)
        hw_title.setText(hw.getTitle())
        sura1 = hwTemplate.findViewById(R.id.sura_start_spinner)
        sura1.setAdapter(ArrayAdapter(halaqaActivity,
                R.layout.spinner_custom_item,
                sowarNames))
        sura1.setSelection(Arrays.asList(*sowarNames).indexOf(hw.getSura1()))
        ayah1 = hwTemplate.findViewById(R.id.ayah_start_spinner)
        val ayat = arrayOfNulls<Int?>(sowar_verses_num.get(hw.getAyah1()))
        for (v in ayat.indices) {
            ayat[v] = 1 + v
        }
        ayah1.setAdapter(ArrayAdapter(halaqaActivity,
                R.layout.spinner_custom_item,
                ayat))
        ayah1.setSelection(hw.getAyah1() - 1)
        sura2 = hwTemplate.findViewById(R.id.sura_end_spinner)
        sura2.setAdapter(ArrayAdapter(halaqaActivity,
                R.layout.spinner_custom_item,
                sowarNames))
        sura2.setSelection(Arrays.asList(*sowarNames).indexOf(hw.getSura2()))
        ayah2 = hwTemplate.findViewById(R.id.ayah_end_spinner)
        val ayat2 = arrayOfNulls<Int?>(sowar_verses_num.get(hw.getAyah1()))
        for (v in ayat2.indices) {
            ayat2[v] = 1 + v
        }
        ayah2.setAdapter(ArrayAdapter(halaqaActivity,
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
                ayah1.setAdapter(ArrayAdapter(halaqaActivity,
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
                ayah2.setAdapter(ArrayAdapter(halaqaActivity,
                        R.layout.spinner_custom_item,
                        ayat2))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        //Buttons
        val delHW = hwTemplate.findViewById<ImageButton?>(R.id.delete_hw)
        val nextHalaqa = hwTemplate.findViewById<Button?>(R.id.nextHalaqa)
        val clearDate = hwTemplate.findViewById<ImageButton?>(R.id.date_delete)
        init_date_picker()
        delHW.setOnClickListener { view: View? ->
            AlertDialog.Builder(halaqaActivity
            ).setTitle("هل أنت متأكد؟")
                    .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface?, i: Int ->
                        holder.removeView(hwTemplate)
                        halaqaActivity.hws.remove(this@HalaqaHomeworkClass)
                        halaqaActivity.hwsToDelete.add(hw.getHomeWorkID())
                    }
                    .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface?, i: Int -> dialogInterface.dismiss() }
                    .create().show()
        }
        nextHalaqa.setOnClickListener { view: View? ->
            if (curPickedDate == null) curPickedDate = Calendar.getInstance()
            val newDate = curPickedDate.getTimeInMillis() + day
            curPickedDate.setTimeInMillis(newDate)
            updateDateTV(curPickedDate.get(Calendar.YEAR), curPickedDate.get(Calendar.MONTH), curPickedDate.get(Calendar.DAY_OF_MONTH))
        }

        /*moreWeek.setOnClickListener(view -> {
            if (curPickedDate == null) curPickedDate = Calendar.getInstance();
            long newDate = curPickedDate.getTimeInMillis() + week;
            curPickedDate.setTimeInMillis(newDate);
            updateDateTV(curPickedDate.get(Calendar.YEAR) , curPickedDate.get(Calendar.MONTH) , curPickedDate.get(Calendar.DAY_OF_MONTH) );
        });*/clearDate.setOnClickListener { view: View? ->
            curPickedDate = null
            deadline_date_view.setText(halaqaActivity
                    .getResources().getText(R.string.date_not_set))
        }
    }

    fun addHalaqaMode(holder: ViewGroup?) {
        val hwTemplate = LayoutInflater.from(halaqaActivity
        ).inflate(R.layout.template_halaqa_homework, holder, false)
        holder.addView(hwTemplate)
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view)
        add_deadline = hwTemplate.findViewById(R.id.add_deadline)
        add_deadline.setText(R.string.add_date)
        hw_title = hwTemplate.findViewById(R.id.hw_title)
        sura1 = hwTemplate.findViewById(R.id.sura_start_spinner)
        ayah1 = hwTemplate.findViewById(R.id.ayah_start_spinner)
        sura2 = hwTemplate.findViewById(R.id.sura_end_spinner)
        ayah2 = hwTemplate.findViewById(R.id.ayah_end_spinner)
        sura1.setAdapter(ArrayAdapter(halaqaActivity,
                R.layout.spinner_custom_item,
                sowarNames))
        sura2.setAdapter(ArrayAdapter(halaqaActivity,
                R.layout.spinner_custom_item,
                sowarNames))

        //Listeners
        sura1.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
                val ayat = arrayOfNulls<Int?>(sowar_verses_num.get(i))
                for (v in 0 until sowar_verses_num.get(i)) {
                    ayat[v] = 1 + v
                }
                ayah1.setAdapter(ArrayAdapter(halaqaActivity,
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
                ayah2.setAdapter(ArrayAdapter(halaqaActivity,
                        R.layout.spinner_custom_item,
                        ayat2))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        //Buttons
        val delHW = hwTemplate.findViewById<ImageButton?>(R.id.delete_hw)
        val nextHalaqa = hwTemplate.findViewById<Button?>(R.id.nextHalaqa)
        nextHalaqa.visibility = View.GONE
        val clearDate = hwTemplate.findViewById<ImageButton?>(R.id.date_delete)
        init_date_picker()
        delHW.setOnClickListener { view: View? ->
            AlertDialog.Builder(halaqaActivity
            ).setTitle("هل أنت متأكد؟")
                    .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface?, i: Int ->
                        holder.removeView(hwTemplate)
                        halaqaActivity.hws.remove(this@HalaqaHomeworkClass)
                    }
                    .setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface?, i: Int -> dialogInterface.dismiss() }
                    .create().show()
        }
        clearDate.setOnClickListener { view: View? ->
            curPickedDate = null
            deadline_date_view.setText(halaqaActivity
                    .getResources().getText(R.string.date_not_set))
        }
    }

    fun viewHomeworkMode(holder: ViewGroup?, hw: HomeWork?) {
        val hwTemplate = LayoutInflater.from(halaqaActivity
        ).inflate(R.layout.template_view_homework, holder, false)
        holder.addView(hwTemplate)
        deadline_date_view = hwTemplate.findViewById(R.id.deadline_date_view)
        val hw_description = hwTemplate.findViewById<TextView?>(R.id.hw_description)
        val hw_title = hwTemplate.findViewById<TextView?>(R.id.hw_title)
        hw_title.text = hw.getTitle()
        val hw_dec = "سورة " + hw.getSura1() + " آية " + hw.getAyah1() + " إلى " + "سورة " + hw.getSura2() + " آية " + hw.getAyah2()
        hw_description.text = hw_dec
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
        add_deadline.setOnClickListener(View.OnClickListener { view: View? -> dpd.show(halaqaActivity.getSupportFragmentManager(), "Datepickerdialog") })
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

    init {
        sowar_verses_num = halaqaActivity
                .getResources().getIntArray(R.array.sowar_verses_num)
        sowarNames = halaqaActivity
                .getResources().getStringArray(R.array.sura_names)
        config = halaqaActivity.getApplicationContext().resources.configuration
        sw = config.smallestScreenWidthDp
        when (mode) {
            1 -> addHalaqaMode(holder)
            2 -> editHalaqaMode(holder, hw)
            else -> {
                if (hw == null) return
                viewHomeworkMode(holder, hw)
            }
        }


        //System.out.println(curPickedDate.get(Calendar.DAY_OF_MONTH) + " " + curPickedDate.get(Calendar.MONTH));
    }
}