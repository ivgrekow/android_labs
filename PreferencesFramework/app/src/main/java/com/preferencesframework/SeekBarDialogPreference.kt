package com.preferencesframework

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.preference.DialogPreference
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView

class SeekBarDialogPreference: DialogPreference {
    private val DEFAULT_MIN_PROGRESS = 0
    private val DEFAULT_MAX_PROGRESS = 100
    private val DEFAULT_PROGRESS = 0

    private var minProgress = 0
    private var maxProgress = 0
    private var progress = 0
    private var progressTextSuffix: String? = null
    private lateinit var progressText: TextView
    private lateinit var seekBar: SeekBar

    constructor(context: Context): this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?): super(context, attributeSet) {
        val a = context.theme.obtainStyledAttributes(attributeSet, R.styleable.SeekBarDialogPreference, 0, 0)

        try {
            setMinProgress(a.getInteger(R.styleable.SeekBarDialogPreference_min, DEFAULT_MIN_PROGRESS))
            setMaxProgress(a.getInteger(R.styleable.SeekBarDialogPreference_android_max, DEFAULT_MAX_PROGRESS))
            setProgressTextSuffix(a.getString(R.styleable.SeekBarDialogPreference_progressTextSuffix))
        } finally {
            a.recycle()
        }

        dialogLayoutResource = R.layout.preference_seek_bar_dialog
        positiveButtonText = "OK"
        negativeButtonText = "Cancel"
        dialogIcon = null
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        setProgress(if (restorePersistedValue) getPersistedInt(DEFAULT_PROGRESS) else defaultValue as Int)
    }

    override fun onGetDefaultValue(a: TypedArray, index: Int): Any {
        return a.getInt(index, DEFAULT_PROGRESS)
    }

    override fun onBindDialogView(view: View) {
        super.onBindDialogView(view)

        val dialogMessageText: TextView = view.findViewById(R.id.text_dialog_message)
        dialogMessageText.text = dialogMessage

        progressText = view.findViewById(R.id.text_progress)
        seekBar = view.findViewById(R.id.seek_bar)
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(
                seekBar: SeekBar, progress: Int,
                fromUser: Boolean
            ) {
                val progressStr: String = java.lang.String.valueOf(progress + minProgress)
                progressText.text =
                    if (progressTextSuffix == null) progressStr
                    else progressStr + progressTextSuffix.toString()
            }
        })

        seekBar.max = maxProgress - minProgress
        seekBar.progress = progress - minProgress
    }

    fun getMinProgress(): Int {
        return minProgress
    }

    fun setMinProgress(minProgress: Int) {
        this.minProgress = minProgress
        setProgress(Math.max(progress, minProgress))
    }

    fun getMaxProgress(): Int {
        return maxProgress
    }

    fun setMaxProgress(maxProgress: Int) {
        this.maxProgress = maxProgress
        setProgress(Math.min(progress, maxProgress))
    }

    fun getProgress(): Int {
        return progress
    }

    fun setProgress(progress: Int) {
        var progressCur = Math.max(Math.min(progress, maxProgress), minProgress)

        if (progressCur != this.progress) {
            this.progress = progressCur
            persistInt(progressCur)
            notifyChanged()
        }
    }

    fun getProgressTextSuffix(): String? {
        return progressTextSuffix
    }

    fun setProgressTextSuffix(progressTextSuffix: String?) {
        this.progressTextSuffix = progressTextSuffix
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        super.onDialogClosed(positiveResult)

        if (positiveResult) {
            val seekBarProgress = seekBar.progress + minProgress

            if (callChangeListener(seekBarProgress)) {
                setProgress(seekBarProgress)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()

        val myState = SavedState(superState)
        myState.minProgress = getMinProgress()
        myState.maxProgress = getMaxProgress()
        myState.progress = getProgress()

        return myState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state == null || state::class != SavedState::class) {
            super.onRestoreInstanceState(state)
            return
        }

        val myState: SavedState = state as SavedState
        setMinProgress(myState.minProgress)
        setMaxProgress(myState.maxProgress)
        setProgress(myState.progress)

        super.onRestoreInstanceState(myState.superState)
    }

    private class SavedState : BaseSavedState {
        var minProgress: Int = 0
        var maxProgress: Int = 0
        var progress: Int = 0

        constructor(superState: Parcelable?) : super(superState)

        constructor(source: Parcel) : super(source) {
            minProgress = source.readInt()
            maxProgress = source.readInt()
            progress = source.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)

            dest.writeInt(minProgress)
            dest.writeInt(maxProgress)
            dest.writeInt(progress)
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