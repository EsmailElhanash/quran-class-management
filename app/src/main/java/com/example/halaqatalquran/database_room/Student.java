package com.example.halaqatalquran.database_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    public int sid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "birthDate")
    public Date birthDate;

    @ColumnInfo(name = "hisHalaqa")
    public int shid;
}
