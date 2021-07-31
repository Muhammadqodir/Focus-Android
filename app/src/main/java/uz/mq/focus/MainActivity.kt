package uz.mq.focus

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import me.ibrahimsn.lib.SmoothBottomBar


class MainActivity : AppCompatActivity() {
    lateinit var bottomBar: SmoothBottomBar
    lateinit var context: Context
    lateinit var navController: NavController
    var newProject: MenuItem? = null
    var addWallet: MenuItem? = null
    var taskList: MenuItem? = null
    lateinit var ivLogo: ImageView
    val database = Firebase.database
    lateinit var progressDialog: ProgressDialog
    lateinit var userName: String
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var storageRef: StorageReference = storage.getReference()
    var usersRef = database.getReference("Users")
    lateinit var fileUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        userName = Utils().getUserName(this)
        usersRef = DBHelper().getUserRef(database, userName)
        setActionBar()
        initViews()
        checkPermissions()
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
        navController.addOnDestinationChangedListener(listener)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Focus")
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)
    }
    val listener = NavController.OnDestinationChangedListener{controller, destination, arguments ->
        when(destination.id){
            R.id.todayFragment -> {
                newProject?.setVisible(false)
                addWallet?.setVisible(false)
                taskList?.setVisible(true)
            }
            R.id.projectsFragment -> {
                newProject?.setVisible(true)
                addWallet?.setVisible(false)
                taskList?.setVisible(false)
            }
            R.id.walletFragment -> {
                newProject?.setVisible(false)
                addWallet?.setVisible(true)
                taskList?.setVisible(false)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_actionbar, menu)
        newProject = menu!!.findItem(R.id.newProject)
        newProject?.setVisible(false)
        addWallet = menu!!.findItem(R.id.addToWallet)
        addWallet?.setVisible(false)
        taskList = menu!!.findItem(R.id.tasksList)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.tasksList -> {
                startActivity(Intent(context, TaskListActivity::class.java))
            }
            R.id.newProject -> {
                val root = layoutInflater.inflate(R.layout.add_new_project_dialog, null)
                val dialog = BottomSheetDialog(this)
                ivLogo = root.findViewById<ImageView>(R.id.ivLogo)

                ivLogo.setOnClickListener{
                    ImagePicker.with(this)
                        .crop()
                        .maxResultSize(512, 512)
                        .start()
                }

                (root.findViewById<Button>(R.id.btnCreate)).setOnClickListener{
                    val projectsRef = usersRef.child("Projects")
                    val name = (root.findViewById<EditText>(R.id.edTitle)).text.toString()
                    val income = (root.findViewById<EditText>(R.id.edIncome)).text.toString()
                    val desc = (root.findViewById<EditText>(R.id.edDesc)).text.toString()
                    val key = projectsRef.push().key.toString()
                    val newProject:Models.ProjectsItem = Models.ProjectsItem(key, name, desc, "", income, 0)
                    projectsRef.child(key).setValue(newProject)
                    progressDialog.show()
                    storageRef.child("logos").child(key+".jpg").putFile(fileUri)
                        .addOnCompleteListener{
                            progressDialog.dismiss()
                            dialog.dismiss()
                        }
                }

                dialog.setContentView(root)
                dialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            fileUri = uri
            ivLogo.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkPermissions(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}