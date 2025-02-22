package com.preferencesframework

import android.content.Context
import android.graphics.Color
import android.preference.PreferenceCategory
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CustomPreferenceCategory : PreferenceCategory {
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?) : super(context)

    override fun onCreateView(parent: ViewGroup): View {
        val categoryTitle = super.onCreateView(parent) as TextView

        categoryTitle.apply {
            setBackgroundColor(Color.GREEN)
            setTextColor(Color.RED)
        }

        return categoryTitle
    }
}