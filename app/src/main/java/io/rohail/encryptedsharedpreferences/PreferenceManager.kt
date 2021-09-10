package io.rohail.encryptedsharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferenceManager(
    context: Context,
    prefsName: String? = context.getString(R.string.app_name)
) {


    private val sharedPreference = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun all(): MutableMap<String, *> = sharedPreference.all

    fun save(KEY_NAME: String, value: Int) = doSave(KEY_NAME, value)
    fun save(KEY_NAME: String, value: Long) = doSave(KEY_NAME, value)
    fun save(KEY_NAME: String, value: Float) = doSave(KEY_NAME, value)
    fun save(KEY_NAME: String, value: String) = doSave(KEY_NAME, value)
    fun save(KEY_NAME: String, status: Boolean) = doSave(KEY_NAME, status)

    private fun doSave(key: String, value: Any) {
        with(sharedPreference.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
            }
            commit()
        }
    }

    fun putToStringSet(KEY_NAME: String, value: String) {
        val newSet = mutableSetOf(value)
        newSet.addAll(getValueStringSet(KEY_NAME))
        with(sharedPreference.edit()) {
            putStringSet(KEY_NAME, newSet)
            commit()
        }
    }

    fun getValueString(KEY_NAME: String, defValue: String? = null): String? =
        sharedPreference.getString(KEY_NAME, defValue)

    fun getValueStringSet(KEY_NAME: String, defValue: Set<String>? = null): Set<String> =
        sharedPreference.getStringSet(KEY_NAME, defValue) ?: emptySet()

    fun getValueInt(KEY_NAME: String): Int = sharedPreference.getInt(KEY_NAME, 0)
    fun getValueLong(KEY_NAME: String) = sharedPreference.getLong(KEY_NAME, 0)
    fun getValueBoolean(KEY_NAME: String) = sharedPreference.getBoolean(KEY_NAME, false)

    fun removeValue(KEY_NAME: String) {
        sharedPreference.remove(KEY_NAME)
    }

    fun removeValueFromStringSet(KEY_NAME: String, value: String) {
        val newSet = getValueStringSet(KEY_NAME = KEY_NAME, emptySet()).toMutableSet()
        newSet.remove(value)
        with(sharedPreference.edit()) {
            putStringSet(KEY_NAME, newSet)
            commit()
        }
    }

    fun clearSharedPreference() {
        sharedPreference.clear()
    }

    @Suppress("UNCHECKED_CAST")
    private fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { it.putString(key, value) }
            is Int -> edit { it.putInt(key, value.toInt()) }
            is Boolean -> edit { it.putBoolean(key, value) }
            is Float -> edit { it.putFloat(key, value.toFloat()) }
            is Long -> edit { it.putLong(key, value.toLong()) }
            is Set<*> -> edit { it.putStringSet(key, value as Set<String>) }
            else -> {
                Log.e("Error", "SharedPreferences Unsupported Type: $value")
            }
        }
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    private fun SharedPreferences.clear() {
        edit { it.clear() }
    }

    private fun SharedPreferences.remove(key: String) {
        edit { it.remove(key) }
    }

}