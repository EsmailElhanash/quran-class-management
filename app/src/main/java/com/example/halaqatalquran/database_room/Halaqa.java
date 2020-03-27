package com.example.halaqatalquran.database_room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Halaqa {
    @PrimaryKey(autoGenerate = true)
    public int hid;

    @ColumnInfo(name = "info")
    public String info;

    @ColumnInfo(name = "halaqa_time")
    public Date time;
}
