package app.islammedia.halaqatalquran.database_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    int sid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "moreInfo")
    String info;

    @ColumnInfo(name = "homework")
    String hws;

    @ColumnInfo(name = "birthDate")
    Date birthDate;

    @ColumnInfo(name = "hisHalaqa")
    int shid;

    public Student(String name, String info, String hws) {
        this.name = name;
        this.info = info;
        this.hws = hws;
    }
}
