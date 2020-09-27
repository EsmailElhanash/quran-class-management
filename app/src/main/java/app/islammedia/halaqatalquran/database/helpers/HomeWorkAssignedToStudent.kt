package app.islammedia.halaqatalquran.database.helpers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.database.entities.StudentsAndHomeWorksCrossRef

class HomeWorkAssignedToStudent {
    @kotlin.jvm.JvmField
    @Embedded
    var hw: HomeWork? = null

    @kotlin.jvm.JvmField
    @Relation(parentColumn = "idTitle", entityColumn = "idName", associateBy = Junction(StudentsAndHomeWorksCrossRef::class))
    var st: MutableList<Student?>? = null
}