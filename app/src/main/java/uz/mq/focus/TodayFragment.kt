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
import uz.mq.focus.adapters.TasksListAdapter

class TodayFragment : Fragment() {
    lateinit var rvAdapter: TasksListAdapter;
    lateinit var rvTodayList:RecyclerView
    lateinit var dbHandler: DBHandler
    lateinit var ivEmpty: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_today, container, false)
        rvTodayList = root.findViewById(R.id.rvToday)
        rvTodayList.layoutManager = LinearLayoutManager(context)
        ivEmpty = root.findViewById(R.id.ivEmpty)
        dbHandler = context?.let { DBHandler(it) }!!
        return root
    }

    fun fillList(){
        val todayTasks:ArrayList<TasksListAdapter.Item> = dbHandler.getTodayTasks()
        if(todayTasks.size > 0){
            ivEmpty.visibility = View.GONE
            rvTodayList.visibility = View.VISIBLE
            rvAdapter = context?.let { TasksListAdapter(todayTasks, it, dbHandler, ivEmpty) }!!
            rvTodayList.adapter = rvAdapter
        }else{
            ivEmpty.visibility = View.VISIBLE
            rvTodayList.visibility = View.GONE
        }
    }

    override fun onResume() {
        fillList()
        super.onResume()
    }

}