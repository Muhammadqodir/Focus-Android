package uz.mq.focus

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DBHelper{
    public fun getToDoListRef(db: FirebaseDatabase, userName: String):DatabaseReference{
        return db.getReference("Users").child(userName).child("Tasks").child("ToDo")
    }
    public fun getUserRef(db: FirebaseDatabase, userName: String):DatabaseReference{
        return db.getReference("Users").child(userName)
    }
}