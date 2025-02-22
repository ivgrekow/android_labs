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

class MyFourthDialogFragment: DialogFragment() {
    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")
    private val checkedItems = booleanArrayOf(false, true, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Выберите котов")
                .setMultiChoiceItems(catNames, checkedItems) { _, i, isChecked ->
                    checkedItems[i] = isChecked

                }
                .setPositiveButton("Готово") { _, _ ->
                    for (i in catNames.indices) {
                        val checked = checkedItems[i]

                        if (checked) {
                            println(catNames[i])
                        }
                    }
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}