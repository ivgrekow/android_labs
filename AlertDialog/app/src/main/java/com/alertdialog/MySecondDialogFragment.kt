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

class MySecondDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Выбор есть всегда")
                .setMessage("Выбери пищу")
                .setIcon(R.drawable.baseline_pets_24)
                .setCancelable(true)
                .setPositiveButton("Вкусная пища") { _, _ ->
                    Toast.makeText(activity, "Вы сделали правильный выбор", Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton("Никакая")  { _, _ ->
                    Toast.makeText(activity, "Ладно", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Здоровая пища") { _, _ ->
                    Toast.makeText(activity, "Возможно вы правы", Toast.LENGTH_SHORT).show()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}