package uz.mq.focus

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
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
    lateinit var addTask: MenuItem
    lateinit var addWallet: MenuItem
    lateinit var taskList: MenuItem
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_icons8_settings)
    }

    private fun initViews(){
        navController = findNavController(R.id.fragment)
        bottomBar = findViewById(R.id.bottomBar)
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.menu_bottom)
        val menu = popupMenu.menu
        bottomBar.setupWithNavController(menu, navController)
//        bottomBar.setOnItemSelectedListener {
//            showFragment(it)
//        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_actionbar, menu)
        addTask = menu!!.findItem(R.id.addTask)
        addTask.setVisible(false)
        addWallet = menu!!.findItem(R.id.addToWallet)
        addWallet.setVisible(false)
        taskList = menu!!.findItem(R.id.tasksList)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tasksList -> {
                startActivity(Intent(context, TaskListActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}