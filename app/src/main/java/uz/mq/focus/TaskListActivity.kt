package uz.mq.focus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.mq.focus.adapters.TasksListAdapter

class TaskListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
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
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(addTaskDialog)
        dialog.show()
    }

    private fun findViews(){
        val rvTodayList: RecyclerView = findViewById(R.id.rvTaskList)
        val rvAdapter:TasksListAdapter? = TasksListAdapter(fillList(), this)
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