package app.islammedia.halaqatalquran.database.helpers

import java.util.*

class HalaqaDate(private var date: Calendar?, private var rep: Int) {
    fun getDate(): Calendar? {
        return date
    }

    fun setDate(date: Calendar?) {
        this.date = date
    }

    fun getRep(): Int {
        return rep
    }

    fun setRep(rep: Int) {
        this.rep = rep
    }
}