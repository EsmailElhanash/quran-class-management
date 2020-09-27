package app.islammedia.halaqatalquran.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.islammedia.halaqatalquran.database.entities.*

@Database(entities = [Student::class, Halaqa::class, HomeWork::class, StudentsAndHomeWorksCrossRef::class, HalaqatAndHomeWorksCrossRef::class, HalaqatAndStudentsCrossRef::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MainDataBase : RoomDatabase() {
    abstract fun myDAO(): MyDAO
}