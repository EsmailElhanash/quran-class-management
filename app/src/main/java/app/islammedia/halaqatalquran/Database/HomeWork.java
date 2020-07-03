package app.islammedia.halaqatalquran.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class HomeWork {

    @NonNull
    public String getIdTitle() {
        return idTitle;
    }

    public void setIdTitle(@NonNull String idTitle) {
        this.idTitle = idTitle;
    }

    @ColumnInfo(name = "idTitle") @PrimaryKey @NonNull
    private String idTitle;

    @ColumnInfo(name = "hw_title")
    String title;

    @ColumnInfo(name = "hw_dueDate")
    Calendar dueDate;

    @ColumnInfo(name = "hw_sura1")
    String sura1;


    @ColumnInfo(name = "hw_sura2")
    String sura2;


    @ColumnInfo(name = "hw_ayah1")
    int ayah1;


    @ColumnInfo(name = "hw_ayah2")
    int ayah2;

    //ArrayList<String> sts_assigned;

    public HomeWork(String title , Calendar dueDate, String sura1, String sura2, int ayah1, int ayah2, @NonNull String idTitle) {
        this.title = title;
        if (title.isEmpty()) {
            this.title = sura1 + ayah1 + "_" + sura2 + ayah2;
        }
        this.dueDate = dueDate;
        this.sura1 = sura1;
        this.sura2 = sura2;
        this.ayah1 = ayah1;
        this.ayah2 = ayah2;
        this.idTitle = idTitle;
    /*;;*/
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public String getSura1() {
        return sura1;
    }

    public void setSura1(String sura1) {
        this.sura1 = sura1;
    }

    public String getSura2() {
        return sura2;
    }

    public void setSura2(String sura2) {
        this.sura2 = sura2;
    }

    public int getAyah1() {
        return ayah1;
    }

    public void setAyah1(int ayah1) {
        this.ayah1 = ayah1;
    }

    public int getAyah2() {
        return ayah2;
    }

    public void setAyah2(int ayah2) {
        this.ayah2 = ayah2;
    }
}
