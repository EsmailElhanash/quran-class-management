package app.islammedia.halaqatalquran.database

import android.util.Log
import androidx.room.TypeConverter
import app.islammedia.halaqatalquran.database.helpers.HalaqaDate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object Converters {
    @TypeConverter
    fun ArrayListOfIntegersFromString(value: String?): ArrayList<Int?>? {
        val listType = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun StringFromArrayListOfInteger(list: ArrayList<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun ArrayListOfStringsFromString(value: String?): ArrayList<String?>? {
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun StringFromArrayListOfStrings(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @kotlin.jvm.JvmStatic
    @TypeConverter
    fun fromLong(l: Long?): Calendar? {
        if (l == null) return null
        val c = Calendar.getInstance()
        c.time = Date(l)
        return c
    }

    @kotlin.jvm.JvmStatic
    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return c?.timeInMillis
    }

    @kotlin.jvm.JvmStatic
    @TypeConverter
    fun fromHalaqaDates(hds: MutableList<HalaqaDate>): String? {
        if (hds.size == 0) return null
        val hdsString = StringBuilder()
        for (hd in hds) hdsString.append(hd.getDate()?.timeInMillis).append("_").append(hd.getRep()).append("&")
        hdsString.deleteCharAt(hdsString.length - 1)
        return hdsString.toString()
    }

    @kotlin.jvm.JvmStatic
    @TypeConverter
    fun toHalaqaDate(hdsString: String?): MutableList<HalaqaDate?>? {
        if (hdsString == null) return null
        val hdStr: Array<String> = hdsString.split("&".toRegex()).toTypedArray()
        val halaqaDates: MutableList<HalaqaDate?> = ArrayList()
        for (s in hdStr) {
            val calReps: Array<String> = s.split("_".toRegex()).toTypedArray()
            val c = Calendar.getInstance()
            c.timeInMillis = calReps[0].toLong()
            halaqaDates.add(HalaqaDate(c, calReps[1].toInt()))
        }
        Log.i("dates number", halaqaDates.size.toString())
        return halaqaDates
    }
}