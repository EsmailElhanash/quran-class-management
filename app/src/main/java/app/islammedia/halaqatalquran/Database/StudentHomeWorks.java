package app.islammedia.halaqatalquran.Database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

class StudentHomeWorks{

    @Embedded public Student st;
    @Relation(
            parentColumn = "idName",
            entityColumn = "idTitle",
            associateBy = @Junction(StudentsAndHomeWorksCrossRef.class)
    )
    public List<HomeWork> hws;

}

