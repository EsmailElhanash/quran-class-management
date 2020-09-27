package app.islammedia.halaqatalquran.database.helpers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import app.islammedia.halaqatalquran.database.entities.Halaqa
import app.islammedia.halaqatalquran.database.entities.HalaqatAndHomeWorksCrossRef
import app.islammedia.halaqatalquran.database.entities.HomeWork

class HalaqaHomeWorks {
    @Embedded
    var h: Halaqa? = null

    @Relation(parentColumn = "halaqaID", entityColumn = "idTitle", associateBy = Junction(HalaqatAndHomeWorksCrossRef::class))
    var hws: MutableList<HomeWork?>? = null
}