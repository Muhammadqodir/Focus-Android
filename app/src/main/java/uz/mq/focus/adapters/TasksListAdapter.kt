package uz.mq.focus.adapters

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.mq.focus.R
import uz.mq.focus.Utils

class TasksListAdapter(private val items: ArrayList<Item>, val context: Context):
        RecyclerView.Adapter<TasksListAdapter.MyViewHolder>(){

    class Item(val title:String, val priority:Int, val deadline:String, val category:Int, val tododate:String = "undefined", val completed:Boolean, val description:String="")

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvTitle: TextView? = null
        var tvPriority: TextView? = null
        var tvDeadline: TextView? = null
        var ivCategory: ImageView? = null
        var llParent: LinearLayout? = null

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvPriority = itemView.findViewById(R.id.tvPriority)
            tvDeadline = itemView.findViewById(R.id.tvDeadline)
            ivCategory = itemView.findViewById(R.id.categoryIcon)
            llParent = itemView.findViewById(R.id.parent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {val itemView =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item:Item = items[position]
        holder.tvTitle?.text = item.title
        holder.tvDeadline?.text = item.deadline
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
            showActionDialog(position)
        }
    }

    private fun showActionDialog(index:Int){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogRoot = inflater.inflate(R.layout.task_actions_dialog, null)
        val dialog = BottomSheetDialog(context)
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnRemove).setOnClickListener{
            //Remove Task
        }
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnTomorrow).setOnClickListener{
            //Do tomorrow
        }
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnCompleted).setOnClickListener{
            //Completed
        }
        dialog.setContentView(dialogRoot)
        dialog.show()
    }

    public fun addItem(item:TasksListAdapter.Item){
        items.add(0, item)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}