package com.dicoding.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.model.ReminderModel
import com.dicoding.githubuserapp.preference.ReminderPreference
import com.dicoding.githubuserapp.databinding.SettingsActivityBinding
import com.dicoding.githubuserapp.receiver.AlarmReceiver

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var reminderSet: SwitchPreferenceCompat
        private var reminderModel: ReminderModel = ReminderModel()
        private var alarmReceiver: AlarmReceiver = AlarmReceiver()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            reminderSet = findPreference(resources.getString(R.string.key_set_alarm))!!
            val reminderPreference = context?.let { ReminderPreference(it) }
            if (reminderPreference != null) {
                reminderSet.isChecked = reminderPreference.getReminder().isSet
            }

            reminderSet.setOnPreferenceChangeListener { _, newValue ->
                reminderModel.isSet = newValue as Boolean
                reminderPreference?.setReminder(reminderModel)
                if (newValue) {
                    context?.let { alarmReceiver.setAlarm(it, "daily reminder") }
                } else {
                    context?.let { alarmReceiver.cancelAlarm(it) }
                }
                true
            }
        }
    }
}
//binding.apply {
//    settings.isChecked = reminderPreference.getReminder().isSet
//    settings.setOnCheckedChangeListener { switch, checked ->
//        reminderModel.isSet = checked
//        reminderPreference.setReminder(reminderModel)
//        if (checked) {
//            alarmReceiver.setAlarm(this, "daily reminder")
//        } else {
//            alarmReceiver.cancelAlarm(this)
//        }
//    }
//}
