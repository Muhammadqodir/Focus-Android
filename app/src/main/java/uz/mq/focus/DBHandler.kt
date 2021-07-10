package uz.mq.focus

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import uz.mq.focus.adapters.TasksListAdapter

val DATABASENAME = "DB_FOCUS"
val TASKS_TABLE = "Tasks"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TASKS_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(256), priority INTEGER, deadline VARCHAR(256), category INTEGER, description VARCHAR(256)"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
    }
    fun addTask(user: TasksListAdapter.Item) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("title", user.title)
        contentValues.put("priority", user.priority)
        contentValues.put("deadline", user.deadline)
        contentValues.put("category", user.category)
        contentValues.put("description", user.description)
        val result = database.insert(TASKS_TABLE, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTasksList(): MutableList<TasksListAdapter.Item> {
        val list: MutableList<TasksListAdapter.Item> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TASKS_TABLE"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = TasksListAdapter.Item(result.getString(result.getColumnIndex("title"))
                ,result.getString(result.getColumnIndex("priority")).toInt()
                ,result.getString(result.getColumnIndex("deadline"))
                ,result.getString(result.getColumnIndex("category")).toInt()
                ,result.getString(result.getColumnIndex("description")))
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list
    }
}