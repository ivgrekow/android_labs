package com.preferencesframework

import android.os.Bundle
import android.preference.PreferenceFragment

class UpdatePreferenceFragment: PreferenceFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_update)
    }
}