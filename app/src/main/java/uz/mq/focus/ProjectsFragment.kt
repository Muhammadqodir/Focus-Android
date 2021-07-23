package uz.mq.focus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import uz.mq.focus.adapters.TasksListAdapter

class TasksFragment : Fragment() {
    lateinit var rvAdapter: TasksListAdapter;
    lateinit var rvProjectsList:RecyclerView
    lateinit var ivEmpty: ImageView
    val database = Firebase.database
    lateinit var userName: String
    val TAG:String = "ProjectsFragment"
    val usersRef = database.getReference("Users")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_projects, container, false)
        rvProjectsList = root.findViewById(R.id.tvPriority)
        rvProjectsList.layoutManager = LinearLayoutManager(context)
        ivEmpty = root.findViewById(R.id.ivEmpty)
        return root
    }


}