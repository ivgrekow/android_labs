package com.preferencesframework

import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceActivity
import android.widget.ListView

class RingtonePreferenceActivity: PreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.ringtone_preference)
        findViewById<ListView>(android.R.id.list).setBackgroundColor(Color.MAGENTA)
    }
}