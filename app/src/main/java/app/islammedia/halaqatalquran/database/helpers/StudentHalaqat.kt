package app.islammedia.halaqatalquran.database.helpers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.islammedia.halaqatalquran.database.entities.Halaqa
import app.islammedia.halaqatalquran.database.entities.HalaqatAndStudentsCrossRef
import app.islammedia.halaqatalquran.database.entities.Student

class StudentHalaqat {
    @Embedded
    var s: Student? = null

    @Relation(parentColumn = "idName", entityColumn = "halaqaID", associateBy = Junction(HalaqatAndStudentsCrossRef::class))
    var halaqat: MutableList<Halaqa?>? = null
}