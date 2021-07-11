package uz.mq.focus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.mq.focus.adapters.TasksListAdapter

class TodayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_today, container, false)
        val rvTodayList:RecyclerView = root.findViewById(R.id.rvToday)
        val rvAdapter:TasksListAdapter? = context?.let { TasksListAdapter(fillList(), it) }
        rvTodayList.layoutManager = LinearLayoutManager(context)
        rvTodayList.adapter = rvAdapter
        return root
    }

    private fun fillList():ArrayList<TasksListAdapter.Item>{
        val data = ArrayList<TasksListAdapter.Item>()
        (0..3).forEach { i->
            run {
                data.add(TasksListAdapter.Item("Title " + i, i, "18.02.2021", i, "", completed = false))
            }
        }
        return data
    }
}