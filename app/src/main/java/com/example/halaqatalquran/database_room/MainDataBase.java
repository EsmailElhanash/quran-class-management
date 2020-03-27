package com.example.halaqatalquran.database_room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Student.class , Halaqa.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MainDataBase extends RoomDatabase {

}
