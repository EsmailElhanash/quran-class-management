package app.islammedia.halaqatalquran.database.helpers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.database.entities.StudentsAndHomeWorksCrossRef

class StudentHomeWorks {
    @kotlin.jvm.JvmField
    @Embedded
    var st: Student? = null

    @kotlin.jvm.JvmField
    @Relation(parentColumn = "idName", entityColumn = "idTitle", associateBy = Junction(StudentsAndHomeWorksCrossRef::class))
    var hws: MutableList<HomeWork?>? = null
}