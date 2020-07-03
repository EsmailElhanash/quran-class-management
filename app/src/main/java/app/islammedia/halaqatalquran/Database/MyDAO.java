package app.islammedia.halaqatalquran.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.Calendar;
import java.util.List;

@Dao
public interface MyDAO {

    @Query("SELECT * FROM HomeWork WHERE idTitle == :id")
    HomeWork getHomeWork(String id);

    @Insert
    void insertHalaqa(Halaqa... hs);

    @Insert
    void insertStudent(Student... ss);

    @Insert
    void insertHomework(HomeWork... hws);

    //String title , Calendar dueDate, String sura1, String sura2, int ayah1, int ayah2
    @Query("UPDATE HomeWork SET " +
            "hw_title = :title , hw_dueDate = :dueDate, " +
            "hw_sura1 = :sura1 , hw_sura2 = :sura2," +
            " hw_ayah1 = :ayah1 , hw_ayah2 = :ayah2" +
            " WHERE idTitle == :hwID")
    void updateHomeWork(String hwID , String title , Calendar dueDate, String sura1, String sura2, int ayah1, int ayah2);

    @Insert
    void insertStudentsAndHomeWorksCrossRef(StudentsAndHomeWorksCrossRef... StHwCrossRefs);

    @Query("SELECT idTitle FROM StudentsAndHomeWorksCrossRef WHERE idName == :sid")
    List<String> getStudent_HomeWorks(String sid);

    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM Student WHERE idName == :id LIMIT 1")
    Student getStudent(String id);

    @Query("SELECT * FROM Halaqa")
    List<Halaqa> getHalaqat();

    @Query("UPDATE Student SET name = :newName , moreInfo = :newInfo WHERE idName == :sid")
    void updateStudent(String sid , String newName , String newInfo);


    @Transaction
    @Query("SELECT * FROM Student")
    public List<StudentHomeWorks> getStudentHomeWorks();

    @Transaction
    @Query("SELECT * FROM HomeWork")
    public List<HomeWorkAssignedTo> getHomeWorkAssignedStudents();

    @Query("DELETE FROM Student WHERE idName = :stID")
    void deleteStudent(String stID);

}
