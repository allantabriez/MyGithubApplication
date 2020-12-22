package com.example.my2ndsubmission.view

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.my2ndsubmission.R
import com.example.my2ndsubmission.alarm.AlarmReceiver

class SettingPreferenceFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var alarm: String
    private lateinit var alarmPreference: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        setSummaries()
        alarmReceiver = AlarmReceiver()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init(){
        alarm = resources.getString(R.string.key_alarm)
        alarmPreference = findPreference<SwitchPreference>(alarm) as SwitchPreference
    }

    private fun setSummaries(){
        val sharedPref = preferenceManager.sharedPreferences
        alarmPreference.isChecked = sharedPref.getBoolean(alarm, false)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == alarm) {
            alarmPreference.isChecked = sharedPreferences.getBoolean(alarm, false)
            val bool = sharedPreferences.getBoolean(alarm, false)
            if (bool) alarmReceiver.setRepeatingAlarm(requireContext())
            else alarmReceiver.cancelAlarm(requireContext())
        }
    }
}