package app.islammedia.halaqatalquran.database_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.List;

@Entity
public class HomeWork {
    @PrimaryKey(autoGenerate = true)
    int hwid;

    @ColumnInfo(name = "hw_title")
    String title;

    @ColumnInfo(name = "hw_dueDate")
    Calendar dueDate;

    @ColumnInfo(name = "hw_sura1")
    int sura1;


    @ColumnInfo(name = "hw_sura2")
    int sura2;


    @ColumnInfo(name = "hw_ayah1")
    int ayah1;


    @ColumnInfo(name = "hw_ayah2")
    int ayah2;

    List<Integer> sts_assigned;

    public HomeWork(int hwid, String title, Calendar dueDate, int sura1, int sura2, int ayah1, int ayah2, List<Integer> sts_assigned) {
        this.hwid = hwid;
        this.title = title;
        this.dueDate = dueDate;
        this.sura1 = sura1;
        this.sura2 = sura2;
        this.ayah1 = ayah1;
        this.ayah2 = ayah2;
        this.sts_assigned = sts_assigned;
    }
}
