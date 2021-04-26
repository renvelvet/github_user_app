package com.dicoding.githubuserapp.data.preference

import android.content.Context
import androidx.core.content.edit
import com.dicoding.githubuserapp.data.model.ReminderModel

class ReminderPreference(context: Context) {
    companion object {
        private const val PREFS_NAME = "pref_name"
        private const val REMINDER = "reminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: ReminderModel) {
        preferences.edit {
            putBoolean(REMINDER, value.isSet)
        }
    }

    fun getReminder() : ReminderModel {
        val model = ReminderModel()
        model.isSet = preferences.getBoolean(REMINDER, false)
        return model
    }
}