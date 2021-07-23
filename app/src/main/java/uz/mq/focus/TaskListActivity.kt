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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import uz.mq.focus.adapters.TasksListAdapter

class TaskListActivity : AppCompatActivity() {
    lateinit var ivEmpty: ImageView
    lateinit var rvTasksList: RecyclerView
    val database = Firebase.database
    lateinit var userName: String
    val TAG:String = "ProjectsFragment"
    val usersRef = database.getReference("Users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setActionBar()
        setTitle("TO DO")
        userName = Utils().getUserName(this)
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
            val ref = DBHelper().getToDoListRef(database, userName);
            val key:String = ref.push().key!!
            val newTask = Models.TaskItem(key, edTitle.text.toString(), "", spPriority.selectedItemPosition,  spCategory.selectedItemPosition, "false")
            ref.child(key).setValue(newTask)
            Toast.makeText(this, "Added!", Toast.LENGTH_LONG).show();
            dialog.dismiss()
        }
        dialog.setContentView(addTaskDialog)
        dialog.show()
    }
    lateinit var rvAdapter: TasksListAdapter
    private fun findViews(){
        rvTasksList = findViewById(R.id.rvTaskList)
        rvTasksList.layoutManager = LinearLayoutManager(this)
        ivEmpty = findViewById(R.id.ivEmpty)
        fillList()
    }

    fun fillList(){
        DBHelper().getToDoListRef(database, userName).addValueEventListener(object:
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks:ArrayList<Models.TaskItem> = ArrayList()
                for (data: DataSnapshot in snapshot.children){
                    tasks.add(data.getValue(Models.TaskItem::class.java)!!)
                }
                if(tasks.size > 0){
                    ivEmpty.visibility = View.GONE
                    rvTasksList.visibility = View.VISIBLE
                    rvAdapter =  TasksListAdapter(tasks, this@TaskListActivity)
                    rvTasksList.adapter = rvAdapter
                }else{
                    ivEmpty.visibility = View.VISIBLE
                    rvTasksList.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

}