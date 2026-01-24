package app.islammedia.halaqatalquran.database

import android.content.Context
import androidx.room.Room
import app.islammedia.halaqatalquran.database.entities.Halaqa
import app.islammedia.halaqatalquran.database.entities.HomeWork
import app.islammedia.halaqatalquran.database.entities.Student
import app.islammedia.halaqatalquran.database.entities.StudentsAndHomeWorksCrossRef

class MainDataBaseManager private constructor(){

    companion object {
        fun getMainDataBaseInstance(c:Context) : MainDataBase{
            return Room.databaseBuilder(c, MainDataBase::class.java,"MainDataBase").build()
        }

        suspend fun addStudent(c: Context, student: Student, vararg homeWorks : HomeWork?) {
            val db = getMainDataBaseInstance(c)
            db.myDAO().insertStudent(student)
            for (h in homeWorks) {
                db.myDAO().insertHomework(h)
                db.myDAO().insertStudentsAndHomeWorksCrossRef(StudentsAndHomeWorksCrossRef(student.getStudentId(), h!!.getHomeWorkID()))
            }
        }

    }

    fun addTestElements(c:Context){
        val db : MainDataBase = getMainDataBaseInstance(c)
        val r: Runnable =  Runnable {
            for (i in 1..10) {
                db.myDAO().insertStudent(Student(
                        "sn$i", "si$i", "sid$i"))

            db.myDAO().insertHalaqa(Halaqa(
                    "hn$i", "hi$i", null , "hid$i"))
            }
        }
        Thread(r).start()
    }

}