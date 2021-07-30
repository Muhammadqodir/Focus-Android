package uz.mq.focus.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import uz.mq.focus.*

class TasksListAdapter(private val items: ArrayList<Models.TaskItem>, val context: Context, val tasksRef: DatabaseReference, val currentBranch: String, val isToDoList: Boolean = false):
        RecyclerView.Adapter<TasksListAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvTitle: TextView? = null
        var tvPriority: TextView? = null
        var tvDeadline: TextView? = null
        var ivCategory: ImageView? = null
        var llParent: LinearLayout? = null
        var btnAction: ImageView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvPriority = itemView.findViewById(R.id.tvPriority)
            tvDeadline = itemView.findViewById(R.id.tvDeadline)
            ivCategory = itemView.findViewById(R.id.categoryIcon)
            llParent = itemView.findViewById(R.id.parent)
            btnAction = itemView.findViewById(R.id.btnCheck)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {val itemView =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item:Models.TaskItem = items[position]
        holder.tvTitle?.text = item.title
        if (item.priority in 0..3){
            holder.tvPriority?.text = Utils().prioritys[item.priority]
            holder.tvPriority?.setBackgroundResource(R.drawable.text_bg)
            holder.tvPriority?.background?.setColorFilter(context.resources.getColor(Utils().prioritysColors[item.priority]),PorterDuff.Mode.SRC_OVER)
        }else{
            holder.tvPriority?.text = Utils().prioritys[0]
            holder.tvPriority?.setBackgroundResource(R.drawable.text_bg)
            holder.tvPriority?.background?.setColorFilter(context.resources.getColor(Utils().prioritysColors[0]),PorterDuff.Mode.SRC_OVER)
        }
        if (item.category in 0..2){
            holder.ivCategory?.setBackgroundResource(R.drawable.circle)
            holder.ivCategory?.background?.setColorFilter(context.resources.getColor(Utils().categorysColor[item.category]),PorterDuff.Mode.SRC_OVER)
            holder.ivCategory?.setImageDrawable(context.resources.getDrawable(Utils().categorysIcon[item.category]))
        }else{
            holder.ivCategory?.setBackgroundResource(R.drawable.circle)
            holder.ivCategory?.background?.setColorFilter(context.resources.getColor(Utils().categorysColor[0]),PorterDuff.Mode.SRC_OVER)
            holder.ivCategory?.setImageDrawable(context.resources.getDrawable(Utils().categorysIcon[0]))
        }
        holder.llParent?.setOnClickListener {
            showActionDialog(item)
        }
        holder.llParent?.setOnLongClickListener{
            Toast.makeText(context, position.toString(), Toast.LENGTH_LONG).show()
            return@setOnLongClickListener true
        }
        if (isToDoList){
            holder.btnAction?.setImageResource(R.drawable.ic_tomorrow)
            holder.btnAction?.setOnClickListener{
                holder.btnAction?.setOnClickListener{
                    ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                        tasksRef.child("ToDo").child(item.key).removeValue()
                        tasksRef.child("Today").child(item.key).setValue(item)
                        Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                    }.show()
                }
            }
        }else{
            holder.btnAction?.setOnClickListener{
                ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                    tasksRef.child("Today").child(item.key).removeValue()
                    tasksRef.child("Completed").child(item.key).setValue(item)
                    Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                }.show()
            }
        }
    }

    private fun showActionDialog(task: Models.TaskItem){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogRoot = inflater.inflate(R.layout.task_actions_dialog, null)
        val dialog = BottomSheetDialog(context)
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnRemove).setOnClickListener{
            dialog.dismiss()
            ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                tasksRef.child(currentBranch).child(task.key).removeValue()
                Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
            }.show()
        }
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnTomorrow).setOnClickListener{
            dialog.dismiss()
            var tododate = Utils().getToDayDate()
            if (isToDoList){
                ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                    tasksRef.child("ToDo").child(task.key).removeValue()
                    tasksRef.child("Today").child(task.key).setValue(task)
                    Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                }.show()
            }else{
                ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                    tasksRef.child("Today").child(task.key).removeValue()
                    tasksRef.child("ToDo").child(task.key).setValue(task)
                    Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                }.show()
            }
        }
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnCompleted).setOnClickListener{
            dialog.dismiss()
            if (isToDoList){
                ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                    tasksRef.child("ToDo").child(task.key).removeValue()
                    tasksRef.child("Completed").child(task.key).setValue(task)
                    Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                }.show()
            }else{
                ConfirmDialog(context, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater) {
                    tasksRef.child("Today").child(task.key).removeValue()
                    tasksRef.child("Completed").child(task.key).setValue(task)
                    Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                }.show()
            }
        }
        dialog.setContentView(dialogRoot)
        dialog.show()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}