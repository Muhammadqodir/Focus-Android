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
import uz.mq.focus.adapters.TasksListAdapter

class TodayFragment : Fragment() {
    lateinit var rvAdapter: TasksListAdapter;
    lateinit var rvTodayList:RecyclerView
    lateinit var ivEmpty: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_today, container, false)
        rvTodayList = root.findViewById(R.id.rvToday)
        rvTodayList.layoutManager = LinearLayoutManager(context)
        ivEmpty = root.findViewById(R.id.ivEmpty)
        return root
    }


}