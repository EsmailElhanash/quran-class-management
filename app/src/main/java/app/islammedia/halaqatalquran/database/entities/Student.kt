package app.islammedia.halaqatalquran.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Student(@field:ColumnInfo(name = "name") private var studentName: String,
              @field:ColumnInfo(name = "moreInfo") private val info: String?,
              @field:PrimaryKey @field:ColumnInfo(name = "idName") private var studentId: String)
    : Comparable<Student> {
    fun getStudentId(): String {
        return studentId
    }

    fun setStudentId(studentId: String) {
        this.studentId = studentId
    }

    fun getName(): String {
        return studentName
    }

    fun setName(name: String) {
        this.studentName = name
    }

    fun getInfo(): String? {
        return info
    }

    fun getHalaqaID(): Int {
        return halaqaID
    }

    fun setHalaqaID(halaqaID: Int) {
        this.halaqaID = halaqaID
    }

    @ColumnInfo(name = "hisHalaqa")
    private var halaqaID = 0

    override fun compareTo(other: Student): Int {
        return when {
            this.getName() < other.studentName -> -1
            this.getName() > other.studentName -> 1
            else -> 0
        }
    }
}