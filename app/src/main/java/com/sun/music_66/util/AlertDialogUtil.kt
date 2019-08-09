package com.sun.music_66.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.sun.music_66.R
/**
 * Created by nguyenxuanhoi on 2019-07-18.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
object AlertDialogUtil {

    @SuppressLint("InflateParams")
    fun showMessageDialog(context: Context, message: String, cancelable: Boolean, onButtonClicked: () -> Unit) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_message, null)
        val txtMessage = view.findViewById(R.id.txtMessage) as TextView
        val btnOK = view.findViewById(R.id.btnOK) as Button
        val dialog = createDialog(context, cancelable, view)
        txtMessage.text = message
        btnOK.setOnClickListener {
            dialog.cancel()
            onButtonClicked.invoke()
        }
        dialog.show()
    }

    @SuppressLint("InflateParams")
    fun showConfirmMessageDialog(context: Context, message: String, cancelable: Boolean, onPositiveButtonClicked: () -> Unit) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_message, null)
        val txtMessage = view.findViewById(R.id.txtMessage) as TextView
        val btnNegative = view.findViewById(R.id.btnNegative) as Button
        val btnPositive = view.findViewById(R.id.btnPositive) as Button
        val dialog = createDialog(context, cancelable, view)
        txtMessage.text = message
        btnNegative.setOnClickListener {
            dialog.cancel()
        }
        btnPositive.setOnClickListener {
            dialog.cancel()
            onPositiveButtonClicked.invoke()
        }

        dialog.show()
    }

    private fun createDialog(context: Context, cancelable: Boolean, view: View): Dialog {
        val dialog = AlertDialog.Builder(context)
                .setCancelable(cancelable)
                .setView(view)
                .create()
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimationNormal
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        return dialog
    }
}
