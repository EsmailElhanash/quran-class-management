package app.islammedia.halaqatalquran.Database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"idName", "idTitle"})
public class StudentsAndHomeWorksCrossRef {
        @NonNull String idName;
        @NonNull String idTitle;

        public StudentsAndHomeWorksCrossRef(String idName, String idTitle) {
                this.idName = idName;
                this.idTitle = idTitle;
        }
}