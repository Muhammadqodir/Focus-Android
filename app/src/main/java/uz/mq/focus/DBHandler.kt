package uz.mq.focus

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import uz.mq.focus.adapters.TasksListAdapter

val DATABASENAME = "DB_FOCUS"
val TASKS_TABLE = "Tasks"

class DBHandler(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE IF NOT EXISTS " + TASKS_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(256), priority INTEGER, deadline VARCHAR(256), category INTEGER, tododate VARCHAR(256), completed BOOLEAN, description VARCHAR(256))"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onCreate(db);
    }
    fun addTask(user: TasksListAdapter.Item) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("title", user.title)
        contentValues.put("priority", user.priority)
        contentValues.put("deadline", user.deadline)
        contentValues.put("category", user.category)
        contentValues.put("tododate", user.tododate)
        contentValues.put("completed", user.completed)
        contentValues.put("description", user.description)
        val result = database.insert(TASKS_TABLE, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTasksList(): ArrayList<TasksListAdapter.Item> {
        val list: ArrayList<TasksListAdapter.Item> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TASKS_TABLE WHERE completed = 0 AND tododate = 'undefined' ORDER BY id DESC"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = TasksListAdapter.Item(result.getString(result.getColumnIndex("title"))
                    , result.getString(result.getColumnIndex("priority")).toInt()
                    , result.getString(result.getColumnIndex("deadline"))
                    , result.getString(result.getColumnIndex("category")).toInt()
                    , result.getString(result.getColumnIndex("tododate"))
                    , result.getString(result.getColumnIndex("completed"))!!.toBoolean()
                    , result.getString(result.getColumnIndex("description"))
                    , id=result.getString(result.getColumnIndex("id")).toInt())
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun getTodayTasks(): ArrayList<TasksListAdapter.Item> {
        val list: ArrayList<TasksListAdapter.Item> = ArrayList()
        val db = this.readableDatabase
        val today = Utils().getToDayDate()
        val query = "Select * from $TASKS_TABLE WHERE tododate = '$today' and completed = 0"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = TasksListAdapter.Item(result.getString(result.getColumnIndex("title"))
                    , result.getString(result.getColumnIndex("priority")).toInt()
                    , result.getString(result.getColumnIndex("deadline"))
                    , result.getString(result.getColumnIndex("category")).toInt()
                    , result.getString(result.getColumnIndex("tododate"))
                    , result.getString(result.getColumnIndex("completed"))!!.toBoolean()
                    , result.getString(result.getColumnIndex("description"))
                    , id=result.getString(result.getColumnIndex("id")).toInt())
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list
    }

    fun removeTask(taskId:Int){
        if(taskId >= 0){
            val db = this.readableDatabase
            db.delete(TASKS_TABLE, "id = $taskId", null)
        }
    }

    fun planTask(taskId: Int, date: String){
        if (taskId >= 0){
            val db = this.readableDatabase
            val query = "UPDATE $TASKS_TABLE SET tododate = '$date' WHERE id = $taskId"
            db.execSQL(query)
        }
    }

    fun completeTask(taskId: Int){
        if (taskId >= 0){
            val db = this.readableDatabase
            val today = Utils().getToDayDate()
            val query = "UPDATE $TASKS_TABLE SET tododate = '$today', completed = 1 WHERE id = $taskId"
            db.execSQL(query)
        }
    }
}