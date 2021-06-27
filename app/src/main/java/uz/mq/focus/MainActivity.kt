package uz.mq.focus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar
import java.security.AccessControlContext

class MainActivity : AppCompatActivity() {
    lateinit var bottomBar: SmoothBottomBar
    lateinit var context: Context
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        setActionBar()
        initViews()
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar);
    }

    private fun initViews(){
        navController = findNavController(R.id.fragment)
        bottomBar = findViewById(R.id.bottomBar)
        bottomBar.setOnItemSelectedListener {
            when(it){
                0 -> navController.navigate(R.id.todayFragment)
                1 -> navController.navigate(R.id.tasksFragment)
                2 -> navController.navigate(R.id.walletFragment)
            }
        }
    }

}