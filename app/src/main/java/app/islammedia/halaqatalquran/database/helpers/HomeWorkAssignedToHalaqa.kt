package app.islammedia.halaqatalquran.database.helpers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.islammedia.halaqatalquran.database.entities.Halaqa
import app.islammedia.halaqatalquran.database.entities.HalaqatAndHomeWorksCrossRef
import app.islammedia.halaqatalquran.database.entities.HomeWork

class HomeWorkAssignedToHalaqa {
    @Embedded
    var hw: HomeWork? = null

    @Relation(parentColumn = "idTitle", entityColumn = "halaqaID", associateBy = Junction(HalaqatAndHomeWorksCrossRef::class))
    var halaqat: MutableList<Halaqa?>? = null
}