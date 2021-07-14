package uz.mq.focus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import uz.mq.focus.adapters.TasksListAdapter

class TaskListActivity : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    lateinit var ivEmpty: ImageView
    lateinit var rvTasksList: RecyclerView
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
            val newTask = TasksListAdapter.Item(edTitle.text.toString(), spPriority.selectedItemPosition, "", spCategory.selectedItemPosition, completed = false)
            dbHandler.addTask(newTask)
            Toast.makeText(this, "Added!", Toast.LENGTH_LONG).show();
            fillList()
            Log.e("List size", rvAdapter.itemCount.toString())
            dialog.dismiss()
        }
        dialog.setContentView(addTaskDialog)
        dialog.show()
    }
    lateinit var rvAdapter: TasksListAdapter
    private fun findViews(){
        rvTasksList = findViewById(R.id.rvTaskList)
        ivEmpty = findViewById(R.id.ivEmpty)
        fillList()
    }

    fun fillList(){
        val list = dbHandler.getTasksList()
        if(list.size > 0){
            rvTasksList.visibility = View.VISIBLE
            ivEmpty.visibility = View.GONE
            rvAdapter = TasksListAdapter(dbHandler.getTasksList(), this, dbHandler, ivEmpty,true)
            rvTasksList.layoutManager = LinearLayoutManager(this)
            rvTasksList.adapter = rvAdapter
        }else{
            rvTasksList.visibility = View.GONE
            ivEmpty.visibility = View.VISIBLE
        }
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

}