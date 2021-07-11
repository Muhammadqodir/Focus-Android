package uz.mq.focus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.mq.focus.adapters.TasksListAdapter

class TaskListActivity : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        dbHandler = DBHandler(this)
        setActionBar()
        setTitle("TO DO")
        findViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_task_list_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> this.finish()
            R.id.addTask -> showAddTaskDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddTaskDialog(){
        val addTaskDialog = layoutInflater.inflate(R.layout.add_new_task, null)
        val edTitle = addTaskDialog.findViewById<EditText>(R.id.edTitle)
        val spPriority = addTaskDialog.findViewById<Spinner>(R.id.spPriority)
        val spCategory = addTaskDialog.findViewById<Spinner>(R.id.spCategory)
        val dialog = BottomSheetDialog(this)
        addTaskDialog.findViewById<Button>(R.id.btnCreate).setOnClickListener{
            val newTask = TasksListAdapter.Item(edTitle.text.toString(), spPriority.selectedItemPosition, "", spCategory.selectedItemPosition)
            dbHandler.addTask(newTask)
            Toast.makeText(this, "Added!", Toast.LENGTH_LONG).show();
            rvAdapter.addItem(newTask)
            Log.e("List size", rvAdapter.itemCount.toString())
            dialog.dismiss()

        }
        dialog.setContentView(addTaskDialog)
        dialog.show()
    }
    lateinit var rvAdapter: TasksListAdapter
    private fun findViews(){
        val rvTodayList: RecyclerView = findViewById(R.id.rvTaskList)
        rvAdapter = TasksListAdapter(dbHandler.getTasksList(), this)
        rvTodayList.layoutManager = LinearLayoutManager(this)
        rvTodayList.adapter = rvAdapter
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }


    private fun fillList():ArrayList<TasksListAdapter.Item>{
        val data = ArrayList<TasksListAdapter.Item>()
        (0..3).forEach { i->
            run {
                data.add(TasksListAdapter.Item("Title " + i, i, "18.02.2021", i, ""))
            }
        }
        return data
    }
}