package app.islammedia.halaqatalquran.database.helpers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.islammedia.halaqatalquran.database.entities.Halaqa
import app.islammedia.halaqatalquran.database.entities.HalaqatAndStudentsCrossRef
import app.islammedia.halaqatalquran.database.entities.Student

class HalaqaStudents {
    @Embedded
    var h: Halaqa? = null

    @Relation(parentColumn = "halaqaID", entityColumn = "idName", associateBy = Junction(HalaqatAndStudentsCrossRef::class))
    var students: MutableList<Student?>? = null
}