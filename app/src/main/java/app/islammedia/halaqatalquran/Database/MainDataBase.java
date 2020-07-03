package app.islammedia.halaqatalquran.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Student.class , Halaqa.class , HomeWork.class , StudentsAndHomeWorksCrossRef.class}, version = 1 ,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MainDataBase extends RoomDatabase {
    public abstract MyDAO myDAO();
}
