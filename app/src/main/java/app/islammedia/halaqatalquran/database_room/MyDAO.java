package app.islammedia.halaqatalquran.database_room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDAO {

    @Insert
    void insertHalaqa(Halaqa... hs);

    @Insert
    void insertStudent(Student... ss);

    @Insert
    void insertHomework(HomeWork... hws);

    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM Halaqa")
    List<Halaqa> getHalaqat();

}
