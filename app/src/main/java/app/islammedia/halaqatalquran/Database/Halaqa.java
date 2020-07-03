package app.islammedia.halaqatalquran.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Halaqa {
    @PrimaryKey(autoGenerate = true)
    int hid;

    @ColumnInfo(name = "info")
    String info;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "halaqa_time")
    String time;

    public Halaqa(String name, String time, String info){
        this.name=name;
        this.info=info;
        this.time=time;
    }
}
