package app.islammedia.halaqatalquran.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["halaqaID", "idTitle"])
class HalaqatAndHomeWorksCrossRef(var halaqaID: String, var idTitle: String)