package app.islammedia.halaqatalquran.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.islammedia.halaqatalquran.database.helpers.HalaqaDate

@Entity
class Halaqa(@kotlin.jvm.JvmField @ColumnInfo(name = "name") var name: String,
             @kotlin.jvm.JvmField @ColumnInfo(name = "info") var info: String?,
             @kotlin.jvm.JvmField @ColumnInfo(name = "halaqa_dates") var halaqaDates: MutableList<HalaqaDate?>?,
             @kotlin.jvm.JvmField @PrimaryKey var halaqaID: String)