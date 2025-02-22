package com.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar

class MyThirdDialogFragment: DialogFragment() {
    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Выберите кота")
                .setSingleChoiceItems(catNames, -1) { _, i ->
                    Toast.makeText(activity, "Выбранный кот: ${catNames[i]}", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("ОК") { _, _ ->
                }
                .setNegativeButton("Отмена") { _, _ ->
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}