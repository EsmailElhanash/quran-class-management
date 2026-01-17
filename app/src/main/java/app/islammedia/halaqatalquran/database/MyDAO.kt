package app.islammedia.halaqatalquran.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import app.islammedia.halaqatalquran.database.entities.*
import app.islammedia.halaqatalquran.database.helpers.HalaqaDate
import app.islammedia.halaqatalquran.database.helpers.HomeWorkAssignedToStudent
import app.islammedia.halaqatalquran.database.helpers.StudentHomeWorks
import java.util.*

@Dao
interface MyDAO {
    ////////////////////////////////////////////////////
    /* INSERT Operations*/
    @Insert
    fun insertHalaqa(vararg hs: Halaqa?)
    @Insert
    fun insertStudent(vararg ss: Student?)
    @Insert
    fun insertHomework(vararg hws: HomeWork?)
    @Insert
    fun insertStudentsAndHomeWorksCrossRef(vararg StHwCrossRefs: StudentsAndHomeWorksCrossRef?)
    @Insert
    fun insertHalaqaAndHomeWorksCrossRef(vararg hhCrossRef: HalaqatAndHomeWorksCrossRef?)
    @Insert
    fun insertHalaqaAndStudentsCrossRef(vararg hhCrossRef: HalaqatAndStudentsCrossRef?)

    ////////////////////////////////////////////////////
    /* SELECT (GET) Operations*/
    @Query("SELECT homeWorkID FROM StudentsAndHomeWorksCrossRef WHERE studentID == :sid")
    fun getStudentHomeWorks(sid: String?): MutableList<String?>?
    @Query("SELECT idTitle FROM HalaqatAndHomeWorksCrossRef WHERE halaqaID == :halaqaID")
    fun getHalaqaHomeWorks(halaqaID: String?): MutableList<String?>?
    @Query("SELECT idName FROM HalaqatAndStudentsCrossRef WHERE halaqaID == :halaqaID")
    fun getHalaqaStudents(halaqaID: String?): MutableList<String?>?
    @Query("SELECT halaqaID FROM HalaqatAndStudentsCrossRef WHERE idName == :idName")
    fun getStudentHalaqat(idName: String?): MutableList<String?>?
    @Query("SELECT * FROM Student WHERE idName == :id LIMIT 1")
    fun getStudent(id: String?): Student?
    @Query("SELECT * FROM Student")
    fun getAllStudents(): MutableList<Student?>?
    @Query("SELECT * FROM Halaqa WHERE halaqaID == :halaqaID LIMIT 1")
    fun getHalaqa(halaqaID: String?): Halaqa?
    @Query("SELECT * FROM Halaqa")
    fun getHalaqat(): MutableList<Halaqa?>?
    @Query("SELECT * FROM HomeWork WHERE homeWorkID == :idTitle LIMIT 1")
    fun getHomeWork(idTitle: String?): HomeWork?

    ////////////////////////////////////////////////////
    /* UPDATE Operations*/
    @Query("UPDATE Student SET name = :newName , moreInfo = :newInfo WHERE idName == :sid")
    fun updateStudent(sid: String?, newName: String?, newInfo: String?)
    @Query("UPDATE Halaqa SET name = :newName , info = :newInfo ,  halaqa_dates =:newDates WHERE halaqaID == :halaqaID")
    fun updateHalaqa(halaqaID: String?, newName: String?, newInfo: String?, newDates: MutableList<HalaqaDate?>?)

    @Query("UPDATE HomeWork SET " +
            "hw_title = :title , hw_dueDate = :dueDate, " +
            "hw_sura1 = :sura1 , hw_sura2 = :sura2," +
            " hw_ayah1 = :ayah1 , hw_ayah2 = :ayah2" +
            " WHERE idTitle == :hwID ")
    fun updateHomeWork(hwID: String?, title: String?, dueDate: Calendar?, sura1: String?, sura2: String?, ayah1: Int, ayah2: Int)
    @Query("UPDATE HomeWork SET rating = :rating WHERE homeWorkID == :idTitle ")
    fun setHomeWorkRating(rating: Float, idTitle: String?)

    ////////////////////////////////////////////////////
    /* Transaction Select Operations*/
    @Transaction
    @Query("SELECT * FROM Student")
    fun getStudentHomeWorks(): MutableList<StudentHomeWorks?>?
    @Transaction
    @Query("SELECT * FROM HomeWork")
    fun getHomeWorkAssignedStudents(): MutableList<HomeWorkAssignedToStudent?>?

    ////////////////////////////////////////////////////
    /*DELETE OPERATIONS*/
    @Query("DELETE FROM Student WHERE idName = :stID")
    fun deleteStudent(stID: String?)
    @Query("DELETE FROM Halaqa WHERE halaqaID = :halaqaID")
    fun deleteHalaqa(halaqaID: String?)
    @Query("DELETE FROM HalaqatAndStudentsCrossRef WHERE idName = :idName")
    fun deleteStudentFromHalaqa(idName: String?)
    @Query("DELETE FROM HalaqatAndHomeWorksCrossRef WHERE (halaqaID = :halaqaID) AND (idTitle = :idTitle)")
    fun deleteHalaqaHomeworkCrossRef(halaqaID: String?, idTitle: String?)
    @Query("DELETE FROM StudentsAndHomeWorksCrossRef WHERE (studentID = :idName) AND (homeWorkID = :idTitle)")
    fun deleteStudentHomeworkCrossRef(idName: String?, idTitle: String?)
}