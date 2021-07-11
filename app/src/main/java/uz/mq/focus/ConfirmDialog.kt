package uz.mq.focus

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ConfirmDialog(context: Context, inflater: LayoutInflater, action: () -> Unit) {

    var dialog:BottomSheetDialog? = null

    init{
        val dialogRoot = inflater.inflate(R.layout.task_actions_dialog, null)
        dialog = BottomSheetDialog(context)
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnCancel).setOnClickListener{
            dialog?.dismiss()
        }
        dialogRoot.findViewById<FloatingActionButton>(R.id.btnCompleted).setOnClickListener{
            action()
        }
        dialog?.setContentView(dialogRoot)
    }

    public fun show(){
        if (dialog != null){
            dialog?.show()
        }
    }

}