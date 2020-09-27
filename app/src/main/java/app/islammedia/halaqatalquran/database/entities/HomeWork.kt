package app.islammedia.halaqatalquran.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class HomeWork(@field:ColumnInfo(name = "hw_title") var title: String?, dueDate: Calendar?, sura1: String?, sura2: String?, ayah1: Int, ayah2: Int, idTitle: String) {
    fun getHomeWorkID(): String {
        return homeWorkID
    }

    fun setHomeWorkID(homeWorkID: String) {
        this.homeWorkID = homeWorkID
    }

    @ColumnInfo(name = "homeWorkID")
    @PrimaryKey
    private var homeWorkID: String

    @ColumnInfo(name = "rating")
    private var rating = 0f
    fun setRating(rating: Float) {
        this.rating = rating
    }

    fun getRating(): Float {
        return rating
    }

    fun setStartDate(startDate: Long) {
        this.startDate = startDate
    }

    fun getStartDate(): Long {
        return startDate
    }

    @ColumnInfo(name = "startDate")
    private var startDate: Long

    @ColumnInfo(name = "hw_dueDate")
    var dueDate: Calendar?

    @ColumnInfo(name = "hw_sura1")
    var sura1: String?

    @ColumnInfo(name = "hw_sura2")
    var sura2: String?

    @ColumnInfo(name = "hw_ayah1")
    var ayah1: Int

    @ColumnInfo(name = "hw_ayah2")
    var ayah2: Int
    fun getTitle(): String? {
        return title
    }

    fun getDueDate(): Calendar? {
        return dueDate
    }

    fun getSura1(): String? {
        return sura1
    }

    fun getSura2(): String? {
        return sura2
    }

    fun getAyah1(): Int {
        return ayah1
    }

    fun getAyah2(): Int {
        return ayah2
    }

    //ArrayList<String> sts_assigned;
    init {
        if (title.isEmpty()) {
            title = sura1 + ayah1 + "_" + sura2 + ayah2
        }
        startDate = System.currentTimeMillis()
        this.dueDate = dueDate
        this.sura1 = sura1
        this.sura2 = sura2
        this.ayah1 = ayah1
        this.ayah2 = ayah2
        this.homeWorkID = idTitle
        /*;;*/
    }
}