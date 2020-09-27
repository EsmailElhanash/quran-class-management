package app.islammedia.halaqatalquran.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["studentId", "homeWorkID"])
class StudentsAndHomeWorksCrossRef(var studentID: String, var homeWorkID: String)