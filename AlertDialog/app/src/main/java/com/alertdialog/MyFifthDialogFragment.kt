package com.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar

class MyFifthDialogFragment: DialogFragment() {
    private val catNames = arrayOf("Васька", "Рыжик", "Мурзик")
    private val checkedItems = booleanArrayOf(false, true, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val linearLayout = layoutInflater.inflate(R.layout.rating_dialog, null)
        val rating = linearLayout.findViewById<RatingBar>(R.id.rating_bar)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Голосуем за любимого кота!")
                .setView(linearLayout)
                .setIcon(android.R.drawable.btn_star_big_on)
                .setPositiveButton("Готово") { _, _ ->
                    Toast.makeText(activity, "Рейтинг: ${rating.rating}", Toast.LENGTH_SHORT).show()
                    dialog?.dismiss()
                    (activity as MainActivity).ok()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.cancel()
                    (activity as MainActivity).cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}