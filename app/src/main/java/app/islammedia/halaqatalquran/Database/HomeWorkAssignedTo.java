package app.islammedia.halaqatalquran.Database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

class HomeWorkAssignedTo{

    @Embedded
    public HomeWork hw;
    @Relation(
            parentColumn = "idTitle",
            entityColumn = "idName",
            associateBy = @Junction(StudentsAndHomeWorksCrossRef.class)
    )
    public List<Student> st;

}
