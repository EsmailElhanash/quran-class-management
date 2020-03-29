package app.islammedia.halaqatalquran.database_room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Student.class , Halaqa.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MainDataBase extends RoomDatabase {
    public abstract MyDAO myDAO();
}
