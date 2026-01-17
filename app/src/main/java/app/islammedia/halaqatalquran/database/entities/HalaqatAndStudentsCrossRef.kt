package app.islammedia.halaqatalquran.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["halaqaID", "idName"])
class HalaqatAndStudentsCrossRef(var halaqaID: String, var idName: String)