package app.islammedia.halaqatalquran.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Student {
    @NonNull
    public String getIdName() {
        return idName;
    }

    public void setIdName(@NonNull String idName) {
        this.idName = idName;
    }

    @ColumnInfo(name = "idName") @PrimaryKey @NonNull
    private String idName;

    @ColumnInfo(name = "name") @NonNull
    private String name;

    @ColumnInfo(name = "moreInfo")
    private String info;





    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
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

    @ColumnInfo(name = "hisHalaqa")
    private int halaqaID;



    public Student(@NonNull String name, String info, @NonNull String idName) {
        this.name = name;
        this.info = info;

        this.idName = idName;
    /*;*/
    }
}
