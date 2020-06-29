package app.islammedia.halaqatalquran.database_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int sid;

    public int getSid() {
        return sid;
    }

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "moreInfo")
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getHalaqaID() {
        return halaqaID;
    }

    public void setHalaqaID(int halaqaID) {
        this.halaqaID = halaqaID;
    }

    public List<HomeWork> getHws() {
        return hws;
    }

    public void setHws(List<HomeWork> hws) {
        this.hws = hws;
    }

    @ColumnInfo(name = "hisHalaqa")
    private int halaqaID;

    @ColumnInfo(name = "HWS")
    private List<HomeWork> hws = new ArrayList<>();

    public Student(String name, String info) {
        this.name = name;
        this.info = info;
    }
}
