package app.islammedia.halaqatalquran.student.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.islammedia.halaqatalquran.database.entities.Student

class StudentViewModel : ViewModel() {
    var student : MutableLiveData<Student> = MutableLiveData()

    fun getStudent(studentID : String){
        
    }

}