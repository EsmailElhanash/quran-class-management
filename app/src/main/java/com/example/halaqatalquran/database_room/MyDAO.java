package com.example.halaqatalquran.database_room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDAO {

    @Query("SELECT * FROM Student WHERE hisHalaqa = :hid")
    List<Student> getThisHalaqaStudents(int hid);


}
