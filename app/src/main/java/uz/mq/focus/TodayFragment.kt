package uz.mq.focus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import uz.mq.focus.adapters.TasksListAdapter

class TodayFragment : Fragment() {
    lateinit var rvAdapter: TasksListAdapter;
    lateinit var rvTodayList:RecyclerView
    lateinit var ivEmpty: ImageView
    val database = Firebase.database
    lateinit var userName: String
    var usersRef = database.getReference("Users")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_today, container, false)
        rvTodayList = root.findViewById(R.id.rvToday)
        rvTodayList.layoutManager = LinearLayoutManager(context)
        ivEmpty = root.findViewById(R.id.ivEmpty)
        userName = Utils().getUserName(requireActivity().applicationContext)
        usersRef = DBHelper().getUserRef(database, userName)
        fillList()
        return root
    }


    fun fillList(){
        DBHelper().getUserRef(database, userName).child("Tasks").child("Today").addValueEventListener(object:
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val tasks:ArrayList<Models.TaskItem> = ArrayList()
                for (data: DataSnapshot in snapshot.children){
                    tasks.add(data.getValue(Models.TaskItem::class.java)!!)
                }
                if(tasks.size > 0){
                    ivEmpty.visibility = View.GONE
                    rvTodayList.visibility = View.VISIBLE
                    rvAdapter =  TasksListAdapter(tasks, requireActivity(), usersRef.child("Tasks"), "ToDo")
                    rvTodayList.adapter = rvAdapter
                }else{
                    ivEmpty.visibility = View.VISIBLE
                    rvTodayList.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TodayFragment", "Failed to read value.", error.toException())
            }

        })
    }


}