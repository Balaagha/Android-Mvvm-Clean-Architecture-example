package com.example.data.helper.manager

import android.content.Context
import android.content.SharedPreferences
import com.example.data.helper.models.typedefs.SharedTypes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsManager constructor(context: Context?, @SharedTypes.SharedTypesDef sharedType: String) {

    val prefs: SharedPreferences? = context?.getSharedPreferences(sharedType, Context.MODE_PRIVATE)

    fun <T> set(item: String, value: T) {
        val editor = prefs?.edit()

        when (value) {
            is Int -> {
                editor?.putInt(item, value)
            }
            is Long -> {
                editor?.putLong(item, value)
            }
            is String -> {
                editor?.putString(item, value)
            }
            is Boolean -> {
                editor?.putBoolean(item, value)
            }
            else -> {
                editor?.putString(item, value as String)
            }
        }
        editor?.apply()
    }


    inline fun <reified T> get(item: String, default: T?): T? {
        return when (T::class) {
            Int::class -> {
                prefs?.getInt(item, default as Int) as T?
            }
            Long::class -> {
                prefs?.getLong(item, default as Long) as T?
            }
            String::class -> {
                prefs?.getString(item, default as String?) as T?
            }
            Boolean::class -> {
                prefs?.getBoolean(item, default as Boolean) as T?
            }
            else -> {
                prefs?.getString(item, default as String?) as T?
            }
        }
    }


    fun contains(prName: String) = try {
        prefs!!.contains(prName)
    } catch (e: Exception) {
        false
    }

    fun clearSharedPrefs() {
        val editor = prefs?.edit()
        editor?.clear()
        editor?.apply()
    }

    fun removeSharedPrefs(prName: String) {
        val editor = prefs?.edit()
        editor?.remove(prName)
        editor?.apply()
    }

    /**
     * Saves object into the Preferences.
     *
     * @param `object` Object of model class (of type [T]) to save
     * @param key Key with which Shared preferences to
     **/
    fun <T> saveObject(key: String, `object`: T): Boolean {
        return try {
//          val jsonString = GsonBuilder().create().toJson(`object`)
            val jsonString = Gson().toJson(`object`)
            prefs?.edit()?.putString(key, jsonString)?.apply()
            true
        } catch (e: Exception){
            false
        }
    }

    /**
     * This function retrieves object from saved json string
     */
    inline fun <reified T> loadObject(serializedObjectKey: String, defaultValue: T): T {
        return try {
            fromJson(prefs?.getString(serializedObjectKey, ""))
        } catch (e: Exception) {
            removeSharedPrefs(serializedObjectKey)
            return defaultValue
        }
    }

    /**
     * Get Gson data for this Typetoken
     */
    inline fun <reified T> fromJson(json: String?): T {
//      return GsonBuilder().create().fromJson(json, T::class.java)
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }



}