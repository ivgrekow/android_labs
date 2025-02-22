package com.preferencesframework

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView

class NumberPickerDialogPreference: DialogPreference {
    private val DEFAULT_MIN_PROGRESS = 0
    private val DEFAULT_MAX_PROGRESS = 100
    private val DEFAULT_PROGRESS = 0

    private var minValue = 0
    private var maxValue = 0
    private var value = 0
    private lateinit var numberPicker: NumberPicker

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) {
        val a = context.theme.obtainStyledAttributes(attributeSet, R.styleable.SeekBarDialogPreference, 0, 0)

        try {
            setMinValue(a.getInteger(R.styleable.SeekBarDialogPreference_min, DEFAULT_MIN_PROGRESS))
            setMaxValue(a.getInteger(R.styleable.SeekBarDialogPreference_android_max, DEFAULT_MAX_PROGRESS))
        } finally {
            a.recycle()
        }

        dialogLayoutResource = R.layout.preference_number_picker_dialog
        setPositiveButtonText(android.R.string.ok)
        setNegativeButtonText(android.R.string.cancel)
        dialogIcon = null
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        setValue(if (restorePersistedValue) getPersistedInt(DEFAULT_PROGRESS) else defaultValue as Int)
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any {
        return a.getInt(index, DEFAULT_PROGRESS)
    }

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)

        val dialogMessageText: TextView = view.findViewById(R.id.text_dialog_message)
        dialogMessageText.text = dialogMessage

        numberPicker = view.findViewById(R.id.number_picker)
        numberPicker.minValue = minValue
        numberPicker.maxValue = maxValue
        numberPicker.value = value
    }

    fun getMinValue(): Int {
        return minValue
    }

    fun setMinValue(minValue: Int) {
        this.minValue = minValue
        setValue(Math.max(value, minValue))
    }

    fun getMaxValue(): Int {
        return maxValue
    }

    fun setMaxValue(maxValue: Int) {
        this.maxValue = maxValue
        setValue(Math.min(value, maxValue))
    }

    fun getValue(): Int {
        return value
    }

    fun setValue(value: Int) {
        var valueCur = Math.max(Math.min(value, maxValue), minValue)

        if (valueCur != this.value) {
            this.value = valueCur
            persistInt(valueCur)
            notifyChanged()
        }
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        super.onDialogClosed(positiveResult)

        if (positiveResult) {
            val numberPickerValue = numberPicker.value

            if (callChangeListener(numberPickerValue)) {
                setValue(numberPickerValue)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()

        val myState = SavedState(superState)
        myState.minValue = minValue
        myState.maxValue = maxValue
        myState.value = value

        return myState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state == null || state::class != (SavedState::class.java)) {
            super.onRestoreInstanceState(state)
            return
        }

        val myState: SavedState = state as SavedState
        setMinValue(myState.minValue)
        setMaxValue(myState.maxValue)
        setValue(myState.value)

        super.onRestoreInstanceState(myState.superState)
    }

    private class SavedState : BaseSavedState {
        var minValue: Int = 0
        var maxValue: Int = 0
        var value: Int = 0

        constructor(superState: Parcelable?) : super(superState)

        constructor(source: Parcel) : super(source) {
            minValue = source.readInt()
            maxValue = source.readInt()
            value = source.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)

            dest.writeInt(minValue)
            dest.writeInt(maxValue)
            dest.writeInt(value)
        }

        /*companion object {
            @Suppress("unused")
            val CREATOR: Parcelable.Creator<SavedState?> = object : Parcelable.Creator<SavedState?> {
                override fun createFromParcel(inn: Parcel): SavedState {
                    return SavedState(inn)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }*/
    }
}