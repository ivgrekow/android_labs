package com.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class MyDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val textView = TextView(activity)

        with (textView) {
            text = "Важное сообщение! Пожалуйста, прочитайте его! Очень прошу!"
            textSize = 18.0F
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                //.setTitle("Важное сообщение!")
                .setCustomTitle(textView)
                .setMessage("Покормите кота!")
                .setIcon(R.drawable.baseline_pets_24)
                .setPositiveButton("Ок, иду на кухню") {
                    dialog, _ -> dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}