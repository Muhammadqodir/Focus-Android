package uz.mq.focus

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class Utils{
    public val categorys = arrayOf("Work", "Education", "Entertainment")
    public val categorysIcon = arrayOf(R.drawable.ic_c_work, R.drawable.ic_c_education, R.drawable.ic_c_entertainment)
    public val categorysColor = arrayOf(R.color.colorWork, R.color.colorEducation, R.color.colorEntertainment)
    public val prioritysColors = arrayOf(R.color.level3, R.color.level2, R.color.level1, R.color.level0)

    public val prioritys = arrayOf("Important & Urgent", "Important & !Urgent", "!Important & Urgent", "!Important & !Urgent")

    public fun getToDayDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(Date())
    }

    public fun getTomorrowDate():String{
        val calendar = Calendar.getInstance()
        val today = calendar.time
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = calendar.time
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(tomorrow)
    }

    public fun getUserName(context: Context):String{
        return "MQ"
    }
}