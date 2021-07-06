package uz.mq.focus.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.mq.focus.R
import uz.mq.focus.Utils

class TasksListAdapter(private val items: List<Item>, val context: Context):
        RecyclerView.Adapter<TasksListAdapter.MyViewHolder>(){

    class Item(val title:String, val priority:Int, val deadline:String, val category:Int, val description:String="")

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvTitle: TextView? = null
        var tvPriority: TextView? = null
        var tvDeadline: TextView? = null
        var ivCategory: ImageView? = null

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvPriority = itemView.findViewById(R.id.tvPriority)
            tvDeadline = itemView.findViewById(R.id.tvDeadline)
            ivCategory = itemView.findViewById(R.id.categoryIcon)
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
            holder.tvPriority?.text = Utils().categorys[item.priority]
            holder.tvPriority?.setBackgroundResource(R.drawable.text_bg)
            holder.tvPriority?.background?.setColorFilter(Color.parseColor("#343434"),PorterDuff.Mode.SRC_OVER)
        }
        if (item.category in 0..2){
            holder.ivCategory?.setImageDrawable(context.resources.getDrawable(Utils().categorysIcon[item.category]))
        }
        holder.ivCategory
    }

    override fun getItemCount(): Int {
        return items.size
    }

}