package com.preferencesframework

import android.preference.PreferenceActivity

class HeaderPrefsActivity: PreferenceActivity() {
    override fun onBuildHeaders(target: MutableList<Header>?) {
        loadHeadersFromResource(R.xml.header_settings, target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean {
        return UpdatePreferenceFragment::class.java.name.equals(fragmentName)
    }
}