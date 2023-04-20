package com.food.schrood.utility

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.food.schrood.databinding.PopUpCustomProgressBinding


/**
 * Created by Choudhary 9166900279 on 28/03/2023.
 */
class DialogManager(private val context: Context) {
    private var aDProgress: AlertDialog? = null
    fun showDialog() {
        if (aDProgress != null) {
            aDProgress?.show()
        }
    }


    fun showProgressDialog(progress: String?) {
        val builder = AlertDialog.Builder(context)
        val customProgressBinding = PopUpCustomProgressBinding.inflate(
            LayoutInflater.from(
                context
            ), null, false
        )

        /*  val view = LayoutInflater.from(context).inflate(R.layout.pop_up_custom_progress, null)*/
        builder.setCancelable(false)
        builder.setView(customProgressBinding.root)
        // builder.setView(view)

        customProgressBinding.tvProgress.text = progress
        aDProgress = builder.create()
        aDProgress!!.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#00000000")))
        aDProgress!!.setCanceledOnTouchOutside(false)
        aDProgress!!.setCancelable(false)
        aDProgress!!.show()
    }

    fun dismissDialog() {
        if (aDProgress != null) {
            aDProgress!!.dismiss()
        }
    }

}